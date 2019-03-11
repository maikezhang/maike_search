package cn.idongjia.divine.mq.kafka.disruptor.factory;

import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionBookEvent;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionSessionEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public class AuctionBookFactory implements EventFactory<AuctionBookEvent> {
    @Override
    public AuctionBookEvent newInstance() {
        return new AuctionBookEvent();
    }
}
