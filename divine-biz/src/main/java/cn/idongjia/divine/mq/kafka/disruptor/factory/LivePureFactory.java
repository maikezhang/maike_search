package cn.idongjia.divine.mq.kafka.disruptor.factory;

import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionSessionEvent;
import cn.idongjia.divine.mq.kafka.disruptor.event.LivePureEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public class LivePureFactory implements EventFactory<LivePureEvent> {
    @Override
    public LivePureEvent newInstance() {
        return new LivePureEvent();
    }
}
