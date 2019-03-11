package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.divine.db.mybatis.pojo.LiveResourceDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.LiveResourceEvent;
import cn.idongjia.divine.mq.kafka.disruptor.factory.LiveResourceFactory;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.LiveResourceHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
public class LiveResourceProcessor extends BaseEventProccessor<MysqlMessage<LiveResourceDO>,LiveResourceEvent> {

    public LiveResourceProcessor() {
        super(LiveResourceProcessor.class.getName());
    }
    @Resource
    private LiveResourceHandler liveResourceHandler;

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    @Override
    protected EventFactory eventFactory() {
        return new LiveResourceFactory();
    }

    @Override
    public void initPipe() {
        disruptor.handleEventsWith(liveResourceHandler);
    }

}
