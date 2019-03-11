package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.divine.db.mybatis.pojo.LiveAnchorBlackWhiteDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.LiveAnchorBlackWhiteEvent;
import cn.idongjia.divine.mq.kafka.disruptor.factory.LiveAnchorBlackWhiteFactory;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.LiveAnchorBlackWhiteHandler;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.LiveSpecialCraftsmanBlackWhiteHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
public class LiveAnchorBlackWhiteProcessor extends BaseEventProccessor<MysqlMessage<LiveAnchorBlackWhiteDO>,LiveAnchorBlackWhiteEvent> {

    @Resource
    private LiveAnchorBlackWhiteHandler           liveAnchorBlackWhiteHandler;
    @Resource
    private LiveSpecialCraftsmanBlackWhiteHandler liveSpecialCraftsmanBlackWhiteHandler;

    public LiveAnchorBlackWhiteProcessor() {
        super(LiveAnchorBlackWhiteProcessor.class.getName());
    }

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    @Override
    protected EventFactory eventFactory() {
        return new LiveAnchorBlackWhiteFactory();
    }

    @Override
    public void initPipe() {
        disruptor.handleEventsWith(liveAnchorBlackWhiteHandler,liveSpecialCraftsmanBlackWhiteHandler);
    }

}
