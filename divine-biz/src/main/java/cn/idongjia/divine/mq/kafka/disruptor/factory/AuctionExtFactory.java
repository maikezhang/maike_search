package cn.idongjia.divine.mq.kafka.disruptor.factory;

import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionExtEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author yuexiaodong@idongjia.cn
 * @date 2018-12-18
 */
public class AuctionExtFactory implements EventFactory<AuctionExtEvent> {

    @Override
    public AuctionExtEvent newInstance() {
        return new AuctionExtEvent();
    }
}
