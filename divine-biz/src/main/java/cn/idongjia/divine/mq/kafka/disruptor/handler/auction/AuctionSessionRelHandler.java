package cn.idongjia.divine.mq.kafka.disruptor.handler.auction;

import cn.idongjia.divine.biz.AuctionLoadBO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionRelDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionSessionRelEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
@Slf4j
public class AuctionSessionRelHandler extends BaseEventHandler<AuctionSessionRelEvent> {
    @Resource
    private AuctionLoadBO auctionItemLoadBO;

    @Override
    public void onEvent(AuctionSessionRelEvent event) throws Exception {
        MysqlMessage<AuctionSessionRelDO> messageBody      = event.getValue();
        AuctionSessionRelDO               auctionSessionDO = messageBody.getData();
        Long                              itemId           = auctionSessionDO.getAid();
        auctionItemLoadBO.loadByItemId(itemId);
    }
}
