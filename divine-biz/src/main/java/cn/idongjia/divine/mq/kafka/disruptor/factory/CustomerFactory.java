package cn.idongjia.divine.mq.kafka.disruptor.factory;

import cn.idongjia.divine.mq.kafka.disruptor.event.CustomerEvent;
import cn.idongjia.divine.mq.kafka.disruptor.event.LiveShowEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public class CustomerFactory implements EventFactory<CustomerEvent> {
    @Override
    public CustomerEvent newInstance() {
        return new CustomerEvent();
    }
}
