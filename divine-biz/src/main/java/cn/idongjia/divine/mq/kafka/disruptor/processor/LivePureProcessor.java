package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.divine.db.mybatis.pojo.LivePureDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.LivePureEvent;
import cn.idongjia.divine.mq.kafka.disruptor.factory.LivePureFactory;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.LivePureHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
public class LivePureProcessor extends BaseEventProccessor<MysqlMessage<LivePureDO>,LivePureEvent> {


    @Resource
    private LivePureHandler livePureHandler;
    public LivePureProcessor() {
        super(LivePureProcessor.class.getName());
    }
    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    @Override
    protected EventFactory eventFactory() {
        return new  LivePureFactory();
    }

    @Override
    public void initPipe() {
        disruptor.handleEventsWith(livePureHandler);
    }

}
