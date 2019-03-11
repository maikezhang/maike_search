package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.divine.db.mybatis.pojo.ItemExtDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.ItemExtEvent;
import cn.idongjia.divine.mq.kafka.disruptor.factory.ItemExtFactory;
import cn.idongjia.divine.mq.kafka.disruptor.handler.auction.AuctionItemExtHandler;
import cn.idongjia.divine.mq.kafka.disruptor.handler.auction.statistics.AuctionStateItemExtHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
public class ItemExtProcessor extends BaseEventProccessor<MysqlMessage<ItemExtDO>,ItemExtEvent> {

    @Resource
    private AuctionItemExtHandler auctionItemExtHandler;
    @Resource
    private AuctionStateItemExtHandler auctionStateItemExtHandler;

    public ItemExtProcessor() {
        super(ItemExtProcessor.class.getName());
    }

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    @Override
    protected EventFactory eventFactory() {
        return new ItemExtFactory();
    }

    @Override
    public void initPipe() {
        disruptor.handleEventsWith(auctionItemExtHandler,auctionStateItemExtHandler);
    }

}
