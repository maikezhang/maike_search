package cn.idongjia.divine.mq.kafka.disruptor.handler.live;

import cn.idongjia.divine.biz.LiveSpecialCraftsmanLoadBO;
import cn.idongjia.divine.db.mybatis.pojo.LiveAnchorBlackWhiteDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.LiveAnchorBlackWhiteEvent;
import cn.idongjia.kafka.message.body.MysqlMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/9/5.
 */
@Component
public class LiveSpecialCraftsmanBlackWhiteHandler extends BaseEventHandler<LiveAnchorBlackWhiteEvent> {
    @Resource
    private LiveSpecialCraftsmanLoadBO liveSpecialCraftsmanLoadBO;

    @Override
    public void onEvent(LiveAnchorBlackWhiteEvent event) throws Exception {
        MysqlMessage<LiveAnchorBlackWhiteDO> message                = event.getValue();
        LiveAnchorBlackWhiteDO               liveAnchorBlackWhiteDO = message.getData();
        liveSpecialCraftsmanLoadBO.loadById(liveAnchorBlackWhiteDO.getAnchorId());
    }
}
