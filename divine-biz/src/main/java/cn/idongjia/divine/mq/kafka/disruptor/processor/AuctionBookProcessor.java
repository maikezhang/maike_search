package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.divine.db.mybatis.pojo.AuctionBookDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionBookEvent;
import cn.idongjia.divine.mq.kafka.disruptor.factory.AuctionBookFactory;
import cn.idongjia.divine.mq.kafka.disruptor.handler.auction.AuctionBookHandler;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.LiveAuctionBookHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
public class AuctionBookProcessor extends BaseEventProccessor<MysqlMessage<AuctionBookDO>,AuctionBookEvent> {

    @Resource
    private LiveAuctionBookHandler liveAuctionBookHandler;
    @Resource
    private AuctionBookHandler     auctionBookHandler;

    public AuctionBookProcessor() {
        super(AuctionBookProcessor.class.getName());
    }

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    @Override
    protected EventFactory eventFactory() {
        return new AuctionBookFactory();
    }

    @Override
    public void initPipe() {
        disruptor.handleEventsWith(liveAuctionBookHandler,auctionBookHandler);
    }

}
