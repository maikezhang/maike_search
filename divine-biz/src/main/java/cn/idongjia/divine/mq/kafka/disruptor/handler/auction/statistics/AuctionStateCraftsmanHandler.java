package cn.idongjia.divine.mq.kafka.disruptor.handler.auction.statistics;

import cn.idongjia.divine.biz.AuctionStateLoadBO;
import cn.idongjia.divine.db.mybatis.pojo.CraftsmanDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.CraftsmanEvent;
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
public class AuctionStateCraftsmanHandler extends BaseEventHandler<CraftsmanEvent> {

    @Resource
    private AuctionStateLoadBO auctionStateLoadBO;

    @Override
    public void onEvent(CraftsmanEvent event) throws Exception {
        MysqlMessage<CraftsmanDO> messageBody = event.getValue();
        CraftsmanDO               craftsmanDO = messageBody.getData();
        String                    type        = messageBody.getType();
        switch(type) {
            case UPDATE:
                auctionStateLoadBO.loadById(craftsmanDO.getCustomerId());
                break;
            default:
                break;
        }
    }
}
