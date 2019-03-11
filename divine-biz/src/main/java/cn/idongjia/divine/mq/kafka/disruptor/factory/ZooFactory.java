package cn.idongjia.divine.mq.kafka.disruptor.factory;

import cn.idongjia.divine.mq.kafka.disruptor.event.LiveVideCoverEvent;
import cn.idongjia.divine.mq.kafka.disruptor.event.ZooEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public class ZooFactory implements EventFactory<ZooEvent> {
    @Override
    public ZooEvent newInstance() {
        return new ZooEvent();
    }
}
