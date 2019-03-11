package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.defend.exception.SentinelExcetionHandler;
import cn.idongjia.divine.db.mybatis.pojo.CraftsmanDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.CraftsmanEvent;
import cn.idongjia.divine.mq.kafka.disruptor.factory.CraftsmanFactory;
import cn.idongjia.divine.mq.kafka.disruptor.handler.auction.AuctionCraftsmanHandler;
import cn.idongjia.divine.mq.kafka.disruptor.handler.auction.statistics.AuctionStateCraftsmanHandler;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.CraftsmanHandler;
import cn.idongjia.divine.mq.kafka.disruptor.handler.session.SessionLiveCraftsmanHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
public class CraftsmanProcessor extends BaseEventProccessor<MysqlMessage<CraftsmanDO>, CraftsmanEvent> {

    @Resource
    private CraftsmanHandler             craftsmanHandler;
    @Resource
    private AuctionStateCraftsmanHandler auctionStateCraftsmanHandler;
    @Resource
    private AuctionCraftsmanHandler      auctionCraftsmanHandler;
    @Resource
    private SessionLiveCraftsmanHandler  sessionLiveCraftsmanHandler;

    public CraftsmanProcessor() {
        super(CraftsmanProcessor.class.getName());
    }

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    @Override
    protected EventFactory eventFactory() {
        return new CraftsmanFactory();
    }

    @Override
    public void initPipe() {
        disruptor.handleEventsWith(craftsmanHandler, auctionStateCraftsmanHandler,
                auctionCraftsmanHandler, sessionLiveCraftsmanHandler);
    }

    @Override
    @SentinelResource(value = "CraftsmanProcessor.publishEvent", entryType = EntryType.IN,
            blockHandler = "handleException", blockHandlerClass = {SentinelExcetionHandler.class})
    public synchronized void publishEvent(MysqlMessage<CraftsmanDO> data) {
        super.publishEvent(data);
    }
}
