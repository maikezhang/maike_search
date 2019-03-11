package cn.idongjia.divine.mq.kafka.disruptor.handler.live;

import cn.idongjia.divine.db.es.entity.LiveEntity;
import cn.idongjia.divine.db.mybatis.pojo.LiveAnchorBlackWhiteDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveShowDO;
import cn.idongjia.divine.db.mybatis.query.LiveShowQuery;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.mq.kafka.disruptor.event.LiveAnchorBlackWhiteEvent;
import cn.idongjia.divine.repository.LiveShowRepositoryI;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.kafka.message.body.MysqlMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lc
 * @create at 2018/9/5.
 */
@Component
public class LiveAnchorBlackWhiteHandler extends BaseEventHandler<LiveAnchorBlackWhiteEvent> {

    @Resource
    private LiveShowRepositoryI liveShowRepository;

    @Resource
    private ESUpsertManager esUpsertManager;

    @Override
    public void onEvent(LiveAnchorBlackWhiteEvent event) throws Exception {
        MysqlMessage<LiveAnchorBlackWhiteDO> message                = event.getValue();
        LiveAnchorBlackWhiteDO               liveAnchorBlackWhiteDO = message.getData();
        String                               type                   = message.getType();
        switch(type) {
            case UPDATE:
            case INSERT:
                List<LiveShowDO> liveShowDOS = liveShowRepository.select(LiveShowQuery.builder().states(Arrays.asList(1,2)).uid(liveAnchorBlackWhiteDO.getAnchorId().intValue()).build());
                if(Utils.isNotEmpty(liveShowDOS)) {
                    for(LiveShowDO liveShowDO: liveShowDOS) {
                        LiveEntity liveEntity = new LiveEntity();
                        liveEntity.setShowLocation(liveAnchorBlackWhiteDO.getType());
                        liveEntity.setId(liveShowDO.getId().toString());
                        esUpsertManager.upsertLiveEntity(liveEntity);
                    }
                }
                break;
            default: break;

        }

    }
}
