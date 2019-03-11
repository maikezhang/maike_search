//package cn.idongjia.divine.mq.kafka.disruptor.processor;
//
//import cn.idongjia.divine.db.mybatis.pojo.LivePlaybackDO;
//import cn.idongjia.divine.db.mybatis.pojo.TabRecommendDO;
//import cn.idongjia.divine.mq.kafka.disruptor.event.PlayBackEvent;
//import cn.idongjia.divine.mq.kafka.disruptor.event.TabRecommendEvent;
//import cn.idongjia.divine.mq.kafka.disruptor.factory.PlayBackFactory;
//import cn.idongjia.divine.mq.kafka.disruptor.factory.TabRecommendFactory;
//import cn.idongjia.divine.mq.kafka.disruptor.handler.live.PlayBackHandler;
//import cn.idongjia.divine.mq.kafka.disruptor.handler.live.TabRecommendHandler;
//import cn.idongjia.kafka.message.body.MysqlMessage;
//import com.lmax.disruptor.EventFactory;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
///**
// * @author lc
// * @create at 2018/8/15.
// */
//@Component
//public class TabRecommendProcessor extends BaseEventProccessor<MysqlMessage<TabRecommendDO>,TabRecommendEvent> {
//
//
//    @Resource
//    private TabRecommendHandler tabRecommendHandler;
//
//    public TabRecommendProcessor() {
//        super(TabRecommendProcessor.class.getName());
//    }
//
//    /**
//     * 事件工厂
//     *
//     * @return EventFactory
//     */
//    @Override
//    protected EventFactory eventFactory() {
//        return new TabRecommendFactory();
//    }
//
//    @Override
//    public void initPipe() {
//        disruptor.handleEventsWith(tabRecommendHandler);
//    }
//
//}
