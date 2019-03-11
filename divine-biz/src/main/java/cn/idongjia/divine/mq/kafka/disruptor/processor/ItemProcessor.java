package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.divine.db.mybatis.pojo.ItemDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.ItemEvent;
import cn.idongjia.divine.mq.kafka.disruptor.factory.ItemFactory;
import cn.idongjia.divine.mq.kafka.disruptor.handler.auction.AuctionItemHandler;
import cn.idongjia.divine.mq.kafka.disruptor.handler.auction.statistics.AuctionStateItemHandler;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.LiveItemHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
public class ItemProcessor extends BaseEventProccessor<MysqlMessage<ItemDO>,ItemEvent> {

    @Resource
    private AuctionItemHandler auctionItemHandler;

    @Resource
    private LiveItemHandler         liveItemHandler;
    @Resource
    private AuctionStateItemHandler auctionStateItemHandler;

    public ItemProcessor() {
        super(ItemProcessor.class.getName());
    }

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    @Override
    protected EventFactory eventFactory() {
        return new ItemFactory();
    }

    @Override
    public void initPipe() {
        disruptor.handleEventsWith(auctionItemHandler,liveItemHandler,auctionStateItemHandler);
    }

}
