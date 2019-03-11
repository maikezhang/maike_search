package cn.idongjia.divine.mq.kafka.disruptor.factory;

import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionOfferEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public class AuctionOfferFactory implements EventFactory<AuctionOfferEvent> {
    @Override
    public AuctionOfferEvent newInstance() {
        return new AuctionOfferEvent();
    }
}
