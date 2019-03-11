package cn.idongjia.divine.mq.kafka.disruptor.handler.live;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.idongjia.divine.assembler.LiveAssembler;
import cn.idongjia.divine.biz.LiveLoadBO;
import cn.idongjia.divine.db.mybatis.pojo.LiveShowDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.LiveShowEvent;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
@Slf4j
public class LiveShowHandler extends BaseEventHandler<LiveShowEvent> {
    @Resource
    private LiveLoadBO    liveLoadBO;




    @Override
    public void onEvent(LiveShowEvent event) throws Exception {
        MysqlMessage<LiveShowDO> messageBody = event.getValue();
        String                   type        = messageBody.getType();
        LiveShowDO               liveShowDO  = messageBody.getData();
        switch(type) {
            case INSERT:
			case UPDATE:
                liveLoadBO.loadById(liveShowDO.getId());
                break;
            default:
                break;
        }
//        log.info("listen kaipao__live_show data change : update {}",liveShowDO);
    }
}
