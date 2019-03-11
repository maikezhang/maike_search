package cn.idongjia.divine.mq.kafka.listener;

import cn.idongjia.divine.db.mybatis.pojo.AuctionBookDO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionDO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionManualRecommendDO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionOfferDO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionDO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionRelDO;
import cn.idongjia.divine.db.mybatis.pojo.CategoryDO;
import cn.idongjia.divine.db.mybatis.pojo.CraftsmanCategoryAuthDO;
import cn.idongjia.divine.db.mybatis.pojo.CraftsmanDO;
import cn.idongjia.divine.db.mybatis.pojo.CustomerDO;
import cn.idongjia.divine.db.mybatis.pojo.ItemDO;
import cn.idongjia.divine.db.mybatis.pojo.ItemExtDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveAnchorBlackWhiteDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveBookDO;
import cn.idongjia.divine.db.mybatis.pojo.LivePlaybackDO;
import cn.idongjia.divine.db.mybatis.pojo.LivePureDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveResourceDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveShowDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveUserStageRelDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveVideoCoverDO;
import cn.idongjia.divine.db.mybatis.pojo.MediaDO;
import cn.idongjia.divine.db.mybatis.pojo.ZooDO;
import cn.idongjia.divine.db.mybatis.pojo.ZooMessageDO;
import cn.idongjia.divine.mq.kafka.disruptor.processor.AuctionBookProcessor;
import cn.idongjia.divine.mq.kafka.disruptor.processor.AuctionExtProcessor;
import cn.idongjia.divine.mq.kafka.disruptor.processor.AuctionManualRecommendProcessor;
import cn.idongjia.divine.mq.kafka.disruptor.processor.AuctionOfferProcesser;
import cn.idongjia.divine.mq.kafka.disruptor.processor.AuctionProcessor;
import cn.idongjia.divine.mq.kafka.disruptor.processor.AuctionSessionProcessor;
import cn.idongjia.divine.mq.kafka.disruptor.processor.AuctionSessionRelProcessor;
import cn.idongjia.divine.mq.kafka.disruptor.processor.CategoryProcessor;
import cn.idongjia.divine.mq.kafka.disruptor.processor.CraftsmanCategoryAuthProcessor;
import cn.idongjia.divine.mq.kafka.disruptor.processor.CraftsmanProcessor;
import cn.idongjia.divine.mq.kafka.disruptor.processor.CustomerProcessor;
import cn.idongjia.divine.mq.kafka.disruptor.processor.ItemExtProcessor;
import cn.idongjia.divine.mq.kafka.disruptor.processor.ItemProcessor;
import cn.idongjia.divine.mq.kafka.disruptor.processor.LiveAnchorBlackWhiteProcessor;
import cn.idongjia.divine.mq.kafka.disruptor.processor.LiveBookProcessor;
import cn.idongjia.divine.mq.kafka.disruptor.processor.LivePureProcessor;
import cn.idongjia.divine.mq.kafka.disruptor.processor.LiveResourceProcessor;
import cn.idongjia.divine.mq.kafka.disruptor.processor.LiveShowProcessor;
import cn.idongjia.divine.mq.kafka.disruptor.processor.LiveUserStageRelProcessor;
import cn.idongjia.divine.mq.kafka.disruptor.processor.LiveVideoCoverProcessor;
import cn.idongjia.divine.mq.kafka.disruptor.processor.MediaProcesser;
import cn.idongjia.divine.mq.kafka.disruptor.processor.PlayBackProcessor;
import cn.idongjia.divine.mq.kafka.disruptor.processor.ZooMessageProcessor;
import cn.idongjia.divine.mq.kafka.disruptor.processor.ZooProcesser;
import cn.idongjia.kafka.annocation.KafkaListener;
import cn.idongjia.kafka.annocation.TopicHandler;
import cn.idongjia.kafka.message.Message;
import cn.idongjia.kafka.message.body.MysqlMessage;
import cn.idongjia.log.Log;
import cn.idongjia.log.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/7/30.
 */
@Component
@KafkaListener
public class BinLogListener {
    protected static final Log    logger       = LogFactory.getLog(BinLogListener.class);
    public static final    String KAIPAO_TOPIC = "kaipao__";
    public static final    String USER_TOPIC   = "user__";
    public static final    String ZOO_TOPIC    = "zoo__";

