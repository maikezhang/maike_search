package cn.idongjia.divine.mq.kafka.disruptor.factory;

import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionBookEvent;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public class AuctionFactory implements EventFactory<AuctionEvent> {
    @Override
    public AuctionEvent newInstance() {
        return new AuctionEvent();
    }
}
