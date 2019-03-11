package cn.idongjia.divine.mq.kafka.disruptor.factory;

import cn.idongjia.divine.mq.kafka.disruptor.event.ItemEvent;
import cn.idongjia.divine.mq.kafka.disruptor.event.ZooEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public class ItemFactory implements EventFactory<ItemEvent> {
    @Override
    public ItemEvent newInstance() {
        return new ItemEvent();
    }
}
