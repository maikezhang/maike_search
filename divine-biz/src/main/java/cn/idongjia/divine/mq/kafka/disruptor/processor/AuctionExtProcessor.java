package cn.idongjia.divine.mq.kafka.disruptor.processor;

import cn.idongjia.divine.db.mybatis.pojo.AuctionExtDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionExtEvent;
import cn.idongjia.divine.mq.kafka.disruptor.factory.AuctionExtFactory;
import cn.idongjia.divine.mq.kafka.disruptor.handler.auction.AuctionExtHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.lmax.disruptor.EventFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yuexiaodong@idongjia.cn
 * @create at 2018/12/18.
 */
@Component
@Slf4j
public class AuctionExtProcessor extends BaseEventProccessor<MysqlMessage<AuctionExtDO>, AuctionExtEvent> {

    @Resource
    private AuctionExtHandler auctionExtHandler;

    public AuctionExtProcessor() {
        super(AuctionExtProcessor.class.getName());
    }

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    @Override
    protected EventFactory eventFactory() {
        return new AuctionExtFactory();
    }

    @Override
    public void initPipe() {
        disruptor.handleEventsWith(auctionExtHandler);
    }
}
