package cn.idongjia.divine.mq.kafka.disruptor.factory;

import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionEvent;
import cn.idongjia.divine.mq.kafka.disruptor.event.MediaEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public class MediaFactory implements EventFactory<MediaEvent> {
    @Override
    public MediaEvent newInstance() {
        return new MediaEvent();
    }
}
