package cn.idongjia.divine.mq.kafka.disruptor.handler.auction.statistics;

import cn.idongjia.divine.biz.AuctionStateLoadBO;
import cn.idongjia.divine.db.mybatis.pojo.ItemExtDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.ItemExtEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/17.
 */
@Component
@Slf4j
public class AuctionStateItemExtHandler extends BaseEventHandler<ItemExtEvent> {
    @Resource
    private AuctionStateLoadBO auctionStateLoadBO;

    @Override
    public void onEvent(ItemExtEvent event) throws Exception {
        MysqlMessage<ItemExtDO> messageBody = event.getValue();
        String                  type        = messageBody.getType();
        switch(type) {
            case UPDATE:
                ItemExtDO itemExtDO = messageBody.getData();
                auctionStateLoadBO.loadById(itemExtDO.getCuid(), itemExtDO.getIid());
                break;
            default: break;

        }
    }
}
