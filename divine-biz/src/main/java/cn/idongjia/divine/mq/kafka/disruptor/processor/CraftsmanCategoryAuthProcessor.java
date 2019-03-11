package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.divine.db.mybatis.pojo.CraftsmanCategoryAuthDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.CraftsmanCategoryAuthEvent;
import cn.idongjia.divine.mq.kafka.disruptor.factory.CraftsmanCategoryAuthFactory;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.CraftsmanCategoryAuthHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
public class CraftsmanCategoryAuthProcessor extends BaseEventProccessor<MysqlMessage<CraftsmanCategoryAuthDO>,CraftsmanCategoryAuthEvent> {


    @Resource
    private CraftsmanCategoryAuthHandler craftsmanCategoryAuthHandler;

    public CraftsmanCategoryAuthProcessor() {
        super(CraftsmanCategoryAuthProcessor.class.getName());
    }

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    @Override
    protected EventFactory eventFactory() {
        return new CraftsmanCategoryAuthFactory();
    }

    @Override
    public void initPipe() {
        disruptor.handleEventsWith(craftsmanCategoryAuthHandler);
    }


}
