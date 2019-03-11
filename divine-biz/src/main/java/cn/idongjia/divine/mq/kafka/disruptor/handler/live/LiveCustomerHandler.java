package cn.idongjia.divine.mq.kafka.disruptor.handler.live;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import cn.idongjia.divine.assembler.AssemblerI;
import org.springframework.stereotype.Component;

import cn.idongjia.divine.assembler.LiveAssembler;
import cn.idongjia.divine.db.es.entity.LiveEntity;
import cn.idongjia.divine.db.mybatis.pojo.CustomerDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveShowDO;
import cn.idongjia.divine.db.mybatis.query.LiveShowQuery;
import cn.idongjia.divine.dto.LiveDTO;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.mq.kafka.disruptor.event.CustomerEvent;
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
public class LiveCustomerHandler extends BaseEventHandler<CustomerEvent> {

    @Resource
    private AssemblerI<LiveEntity,LiveShowDO,LiveDTO> liveAssembler;
    @Resource
	private ESUpsertManager                           esUpsertManager;

	@Resource
	private LiveShowRepositoryI	 liveShowRepository;

    @Override
    public void onEvent(CustomerEvent event) throws Exception {
        MysqlMessage<CustomerDO> messageBody = event.getValue();
        String                   type        = messageBody.getType();
        CustomerDO               customerDO  = messageBody.getData();
        switch(type) {
            case UPDATE:
                //todo 等待条件更新接口
				List<LiveShowDO> liveShowDOS = liveShowRepository.select(LiveShowQuery.builder().uid(customerDO.getMainUserId()).build());
				if (Utils.isNotEmpty(liveShowDOS)) {
					 liveShowDOS.stream().forEach(liveShow -> {
						LiveDTO liveDTO = new LiveDTO();
						liveDTO.setCraftsmanAvatar(customerDO.getAvatar());
						liveDTO.setCraftsmanName(customerDO.getName());
                        liveDTO.setId(liveShow.getId().toString());
						LiveEntity liveEntity = liveAssembler.assemble(liveDTO);
                        esUpsertManager.upsertLiveEntity( liveEntity);
					});
				}

                break;
            default:
                break;
        }
    }
}
