package cn.idongjia.divine.mq.kafka.disruptor.handler.auction.statistics;

import cn.idongjia.divine.biz.AuctionStateLoadBO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionDO;
import cn.idongjia.divine.db.mybatis.pojo.ItemExtDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.divine.repository.ItemExtRepositoryI;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * @author lc
 * @create at 2018/8/17.
 */
@Component
@Slf4j
public class AuctionStateHandler extends BaseEventHandler<AuctionEvent> {
    @Resource
    private AuctionStateLoadBO auctionStateLoadBO;

    @Resource
    private ItemExtRepositoryI itemExtRepository;

    @Override
    public void onEvent(AuctionEvent event) throws Exception {
        MysqlMessage<AuctionDO> mysqlMessage = event.getValue();
        AuctionDO               auctionDO    = mysqlMessage.getData();
        Map<Long,ItemExtDO>     itemExtDOMap = itemExtRepository.mapByItemIds(Arrays.asList(auctionDO.getIid()));
        ItemExtDO               itemExtDO    = itemExtDOMap.get(auctionDO.getIid());
        auctionStateLoadBO.loadById(itemExtDO.getCuid(), auctionDO.getIid());
    }
}
