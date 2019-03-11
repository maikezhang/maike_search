package cn.idongjia.divine.mq.kafka.disruptor.factory;

import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionManualRecommendEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public class AuctionManaualRecommendFactory implements EventFactory<AuctionManualRecommendEvent> {
    @Override
    public AuctionManualRecommendEvent newInstance() {
        return new AuctionManualRecommendEvent();
    }
}
