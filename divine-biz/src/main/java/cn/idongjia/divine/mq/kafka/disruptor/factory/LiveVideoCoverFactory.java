package cn.idongjia.divine.mq.kafka.disruptor.factory;

import cn.idongjia.divine.mq.kafka.disruptor.event.LiveResourceEvent;
import cn.idongjia.divine.mq.kafka.disruptor.event.LiveVideCoverEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public class LiveVideoCoverFactory implements EventFactory<LiveVideCoverEvent> {
    @Override
    public LiveVideCoverEvent newInstance() {
        return new LiveVideCoverEvent();
    }
}
