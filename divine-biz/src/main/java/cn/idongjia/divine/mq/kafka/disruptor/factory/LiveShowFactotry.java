package cn.idongjia.divine.mq.kafka.disruptor.factory;

import cn.idongjia.divine.mq.kafka.disruptor.event.LiveShowEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public class LiveShowFactotry implements EventFactory<LiveShowEvent> {
    @Override
    public LiveShowEvent newInstance() {
        return new LiveShowEvent();
    }
}
