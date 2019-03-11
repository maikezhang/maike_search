package cn.idongjia.divine.mq.kafka.disruptor.handler.live;

import cn.idongjia.divine.biz.LiveLoadBO;
import cn.idongjia.divine.db.mybatis.pojo.LiveUserStageRelDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.UserStageRelEvent;
import cn.idongjia.divine.repository.LiveUserStageRelRepositoryI;
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
public class LiveUserStageRelHandler extends BaseEventHandler<UserStageRelEvent> {

    @Resource
    private LiveLoadBO                  liveLoadBO;


    @Override
    public void onEvent(UserStageRelEvent event) throws Exception {
        MysqlMessage<LiveUserStageRelDO> messageBody    = event.getValue();
        LiveUserStageRelDO               userStageRelDO = messageBody.getData();
        Long                             liveId         = userStageRelDO.getLiveId();
        liveLoadBO.loadById(liveId);
    }

}
