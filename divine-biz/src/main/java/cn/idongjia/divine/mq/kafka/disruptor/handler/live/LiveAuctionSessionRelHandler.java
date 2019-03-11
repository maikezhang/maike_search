package cn.idongjia.divine.mq.kafka.disruptor.handler.live;

import cn.idongjia.divine.biz.LiveLoadBO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionRelDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionSessionRelEvent;
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
public class LiveAuctionSessionRelHandler extends BaseEventHandler<AuctionSessionRelEvent> {

    @Resource
    private LiveLoadBO liveLoadBO;

    @Override
    public void onEvent(AuctionSessionRelEvent event) throws Exception {
        MysqlMessage<AuctionSessionRelDO> messageBody         = event.getValue();
        AuctionSessionRelDO               auctionSessionRelDO = messageBody.getData();
        Long                              liveId              = liveLoadBO.getLiveBySessionId(auctionSessionRelDO.getAsid().longValue());
        String                            type                = messageBody.getType();
        switch(type) {
            case UPDATE:
            case INSERT:
            case DELETE:
                if(liveId != null) {
                    liveLoadBO.loadById(liveId);
                }
                break;

            default:
                break;

        }
    }
}
