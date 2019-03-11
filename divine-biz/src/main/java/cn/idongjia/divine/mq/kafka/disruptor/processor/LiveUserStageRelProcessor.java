package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.divine.db.mybatis.pojo.LiveUserStageRelDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.UserStageRelEvent;
import cn.idongjia.divine.mq.kafka.disruptor.factory.LiveUserStageRelFactory;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.LiveUserStageRelHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
public class LiveUserStageRelProcessor extends BaseEventProccessor<MysqlMessage<LiveUserStageRelDO>,UserStageRelEvent> {

    public LiveUserStageRelProcessor() {
        super(LiveUserStageRelProcessor.class.getName());
    }
    @Resource
    private LiveUserStageRelHandler liveUserStageRelHandler;

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    @Override
    protected EventFactory eventFactory() {
        return new LiveUserStageRelFactory();
    }

    @Override
    public void initPipe() {
        disruptor.handleEventsWith(liveUserStageRelHandler);
    }

}
