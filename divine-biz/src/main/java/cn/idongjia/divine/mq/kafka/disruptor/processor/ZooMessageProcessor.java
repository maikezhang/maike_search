package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.defend.exception.SentinelExcetionHandler;
import cn.idongjia.divine.db.mybatis.pojo.ZooMessageDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.ZooMessageEvent;
import cn.idongjia.divine.mq.kafka.disruptor.factory.ZooMessageFactory;
import cn.idongjia.divine.mq.kafka.disruptor.handler.zoo.ZooMessageHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/11/22
 * Time: 下午3:21
 */
@Component
public class ZooMessageProcessor extends BaseEventProccessor<MysqlMessage<ZooMessageDO>,ZooMessageEvent> {

    public ZooMessageProcessor(){
        super(ZooMessageProcessor.class.getName());
    }

    @Resource
    private ZooMessageHandler zooMessageHandler;

    @Override
    protected EventFactory eventFactory() {
        return new ZooMessageFactory();
    }

    @Override
    public void initPipe() {
        disruptor.handleEventsWith(zooMessageHandler);
    }

    @Override
    @SentinelResource(value = "ZooMessageProcessor.publishEvent", entryType = EntryType.IN,
            blockHandler = "handleException", blockHandlerClass = {SentinelExcetionHandler.class})
    public synchronized void publishEvent(MysqlMessage<ZooMessageDO> data) {
        super.publishEvent(data);
    }
}
