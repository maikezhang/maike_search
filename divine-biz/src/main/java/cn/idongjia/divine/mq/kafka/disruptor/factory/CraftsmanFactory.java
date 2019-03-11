package cn.idongjia.divine.mq.kafka.disruptor.factory;

import cn.idongjia.divine.db.mybatis.pojo.CraftsmanDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.CraftsmanEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public class CraftsmanFactory implements EventFactory<CraftsmanEvent> {
    @Override
    public CraftsmanEvent newInstance() {
        return new CraftsmanEvent();
    }
}
