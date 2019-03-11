package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.defend.exception.SentinelExcetionHandler;
import cn.idongjia.divine.db.mybatis.pojo.CustomerDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.CustomerEvent;
import cn.idongjia.divine.mq.kafka.disruptor.factory.CustomerFactory;
import cn.idongjia.divine.mq.kafka.disruptor.handler.auction.AuctionCustomerHandler;
import cn.idongjia.divine.mq.kafka.disruptor.handler.auction.statistics.AuctionStateCustomerHandler;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.LiveCustomerHandler;
import cn.idongjia.divine.mq.kafka.disruptor.handler.session.SessionCustomerHandler;
import cn.idongjia.divine.mq.kafka.disruptor.handler.session.SessionLiveCustomerHandler;
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
public class CustomerProcessor extends BaseEventProccessor<MysqlMessage<CustomerDO>, CustomerEvent> {

    @Resource
    private LiveCustomerHandler         customerHandler;
    @Resource
    private AuctionCustomerHandler      auctionCustomerHandler;
    @Resource
    private SessionCustomerHandler      sessionCustomerHandler;
    @Resource
    private AuctionStateCustomerHandler auctionStateCustomerHandler;
    @Resource
    private SessionLiveCustomerHandler  sessionLiveCustomerHandler;

    public CustomerProcessor() {
        super(CustomerProcessor.class.getName());
    }

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    @Override
    protected EventFactory eventFactory() {
        return new CustomerFactory();
    }

    @Override
    public void initPipe() {
        disruptor.handleEventsWith(customerHandler, auctionCustomerHandler, sessionCustomerHandler, auctionStateCustomerHandler, sessionLiveCustomerHandler);
    }

    @Override
    @SentinelResource(value = "CustomerProcessor.publishEvent", entryType = EntryType.IN,
            blockHandler = "handleException", blockHandlerClass = {SentinelExcetionHandler.class})
    public synchronized void publishEvent(MysqlMessage<CustomerDO> data) {
        super.publishEvent(data);
    }
}
