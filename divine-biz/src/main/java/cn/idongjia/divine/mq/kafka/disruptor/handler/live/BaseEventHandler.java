package cn.idongjia.divine.mq.kafka.disruptor.handler.live;

import cn.idongjia.divine.utils.disruptor.ValueWrapper;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public abstract class BaseEventHandler<E extends ValueWrapper> implements EventHandler<E>, WorkHandler<E> {

    public static final String INSERT = "insert";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";

    @Override
    public void onEvent(E event,long sequence,boolean endOfBatch) throws Exception {
        this.onEvent(event);
    }

    @Override
    public abstract void onEvent(E event) throws Exception;
}