    @Resource
    private LiveShowProcessor               liveShowProcessor;
    @Resource
    private AuctionSessionProcessor         auctionSessionProcessor;
    @Resource
    private LivePureProcessor               livePureProcessor;
    @Resource
    private AuctionSessionRelProcessor      auctionSessionRelProcessor;
    @Resource
    private CraftsmanProcessor              craftsmanProcessor;
    @Resource
    private CustomerProcessor               customerProcessor;
    @Resource
    private PlayBackProcessor               playBackProcessor;
    @Resource
    private LiveResourceProcessor           liveResourceProcessor;
    //    @Resource
//    private                TabRecommendProcessor           tabRecommendProcessor;
    @Resource
    private LiveVideoCoverProcessor         liveVideoCoverProcessor;
    @Resource
    private ZooProcesser                    zooProcesser;
    @Resource
    private LiveBookProcessor               liveBookProcessor;
    @Resource
    private AuctionBookProcessor            auctionBookProcessor;
    @Resource
    private CraftsmanCategoryAuthProcessor  craftsmanCategoryAuthProcessor;
    @Resource
    private LiveUserStageRelProcessor       liveUserStageRelProcessor;
    @Resource
    private CategoryProcessor               categoryProcessor;
    @Resource
    private ItemProcessor                   itemProcessor;
    @Resource
    private ItemExtProcessor                itemExtProcessor;
    @Resource
    private AuctionProcessor                auctionProcessor;
    @Resource
    private AuctionOfferProcesser           auctionOfferProcesser;
    @Resource
    private AuctionManualRecommendProcessor auctionManualRecommendProcessor;
    @Resource
    private MediaProcesser                  mediaProcesser;
    @Resource
    private LiveAnchorBlackWhiteProcessor   liveAnchorBlackWhiteProcessor;
    @Resource
    private ZooMessageProcessor             zooMessageProcessor;
    @Resource
    private AuctionExtProcessor             auctionExtProcessor;


    @TopicHandler(topic = KAIPAO_TOPIC + "live_show")
    public void liveShowConsumer(Message<MysqlMessage<LiveShowDO>> message) {
        long                     start       = System.currentTimeMillis();
        MysqlMessage<LiveShowDO> messageBody = message.getBody();
        liveShowProcessor.publishEvent(messageBody);
        logger.info("cn.idongjia.divine.biz.GeneralLiveLoadBO.loadById spend {} ms", System.currentTimeMillis() - start);

    }

    @TopicHandler(topic = KAIPAO_TOPIC + "kp_auction_session")
    public void auctionSessionConsumer(Message<MysqlMessage<AuctionSessionDO>> message) {
        MysqlMessage<AuctionSessionDO> messageBody = message.getBody();
        auctionSessionProcessor.publishEvent(messageBody);
    }

    @TopicHandler(topic = KAIPAO_TOPIC + "live_pure")
    public void livePureConsumer(Message<MysqlMessage<LivePureDO>> message) {
        MysqlMessage<LivePureDO> messageBody = message.getBody();
        livePureProcessor.publishEvent(messageBody);
    }

    @TopicHandler(topic = KAIPAO_TOPIC + "kp_auction_session_rel")
    public void auctionSessionRelConsumer(Message<MysqlMessage<AuctionSessionRelDO>> message) {
        MysqlMessage<AuctionSessionRelDO> messageBody = message.getBody();
        auctionSessionRelProcessor.publishEvent(messageBody);
    }

    @TopicHandler(topic = USER_TOPIC + "craftsman")
    public void craftsmanConsumer(Message<MysqlMessage<CraftsmanDO>> message) {
        MysqlMessage<CraftsmanDO> messageBody = message.getBody();
        craftsmanProcessor.publishEvent(messageBody);

    }

    @TopicHandler(topic = USER_TOPIC + "customer")
    public void customerConsumer(Message<MysqlMessage<CustomerDO>> message) {
        MysqlMessage<CustomerDO> messageBody = message.getBody();
        customerProcessor.publishEvent(messageBody);
    }

    @TopicHandler(topic = KAIPAO_TOPIC + "live_playback")
    public void playbackConsumer(Message<MysqlMessage<LivePlaybackDO>> message) {
        MysqlMessage<LivePlaybackDO> messageBody = message.getBody();
        playBackProcessor.publishEvent(messageBody);
    }

    @TopicHandler(topic = KAIPAO_TOPIC + "live_resource")
    public void liveResourceConsumer(Message<MysqlMessage<LiveResourceDO>> message) {
        MysqlMessage<LiveResourceDO> messageBody = message.getBody();
        liveResourceProcessor.publishEvent(messageBody);
    }

    @TopicHandler(topic = KAIPAO_TOPIC + "live_video_cover")
    public void videoCoverConsumer(Message<MysqlMessage<LiveVideoCoverDO>> message) {
        MysqlMessage<LiveVideoCoverDO> messageBody = message.getBody();
        liveVideoCoverProcessor.publishEvent(messageBody);
    }

//    @TopicHandler(topic = KAIPAO_TOPIC + "kp_tab_recommend")
//    public void tabRecommendConsumer(Message<MysqlMessage<TabRecommendDO>> message) {
//        MysqlMessage<TabRecommendDO> messageBody = message.getBody();
//        tabRecommendProcessor.publishEvent(messageBody);
//    }

