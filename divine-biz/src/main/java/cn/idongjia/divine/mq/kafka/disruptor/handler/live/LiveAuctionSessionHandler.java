package cn.idongjia.divine.mq.kafka.disruptor.handler.live;

import cn.idongjia.divine.biz.LiveLoadBO;
import cn.idongjia.divine.biz.SessionLiveLoadBO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionSessionEvent;
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
public class LiveAuctionSessionHandler extends BaseEventHandler<AuctionSessionEvent> {

    @Resource
    private LiveLoadBO        liveLoadBO;
    @Resource
    private SessionLiveLoadBO sessionLiveLoadBO;

    @Override
    public void onEvent(AuctionSessionEvent event) throws Exception {
        MysqlMessage<AuctionSessionDO> messageBody      = event.getValue();
        AuctionSessionDO               auctionSessionDO = messageBody.getData();
        AuctionSessionDO               oldSessionDO     = messageBody.getOld();
        Integer                        oldLiveId        = null;
        if (oldSessionDO != null) {
            oldLiveId = oldSessionDO.getLsid();
        }
        Integer liveId = auctionSessionDO.getLsid();
        if (oldLiveId != null) {
            liveLoadBO.loadById(oldLiveId.longValue());
        }
        if (liveId != null && !liveId.equals(oldLiveId)) {
            try {
                liveLoadBO.loadById(liveId.longValue());
            } catch (Exception e) {
                log.error("update live session failed {}", e);
            }
        }
        final String type = messageBody.getType();
        switch (type) {
            case INSERT:
                break;
            case UPDATE:
                //直播拍索引更新
                sessionLiveLoadBO.loadById(auctionSessionDO.getAsid().longValue());
                break;
            default:
                break;
        }
    }
}
