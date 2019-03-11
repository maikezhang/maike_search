package cn.idongjia.divine.mq.kafka.disruptor.handler.session;

import cn.idongjia.divine.db.es.entity.SessionEntity;
import cn.idongjia.divine.db.mybatis.pojo.AuctionOfferDO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionRelDO;
import cn.idongjia.divine.db.mybatis.pojo.CountPO;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionOfferEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.divine.repository.AuctionOfferRepositoryI;
import cn.idongjia.divine.repository.AuctionSessionRelRepositoryI;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
@Slf4j
public class SessionAuctionOfferHandler extends BaseEventHandler<AuctionOfferEvent> {
    @Resource
    private AuctionSessionRelRepositoryI auctionSessionRelRepositoryI;
    @Resource
    private AuctionOfferRepositoryI      auctionOfferRepositoryI;
    @Resource
    private ESUpsertManager              esUpsertManager;

    @Override
    public void onEvent(AuctionOfferEvent event) throws Exception {
        MysqlMessage<AuctionOfferDO>  messageBody            = event.getValue();
        AuctionOfferDO                data                   = messageBody.getData();
        Long                          iid                    = data.getIid();
        Map<Long,AuctionSessionRelDO> auctionSessionRelDOMap = auctionSessionRelRepositoryI.mapByItemIds(Arrays.asList(iid));
        if(Utils.isNotEmpty(auctionSessionRelDOMap)) {
            List<Long> sessionIds = auctionSessionRelDOMap.entrySet().stream().map(entry -> entry.getValue().getAsid().longValue()).collect(Collectors.toList());
            if(Utils.isNotEmpty(sessionIds)) {
                Map<Long,CountPO> countPOMap = auctionOfferRepositoryI.groupBySessionIds(sessionIds);
                sessionIds.stream().forEach(sessionId -> {
                    CountPO       countPO       = countPOMap.get(sessionId);
                    SessionEntity sessionEntity = new SessionEntity();
                    sessionEntity.setId(sessionId.toString());
                    if(countPO == null) {
                        sessionEntity.setOfferTotal(0);
                    } else {
                        sessionEntity.setOfferTotal(countPO.getCount());
                    }
                    esUpsertManager.sessionUpsert(sessionEntity);
                });
            }

        }
    }
}
