package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionRelDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionSessionRelEvent;
import cn.idongjia.divine.mq.kafka.disruptor.factory.AuctionSessionRelFactory;
import cn.idongjia.divine.mq.kafka.disruptor.handler.auction.AuctionSessionRelHandler;
import cn.idongjia.divine.mq.kafka.disruptor.handler.auction.statistics.AuctionStateSessionRelHandler;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.LiveAuctionSessionRelHandler;
import cn.idongjia.divine.mq.kafka.disruptor.handler.session.SessionRelHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
public class AuctionSessionRelProcessor extends BaseEventProccessor<MysqlMessage<AuctionSessionRelDO>, AuctionSessionRelEvent> {

    @Resource
    private LiveAuctionSessionRelHandler  liveAuctionSessionRelHandler;
    @Resource
    private AuctionSessionRelHandler      auctionSessionRelHandler;
    @Resource
    private AuctionStateSessionRelHandler auctionStateSessionRelHandler;
    @Resource
    private SessionRelHandler             sessionRelHandler;

    public AuctionSessionRelProcessor() {
        super(AuctionSessionRelProcessor.class.getName());
    }

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    @Override
    protected EventFactory eventFactory() {
        return new AuctionSessionRelFactory();
    }

    @Override
    public void initPipe() {
        disruptor.handleEventsWith(liveAuctionSessionRelHandler, auctionSessionRelHandler,
                auctionStateSessionRelHandler, sessionRelHandler);
    }

}
