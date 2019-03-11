package cn.idongjia.divine.mq.kafka.disruptor.factory;

import cn.idongjia.divine.mq.kafka.disruptor.event.ItemEvent;
import cn.idongjia.divine.mq.kafka.disruptor.event.ItemExtEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public class ItemExtFactory implements EventFactory<ItemExtEvent> {
    @Override
    public ItemExtEvent newInstance() {
        return new ItemExtEvent();
    }
}
