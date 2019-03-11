package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.divine.db.mybatis.pojo.ZooDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.ZooEvent;
import cn.idongjia.divine.mq.kafka.disruptor.factory.ZooFactory;
import cn.idongjia.divine.mq.kafka.disruptor.handler.auction.AuctionZooHandler;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.LiveZooHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
public class ZooProcesser extends BaseEventProccessor<MysqlMessage<ZooDO>,ZooEvent> {
    public ZooProcesser() {
        super(ZooProcesser.class.getName());
    }
    @Resource
    private LiveZooHandler    liveZooHandler;
    @Resource
    private AuctionZooHandler auctionZooHandler;

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    @Override
    protected EventFactory eventFactory() {
        return new ZooFactory();
    }

    @Override
    public void initPipe() {
        disruptor.handleEventsWith(liveZooHandler,auctionZooHandler);
    }

}
