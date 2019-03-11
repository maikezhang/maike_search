package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.divine.db.mybatis.pojo.LivePlaybackDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.PlayBackEvent;
import cn.idongjia.divine.mq.kafka.disruptor.factory.LiveShowFactotry;
import cn.idongjia.divine.mq.kafka.disruptor.factory.PlayBackFactory;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.PlayBackHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
public class PlayBackProcessor extends BaseEventProccessor<MysqlMessage<LivePlaybackDO>,PlayBackEvent> {
    public PlayBackProcessor() {
        super(PlayBackProcessor.class.getName());
    }

    @Resource
    private PlayBackHandler playBackHandler;

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    @Override
    protected EventFactory eventFactory() {
        return new PlayBackFactory();
    }

    @Override
    public void initPipe() {
        disruptor.handleEventsWith(playBackHandler);
    }

}
