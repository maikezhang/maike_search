package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.divine.db.mybatis.pojo.AuctionManualRecommendDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionManualRecommendEvent;
import cn.idongjia.divine.mq.kafka.disruptor.factory.AuctionManaualRecommendFactory;
import cn.idongjia.divine.mq.kafka.disruptor.handler.auction.AuctionManualRecommendHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
public class AuctionManualRecommendProcessor extends BaseEventProccessor<MysqlMessage<AuctionManualRecommendDO>,AuctionManualRecommendEvent> {

    @Resource
    private AuctionManualRecommendHandler auctionManualRecommendHandler;

    public AuctionManualRecommendProcessor() {
        super(AuctionManualRecommendProcessor.class.getName());
    }

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    @Override
    protected EventFactory eventFactory() {
        return new AuctionManaualRecommendFactory();
    }

    @Override
    public void initPipe() {
        disruptor.handleEventsWith(auctionManualRecommendHandler);
    }

}
