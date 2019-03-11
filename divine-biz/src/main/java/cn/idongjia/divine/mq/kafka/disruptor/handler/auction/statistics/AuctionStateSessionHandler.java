package cn.idongjia.divine.mq.kafka.disruptor.handler.auction.statistics;

import cn.idongjia.divine.biz.AuctionStateLoadBO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionDO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionRelDO;
import cn.idongjia.divine.db.mybatis.pojo.ItemExtDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionSessionEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.divine.repository.AuctionSessionRelRepositoryI;
import cn.idongjia.divine.repository.ItemExtRepositoryI;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
@Slf4j
public class AuctionStateSessionHandler extends BaseEventHandler<AuctionSessionEvent> {
    @Resource
    private AuctionStateLoadBO           auctionStateLoadBO;
    @Resource
    private AuctionSessionRelRepositoryI auctionSessionRelRepository;

    @Resource
    private ItemExtRepositoryI itemExtRepositoryI;

    @Override
    public void onEvent(AuctionSessionEvent event) throws Exception {
        MysqlMessage<AuctionSessionDO> messageBody      = event.getValue();
        AuctionSessionDO               auctionSessionDO = messageBody.getData();
        try {
            List<AuctionSessionRelDO> auctionSessionRels = auctionSessionRelRepository.listBySessionId(auctionSessionDO.getAsid());
            if(Utils.isNotEmpty(auctionSessionRels)) {
                List<Long>          itemIds      = auctionSessionRels.stream().map(AuctionSessionRelDO::getAid).collect(Collectors.toList());
                Map<Long,ItemExtDO> itemExtDOMap = itemExtRepositoryI.mapByItemIds(itemIds);
                if(Utils.isNotEmpty(itemExtDOMap)) {
                    itemExtDOMap.values().stream()
                            .forEach(itemExtDO -> auctionStateLoadBO.loadById(
                                    itemExtDO.getCuid(), itemExtDO.getIid()));
                }
            }
        } catch(Exception e) {
            log.error("update live session failed {}",e);
        }
    }
}
