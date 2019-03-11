package cn.idongjia.divine.mq.kafka.disruptor.handler.auction;

import cn.idongjia.divine.biz.AuctionLoadBO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionOfferDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionOfferEvent;
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
public class AuctionOfferHandler extends BaseEventHandler<AuctionOfferEvent> {
    @Resource
    private AuctionLoadBO        auctionItemLoadBO;

    @Override
    public void onEvent(AuctionOfferEvent event) throws Exception {
        MysqlMessage<AuctionOfferDO> messageBody = event.getValue();
        AuctionOfferDO               data        = messageBody.getData();
        Long                         iid         = data.getIid();
        auctionItemLoadBO.loadByItemId(iid);
    }
}
