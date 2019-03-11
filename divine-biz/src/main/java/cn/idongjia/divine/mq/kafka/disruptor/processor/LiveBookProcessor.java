package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.divine.db.mybatis.pojo.LiveBookDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.LiveBookEvent;
import cn.idongjia.divine.mq.kafka.disruptor.factory.LiveBookFactory;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.LiveBookHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
public class LiveBookProcessor extends BaseEventProccessor<MysqlMessage<LiveBookDO>,LiveBookEvent> {


    @Resource
    private LiveBookHandler liveBookHandler;

    public LiveBookProcessor() {
        super(LiveBookProcessor.class.getName());
    }

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    @Override
    protected EventFactory eventFactory() {
        return  new LiveBookFactory();
    }

    @Override
    public void initPipe() {
        disruptor.handleEventsWith(liveBookHandler);
    }

}
