package cn.idongjia.divine.mq.kafka.disruptor.factory;

import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionSessionRelEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public class AuctionSessionRelFactory implements EventFactory<AuctionSessionRelEvent> {
    @Override
    public AuctionSessionRelEvent newInstance() {
        return new AuctionSessionRelEvent();
    }
}
