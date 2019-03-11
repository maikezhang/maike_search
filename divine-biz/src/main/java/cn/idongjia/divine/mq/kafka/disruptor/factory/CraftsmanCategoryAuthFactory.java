package cn.idongjia.divine.mq.kafka.disruptor.factory;

import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionBookEvent;
import cn.idongjia.divine.mq.kafka.disruptor.event.CraftsmanCategoryAuthEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public class CraftsmanCategoryAuthFactory implements EventFactory<CraftsmanCategoryAuthEvent> {
    @Override
    public CraftsmanCategoryAuthEvent newInstance() {
        return new CraftsmanCategoryAuthEvent();
    }
}
