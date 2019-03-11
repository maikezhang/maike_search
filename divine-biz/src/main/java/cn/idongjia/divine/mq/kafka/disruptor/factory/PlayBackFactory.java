package cn.idongjia.divine.mq.kafka.disruptor.factory;

import cn.idongjia.divine.mq.kafka.disruptor.event.LivePureEvent;
import cn.idongjia.divine.mq.kafka.disruptor.event.PlayBackEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public class PlayBackFactory implements EventFactory<PlayBackEvent> {
    @Override
    public PlayBackEvent newInstance() {
        return new PlayBackEvent();
    }
}
