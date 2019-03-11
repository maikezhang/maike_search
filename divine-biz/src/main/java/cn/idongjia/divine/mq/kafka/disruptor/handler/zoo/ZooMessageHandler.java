package cn.idongjia.divine.mq.kafka.disruptor.handler.zoo;

import cn.idongjia.divine.biz.ZooMessageLoadBO;
import cn.idongjia.divine.cache.ZooMessageUpdateOffsetCache;
import cn.idongjia.divine.db.mybatis.pojo.ZooMessageDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.ZooMessageEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/11/22
 * Time: 下午3:20
 */
@Component
@Slf4j
public class ZooMessageHandler extends BaseEventHandler<ZooMessageEvent> {

    @Resource
    private ZooMessageUpdateOffsetCache cache;

    @Override
    public void onEvent(ZooMessageEvent event) throws Exception {
        MysqlMessage<ZooMessageDO> messageBody  = event.getValue();
        String                     type         = messageBody.getType();
        ZooMessageDO               zooMessageDO = messageBody.getData();

        switch (type){
            case INSERT:
            case UPDATE:
                cache.lpushMessageOffset(zooMessageDO.getZmid());
                break;
            default:
                break;
        }
    }
}
