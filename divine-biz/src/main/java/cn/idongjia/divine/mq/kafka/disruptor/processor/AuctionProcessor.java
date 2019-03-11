package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.divine.db.mybatis.pojo.AuctionDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionEvent;
import cn.idongjia.divine.mq.kafka.disruptor.factory.AuctionFactory;
import cn.idongjia.divine.mq.kafka.disruptor.handler.auction.AuctionHandler;
import cn.idongjia.divine.mq.kafka.disruptor.handler.auction.statistics.AuctionStateHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.lmax.disruptor.EventFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/17.
 */
@Component
@Slf4j
public class AuctionProcessor extends BaseEventProccessor<MysqlMessage<AuctionDO>,AuctionEvent> {

    @Resource
    private AuctionHandler auctionHandler;
    @Resource
    private AuctionStateHandler auctionStateHandler;

    public AuctionProcessor() {
        super(AuctionProcessor.class.getName());
    }

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    @Override
    protected EventFactory eventFactory() {
        return new AuctionFactory();
    }

    @Override
    public void initPipe() {
        disruptor.handleEventsWith(auctionHandler,auctionStateHandler);
    }
}
