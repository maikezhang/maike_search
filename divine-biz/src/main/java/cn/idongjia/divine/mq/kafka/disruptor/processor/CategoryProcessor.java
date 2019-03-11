package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.divine.db.mybatis.pojo.CategoryDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.CategoryEvent;
import cn.idongjia.divine.mq.kafka.disruptor.factory.CategoryFactory;
import cn.idongjia.divine.mq.kafka.disruptor.factory.ZooFactory;
import cn.idongjia.divine.mq.kafka.disruptor.handler.auction.AuctionCategoryHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
public class CategoryProcessor extends BaseEventProccessor<MysqlMessage<CategoryDO>,CategoryEvent> {


    @Resource
    private AuctionCategoryHandler auctionCategoryHandler;

    public CategoryProcessor() {
        super(CategoryProcessor.class.getName());
    }

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    @Override
    protected EventFactory eventFactory() {
        return new CategoryFactory();
    }

    @Override
    public void initPipe() {
        disruptor.handleEventsWith(auctionCategoryHandler);
    }

}
