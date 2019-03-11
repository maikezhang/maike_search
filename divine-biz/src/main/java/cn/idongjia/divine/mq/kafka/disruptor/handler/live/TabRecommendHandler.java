//package cn.idongjia.divine.mq.kafka.disruptor.handler.live;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Component;
//
//import cn.idongjia.divine.biz.LiveLoadBO;
//import cn.idongjia.divine.db.mybatis.pojo.TabRecommendDO;
//import cn.idongjia.divine.dto.LiveDTO;
//import cn.idongjia.divine.mq.kafka.disruptor.event.TabRecommendEvent;
//import cn.idongjia.kafka.message.body.MysqlMessage;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * @author lc
// * @create at 2018/8/15.
// */
//@Component
//@Slf4j
//public class TabRecommendHandler extends BaseEventHandler<TabRecommendEvent> {
//
//    @Resource
//    private LiveLoadBO liveLoadBO;
//
//
//    @Override
//    public void onEvent(TabRecommendEvent event) throws Exception {
//        MysqlMessage<TabRecommendDO> messageBody    = event.getValue();
//        TabRecommendDO               tabRecommendDO = messageBody.getData();
//		if (tabRecommendDO.getType().equals(5)) {
//            LiveDTO liveDTO = new LiveDTO();
//            liveDTO.setRecommendWeight(tabRecommendDO.getWeight());
//            liveDTO.setId(tabRecommendDO.getId().toString());
//            liveLoadBO.update(liveDTO);
//        }
//    }
//}
