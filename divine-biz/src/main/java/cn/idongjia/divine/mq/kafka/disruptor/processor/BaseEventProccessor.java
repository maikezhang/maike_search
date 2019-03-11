package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.divine.utils.disruptor.AbstractEventProcessor;
import cn.idongjia.divine.utils.disruptor.ValueWrapper;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.LiteTimeoutBlockingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.TimeUnit;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public abstract class BaseEventProccessor<D,E extends ValueWrapper<D>> extends AbstractEventProcessor<D,E> implements InitializingBean {
    private static final int QUEUE_SIZE = 128;

    public BaseEventProccessor(String name) {
        super(name);
    }

    /**
     * 队列大小
     *
     * @return 队列长度，必须是2的幂
     */
    @Override
    protected int getQueueSize() {
        return QUEUE_SIZE;
    }

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    @Override
    protected abstract EventFactory eventFactory();

    @Override
    public abstract void initPipe();


    /**
     * 如果要改变线程执行优先级，override此策略. YieldingWaitStrategy会提高响应并在闲时占用70%以上CPU，
     * 慎用SleepingWaitStrategy会降低响应更减少CPU占用，用于日志等场景.
     *
     * @return WaitStrategy
     */
    @Override
    protected WaitStrategy getStrategy() {
        return new LiteTimeoutBlockingWaitStrategy(10,TimeUnit.SECONDS);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}
