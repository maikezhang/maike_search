package cn.idongjia.divine.mq.kafka.disruptor.factory;

import cn.idongjia.divine.mq.kafka.disruptor.event.CategoryEvent;
import cn.idongjia.divine.mq.kafka.disruptor.event.ZooEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public class CategoryFactory implements EventFactory<CategoryEvent> {
    @Override
    public CategoryEvent newInstance() {
        return new CategoryEvent();
    }
}
