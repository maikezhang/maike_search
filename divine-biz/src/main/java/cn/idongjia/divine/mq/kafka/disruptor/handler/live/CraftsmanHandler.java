package cn.idongjia.divine.mq.kafka.disruptor.handler.live;

import cn.idongjia.divine.assembler.AssemblerI;
import cn.idongjia.divine.db.es.entity.LiveEntity;
import cn.idongjia.divine.db.mybatis.pojo.CraftsmanDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveShowDO;
import cn.idongjia.divine.db.mybatis.query.LiveShowQuery;
import cn.idongjia.divine.dto.LiveDTO;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.mq.kafka.disruptor.event.CraftsmanEvent;
import cn.idongjia.divine.repository.LiveShowRepositoryI;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
@Slf4j
public class CraftsmanHandler extends BaseEventHandler<CraftsmanEvent> {

    @Resource
    private AssemblerI<LiveEntity,LiveShowDO,LiveDTO> liveAssembler;
    @Resource
    private ESUpsertManager                           esUpsertManager;

    @Resource
    private LiveShowRepositoryI liveShowRepository;

    @Override
    public void onEvent(CraftsmanEvent event) throws Exception {
        MysqlMessage<CraftsmanDO> messageBody = event.getValue();
        CraftsmanDO               craftsmanDO = messageBody.getData();
        String                    type        = messageBody.getType();
        switch(type) {
            case UPDATE:
                List<LiveShowDO> liveShowDOS = liveShowRepository.select(LiveShowQuery.builder().uid(craftsmanDO.getCustomerId().intValue()).build());
                if(Utils.isNotEmpty(liveShowDOS)) {
                    liveShowDOS.stream().forEach(liveShowDO -> {
                        LiveDTO liveDTO = new LiveDTO();

                        liveDTO.setCraftsmanCity(craftsmanDO.getCity() == null ? "" : craftsmanDO.getCity());
                        liveDTO.setCraftsmanTitle(craftsmanDO.getTitle() == null ? "" : craftsmanDO.getTitle());
                        liveDTO.setId(liveShowDO.getId().toString());
                        LiveEntity liveEntity = liveAssembler.assemble(liveDTO);
                        esUpsertManager.upsertLiveEntity(liveEntity);
                    });

                }

                break;
            default:
                break;
        }
    }
}
