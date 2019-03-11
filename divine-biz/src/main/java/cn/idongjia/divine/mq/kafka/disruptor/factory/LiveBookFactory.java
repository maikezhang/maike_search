package cn.idongjia.divine.mq.kafka.disruptor.factory;

import cn.idongjia.divine.mq.kafka.disruptor.event.LiveBookEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public class LiveBookFactory implements EventFactory<LiveBookEvent> {
    @Override
    public LiveBookEvent newInstance() {
        return new LiveBookEvent();
    }
}
