package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.divine.db.mybatis.pojo.LiveResourceDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveVideoCoverDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.LiveResourceEvent;
import cn.idongjia.divine.mq.kafka.disruptor.event.LiveVideCoverEvent;
import cn.idongjia.divine.mq.kafka.disruptor.factory.LiveResourceFactory;
import cn.idongjia.divine.mq.kafka.disruptor.factory.LiveVideoCoverFactory;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.LiveResourceHandler;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.LiveVideoCoverHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
public class LiveVideoCoverProcessor extends BaseEventProccessor<MysqlMessage<LiveVideoCoverDO>,LiveVideCoverEvent> {

    public LiveVideoCoverProcessor() {
        super(LiveVideoCoverProcessor.class.getName());
    }
    @Resource
    private LiveVideoCoverHandler liveVideoCoverHandler;

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    @Override
    protected EventFactory eventFactory() {
        return new LiveVideoCoverFactory();
    }

    @Override
    public void initPipe() {
        disruptor.handleEventsWith(liveVideoCoverHandler);
    }

}
