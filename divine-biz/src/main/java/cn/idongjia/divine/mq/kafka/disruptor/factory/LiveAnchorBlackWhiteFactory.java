package cn.idongjia.divine.mq.kafka.disruptor.factory;

import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionBookEvent;
import cn.idongjia.divine.mq.kafka.disruptor.event.LiveAnchorBlackWhiteEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public class LiveAnchorBlackWhiteFactory implements EventFactory<LiveAnchorBlackWhiteEvent> {
    @Override
    public LiveAnchorBlackWhiteEvent newInstance() {
        return new LiveAnchorBlackWhiteEvent();
    }
}
