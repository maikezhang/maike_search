package cn.idongjia.divine.mq.kafka.disruptor.factory;

import cn.idongjia.divine.mq.kafka.disruptor.event.LiveResourceEvent;
import cn.idongjia.divine.mq.kafka.disruptor.event.PlayBackEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public class LiveResourceFactory implements EventFactory<LiveResourceEvent> {
    @Override
    public LiveResourceEvent newInstance() {
        return new LiveResourceEvent();
    }
}
