package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.divine.db.mybatis.pojo.MediaDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.MediaEvent;
import cn.idongjia.divine.mq.kafka.disruptor.factory.MediaFactory;
import cn.idongjia.divine.mq.kafka.disruptor.handler.auction.AuctionMediaHandler;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.LiveMediaHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
public class MediaProcesser extends BaseEventProccessor<MysqlMessage<MediaDO>,MediaEvent> {
    public MediaProcesser() {
        super(MediaProcesser.class.getName());
    }
    @Resource
    private AuctionMediaHandler auctionMediaHandler;
    @Resource
    private LiveMediaHandler    liveMediaHandler;

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    @Override
    protected EventFactory eventFactory() {
        return new MediaFactory();
    }

    @Override
    public void initPipe() {
        disruptor.handleEventsWith(auctionMediaHandler,liveMediaHandler);
    }

}
