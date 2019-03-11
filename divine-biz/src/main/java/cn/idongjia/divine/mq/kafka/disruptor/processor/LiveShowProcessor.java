package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.divine.db.mybatis.pojo.LiveShowDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.LiveShowEvent;
import cn.idongjia.divine.mq.kafka.disruptor.factory.LiveShowFactotry;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.LiveShowHandler;
import cn.idongjia.divine.mq.kafka.disruptor.handler.session.SessionLiveHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
public class LiveShowProcessor extends BaseEventProccessor<MysqlMessage<LiveShowDO>,LiveShowEvent> {

    public LiveShowProcessor() {
        super(LiveShowProcessor.class.getName());
    }

    @Resource
    private LiveShowHandler    liveShowHandler;
    @Resource
    private SessionLiveHandler sessionLiveHandler;

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    @Override
    protected EventFactory eventFactory() {
        return new LiveShowFactotry();
    }

    @Override
    public void initPipe() {
        disruptor.handleEventsWith(liveShowHandler,sessionLiveHandler);
    }

}
