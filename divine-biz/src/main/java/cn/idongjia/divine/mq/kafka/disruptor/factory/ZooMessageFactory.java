package cn.idongjia.divine.mq.kafka.disruptor.factory;

import cn.idongjia.divine.mq.kafka.disruptor.event.ZooMessageEvent;
import com.lmax.disruptor.EventFactory;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/11/22
 * Time: 下午3:17
 */
public class ZooMessageFactory implements EventFactory<ZooMessageEvent> {
    @Override
    public ZooMessageEvent newInstance() {
        return new ZooMessageEvent();
    }
}
