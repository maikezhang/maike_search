package cn.idongjia.divine.mq.kafka.disruptor.factory;

import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionBookEvent;
import cn.idongjia.divine.mq.kafka.disruptor.event.UserStageRelEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public class LiveUserStageRelFactory implements EventFactory<UserStageRelEvent> {
    @Override
    public UserStageRelEvent newInstance() {
        return new UserStageRelEvent();
    }
}
