package cn.idongjia.divine.mq.kafka.disruptor.handler.auction.statistics;

import cn.idongjia.divine.biz.AuctionStateLoadBO;
import cn.idongjia.divine.db.mybatis.pojo.CustomerDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.CustomerEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
@Slf4j
public class AuctionStateCustomerHandler extends BaseEventHandler<CustomerEvent> {

    @Resource
    private AuctionStateLoadBO auctionStateLoadBO;

    @Override
    public void onEvent(CustomerEvent event) throws Exception {
        MysqlMessage<CustomerDO> messageBody = event.getValue();
        String                   type        = messageBody.getType();
        CustomerDO               customerDO  = messageBody.getData();
        switch(type) {
            case UPDATE:
                auctionStateLoadBO.loadById(customerDO.getMainUserId().longValue());
                break;
            default:
                break;
        }
    }
}
