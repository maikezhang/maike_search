package cn.idongjia.divine.mq.kafka.disruptor.factory;

import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionSessionEvent;
import cn.idongjia.divine.mq.kafka.disruptor.event.TabRecommendEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public class TabRecommendFactory implements EventFactory<TabRecommendEvent> {
    @Override
    public TabRecommendEvent newInstance() {
        return new TabRecommendEvent();
    }
}
