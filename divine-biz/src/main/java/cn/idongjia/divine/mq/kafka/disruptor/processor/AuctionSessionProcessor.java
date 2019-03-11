package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionSessionEvent;
import cn.idongjia.divine.mq.kafka.disruptor.factory.AuctionSessionFactory;
import cn.idongjia.divine.mq.kafka.disruptor.handler.auction.AuctionSessionHandler;
import cn.idongjia.divine.mq.kafka.disruptor.handler.auction.statistics.AuctionStateSessionHandler;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.LiveAuctionSessionHandler;
import cn.idongjia.divine.mq.kafka.disruptor.handler.session.SessionHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
public class AuctionSessionProcessor extends BaseEventProccessor<MysqlMessage<AuctionSessionDO>,AuctionSessionEvent> {

    @Resource
    private LiveAuctionSessionHandler liveAuctionSessionHandler;
    @Resource
    private AuctionSessionHandler     auctionSessionHandler;
    @Resource
    private SessionHandler            sessionHandler;
    @Resource
    private AuctionStateSessionHandler auctionStateSessionHandler;

    public AuctionSessionProcessor() {
        super(AuctionSessionProcessor.class.getName());
    }

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    @Override
    protected EventFactory eventFactory() {
        return new AuctionSessionFactory();
    }

    @Override
    public void initPipe() {
        disruptor.handleEventsWith(liveAuctionSessionHandler,auctionSessionHandler,sessionHandler,auctionStateSessionHandler);
    }

}
