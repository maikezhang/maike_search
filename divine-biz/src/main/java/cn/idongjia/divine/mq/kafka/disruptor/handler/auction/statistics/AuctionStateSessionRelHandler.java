package cn.idongjia.divine.mq.kafka.disruptor.handler.auction.statistics;

import cn.idongjia.divine.biz.AuctionStateLoadBO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionRelDO;
import cn.idongjia.divine.db.mybatis.pojo.ItemExtDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionSessionRelEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.divine.repository.ItemExtRepositoryI;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
@Slf4j
public class AuctionStateSessionRelHandler extends BaseEventHandler<AuctionSessionRelEvent> {
    @Resource
    private AuctionStateLoadBO auctionStateLoadBO;
    @Resource
    private ItemExtRepositoryI itemExtRepositoryI;

    @Override
    public void onEvent(AuctionSessionRelEvent event) throws Exception {
        MysqlMessage<AuctionSessionRelDO> messageBody      = event.getValue();
        AuctionSessionRelDO               auctionSessionDO = messageBody.getData();
        Long                              itemId           = auctionSessionDO.getAid();
        Map<Long,ItemExtDO>               itemExtDOMap = itemExtRepositoryI.mapByItemIds(Arrays.asList(itemId));
        if(Utils.isNotEmpty(itemExtDOMap)) {
            itemExtDOMap.values().stream()
                    .forEach(itemExtDO -> auctionStateLoadBO.loadById(
                            itemExtDO.getCuid(), itemExtDO.getIid()));
        }
    }
}
