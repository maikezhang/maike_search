package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.divine.db.mybatis.pojo.AuctionOfferDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionOfferEvent;
import cn.idongjia.divine.mq.kafka.disruptor.factory.AuctionOfferFactory;
import cn.idongjia.divine.mq.kafka.disruptor.handler.auction.AuctionOfferHandler;
import cn.idongjia.divine.mq.kafka.disruptor.handler.session.SessionAuctionOfferHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
public class AuctionOfferProcesser extends BaseEventProccessor<MysqlMessage<AuctionOfferDO>,AuctionOfferEvent> {

    @Resource
    private AuctionOfferHandler        auctionOfferHandler;
    @Resource
    private SessionAuctionOfferHandler sessionAuctionOfferHandler;

    public AuctionOfferProcesser() {
        super(AuctionOfferProcesser.class.getName());
    }

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    @Override
    protected EventFactory eventFactory() {
        return new AuctionOfferFactory();
    }

    @Override
    public void initPipe() {
        disruptor.handleEventsWith(auctionOfferHandler,sessionAuctionOfferHandler);
    }

}