    @TopicHandler(topic = ZOO_TOPIC + "zoo")
    public void zooConsumer(Message<MysqlMessage<ZooDO>> message) {
        MysqlMessage<ZooDO> messageBody = message.getBody();
        zooProcesser.publishEvent(messageBody);
    }

    @TopicHandler(topic = KAIPAO_TOPIC + "live_pure_book")
    public void liveBookConsumer(Message<MysqlMessage<LiveBookDO>> message) {
        MysqlMessage<LiveBookDO> messageBody = message.getBody();
        liveBookProcessor.publishEvent(messageBody);
    }

    @TopicHandler(topic = KAIPAO_TOPIC + "auction_book")
    public void auctionBookConsumer(Message<MysqlMessage<AuctionBookDO>> message) {
        MysqlMessage<AuctionBookDO> messageBody = message.getBody();
        auctionBookProcessor.publishEvent(messageBody);
    }

    @TopicHandler(topic = KAIPAO_TOPIC + "dj_craftsman_category_auth")
    public void craftsmanCategoryConsumer(Message<MysqlMessage<CraftsmanCategoryAuthDO>> message) {
        MysqlMessage<CraftsmanCategoryAuthDO> messageBody = message.getBody();
        craftsmanCategoryAuthProcessor.publishEvent(messageBody);
    }

    @TopicHandler(topic = KAIPAO_TOPIC + "live_user_stage_rel")
    public void userStageConsumer(Message<MysqlMessage<LiveUserStageRelDO>> message) {
        MysqlMessage<LiveUserStageRelDO> messageBody = message.getBody();
        liveUserStageRelProcessor.publishEvent(messageBody);
    }

    @TopicHandler(topic = KAIPAO_TOPIC + "dj_category")
    public void categoryConsumer(Message<MysqlMessage<CategoryDO>> message) {
        MysqlMessage<CategoryDO> messageBody = message.getBody();
        categoryProcessor.publishEvent(messageBody);
    }

    @TopicHandler(topic = KAIPAO_TOPIC + "kp_auction")
    public void auctionConsumer(Message<MysqlMessage<AuctionDO>> message) {
        MysqlMessage<AuctionDO> messageBody = message.getBody();
        auctionProcessor.publishEvent(messageBody);
    }

    @TopicHandler(topic = KAIPAO_TOPIC + "kp_item")
    public void itemConsumer(Message<MysqlMessage<ItemDO>> message) {
        MysqlMessage<ItemDO> messageBody = message.getBody();
        itemProcessor.publishEvent(messageBody);
    }

    @TopicHandler(topic = KAIPAO_TOPIC + "kp_item_ext")
    public void itemExtConsumer(Message<MysqlMessage<ItemExtDO>> message) {
        MysqlMessage<ItemExtDO> messageBody = message.getBody();
        itemExtProcessor.publishEvent(messageBody);
    }

    @TopicHandler(topic = KAIPAO_TOPIC + "auction_offer")
    public void offerConsumer(Message<MysqlMessage<AuctionOfferDO>> message) {
        MysqlMessage<AuctionOfferDO> messageBody = message.getBody();
        auctionOfferProcesser.publishEvent(messageBody);
    }

    @TopicHandler(topic = KAIPAO_TOPIC + "media")
    public void mediaConsomer(Message<MysqlMessage<MediaDO>> message) {
        MysqlMessage<MediaDO> messageBody = message.getBody();
        mediaProcesser.publishEvent(messageBody);
    }

    @TopicHandler(topic = KAIPAO_TOPIC + "auction_manual_recommend")
    public void auctionManualRecommendConsumer(Message<MysqlMessage<AuctionManualRecommendDO>> message) {
        MysqlMessage<AuctionManualRecommendDO> messageBody = message.getBody();
        auctionManualRecommendProcessor.publishEvent(messageBody);
    }

    @TopicHandler(topic = KAIPAO_TOPIC + "live_anchor_black_white")
    public void liveAnchorBlackWhiteConsumer(Message<MysqlMessage<LiveAnchorBlackWhiteDO>> message) {
        MysqlMessage<LiveAnchorBlackWhiteDO> messageBody = message.getBody();
        liveAnchorBlackWhiteProcessor.publishEvent(messageBody);
    }

    @TopicHandler(topic = ZOO_TOPIC + "zoo_message")
    public void zooMessageConsumer(Message<MysqlMessage<ZooMessageDO>> message) {
        MysqlMessage<ZooMessageDO> messageBody = message.getBody();
        zooMessageProcessor.publishEvent(messageBody);
    }

    /**
     *
     */
//    @TopicHandler(topic = KAIPAO_TOPIC + "auction_ext")
//    public void auctionExtMessageConsumer(Message<MysqlMessage<AuctionExtDO>> message) {
//        final MysqlMessage<AuctionExtDO> body = message.getBody();
//        auctionExtProcessor.publishEvent(body);
//    }

}
