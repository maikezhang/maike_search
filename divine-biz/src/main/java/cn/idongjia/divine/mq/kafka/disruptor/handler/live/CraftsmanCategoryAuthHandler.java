package cn.idongjia.divine.mq.kafka.disruptor.handler.live;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import cn.idongjia.divine.assembler.AssemblerI;
import org.springframework.stereotype.Component;

import cn.idongjia.divine.assembler.LiveAssembler;
import cn.idongjia.divine.db.es.entity.LiveEntity;
import cn.idongjia.divine.db.mybatis.pojo.CategoryDO;
import cn.idongjia.divine.db.mybatis.pojo.CraftsmanCategoryAuthDO;
import cn.idongjia.divine.db.mybatis.pojo.CraftsmanCategoryRelDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveShowDO;
import cn.idongjia.divine.db.mybatis.query.LiveShowQuery;
import cn.idongjia.divine.dto.LiveDTO;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.mq.kafka.disruptor.event.CraftsmanCategoryAuthEvent;
import cn.idongjia.divine.repository.CraftsmanCategoryAuthRepostoryI;
import cn.idongjia.divine.repository.LiveShowRepositoryI;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
@Slf4j
public class CraftsmanCategoryAuthHandler extends BaseEventHandler<CraftsmanCategoryAuthEvent> {
    @Resource
    private AssemblerI<LiveEntity,LiveShowDO,LiveDTO> liveAssembler;
    @Resource
	private ESUpsertManager                           esUpsertManager;
    @Resource
    private CraftsmanCategoryAuthRepostoryI           craftsmanCategoryAuthRepostory;

	@Resource
	private LiveShowRepositoryI				liveShowRepository;
    @Override
    public void onEvent(CraftsmanCategoryAuthEvent event) throws Exception {
        MysqlMessage<CraftsmanCategoryAuthDO> messageBody             = event.getValue();
        CraftsmanCategoryAuthDO               craftsmanCategoryAuthDO = messageBody.getData();
        List<CategoryDO>                      categoryDOS             = craftsmanCategoryAuthRepostory.list(craftsmanCategoryAuthDO.getCraftsmanId());
        if(Utils.isNotEmpty(categoryDOS)) {
            List<CraftsmanCategoryRelDO> craftsmanCategoryRelDOS = categoryDOS.stream().map(categoryDO -> {
                CraftsmanCategoryRelDO craftsmanCategoryRelDO = new CraftsmanCategoryRelDO();
                craftsmanCategoryRelDO.setCategoryId(categoryDO.getId().longValue());
                craftsmanCategoryRelDO.setCategoryName(categoryDO.getName());
                craftsmanCategoryRelDO.setCraftsmanId(craftsmanCategoryAuthDO.getCraftsmanId().longValue());
                return craftsmanCategoryRelDO;
            }).collect(Collectors.toList());
			int userId = craftsmanCategoryAuthDO.getCraftsmanId().intValue();
			List<LiveShowDO> liveShowDOS = liveShowRepository.select(LiveShowQuery.builder().uid(userId).build());
			if (Utils.isNotEmpty(liveShowDOS)) {
				liveShowDOS.stream().forEach(liveShow -> {
					LiveDTO liveDTO = new LiveDTO();
					liveDTO.setCategoryDOS(craftsmanCategoryRelDOS);
					liveDTO.setId(liveShow.getId().toString());
					LiveEntity liveEntity = liveAssembler.assemble(liveDTO);
                    esUpsertManager.upsertLiveEntity( liveEntity);
				});
			}

        }

    }
}
