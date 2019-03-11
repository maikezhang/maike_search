package cn.idongjia.divine.mq.kafka.disruptor.handler.live;

import cn.idongjia.divine.assembler.AssemblerI;
import cn.idongjia.divine.db.es.entity.LiveEntity;
import cn.idongjia.divine.db.mybatis.pojo.LiveShowDO;
import cn.idongjia.divine.db.mybatis.pojo.ZooDO;
import cn.idongjia.divine.db.mybatis.query.LiveShowQuery;
import cn.idongjia.divine.dto.LiveDTO;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.mq.kafka.disruptor.event.ZooEvent;
import cn.idongjia.divine.repository.LiveShowRepositoryI;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
@Slf4j
public class LiveZooHandler extends BaseEventHandler<ZooEvent> {
    private static final List<Integer> ALL_LIVE_STATUS = Arrays.asList(0,1,2,3);

    @Resource
    private AssemblerI<LiveEntity,LiveShowDO,LiveDTO> liveAssembler;
    @Resource
    private ESUpsertManager                           esUpsertManager;

    @Resource
    private LiveShowRepositoryI liveShowRepositoryI;

    @Override
    public void onEvent(ZooEvent event) throws Exception {
        MysqlMessage<ZooDO> messageBody = event.getValue();
        String              type        = messageBody.getType();
        switch(type) {
            case UPDATE:
                ZooDO zooDO = messageBody.getData();
                List<LiveShowDO> liveShowDOS = liveShowRepositoryI.select(LiveShowQuery.builder().statusArray(ALL_LIVE_STATUS).zid(zooDO.getZid()).build());
                if(Utils.isNotEmpty(liveShowDOS)) {
                    liveShowDOS.stream().forEach(liveShowDO -> {
                        LiveDTO liveDTO = new LiveDTO();
                        liveDTO.setZrc(zooDO.getZrc());
                        liveDTO.setSuid(zooDO.getUid());
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
