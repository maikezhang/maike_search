package cn.idongjia.divine.mq.kafka.disruptor.handler.live;

import cn.idongjia.divine.biz.LiveLoadBO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionBookDO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionDO;
import cn.idongjia.divine.lib.pojo.Conf;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionBookEvent;
import cn.idongjia.divine.repository.AuctionSessionRepositoryI;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
@Slf4j
public class LiveAuctionBookHandler extends BaseEventHandler<AuctionBookEvent> {
    @Resource
    private LiveLoadBO                liveLoadBO;
    @Resource
    private AuctionSessionRepositoryI auctionSessionRepository;

    @Override
    public void onEvent(AuctionBookEvent event) throws Exception {
        MysqlMessage<AuctionBookDO> messageBody   = event.getValue();
        AuctionBookDO               auctionBookDO = messageBody.getData();
        if(auctionBookDO.getContentType().intValue() == Conf.SESSION_BOOK_TYPE) {
            Long             sessionId = auctionBookDO.getContentId();
            AuctionSessionDO sessionDO = auctionSessionRepository.getByPrimaryKey(sessionId.intValue());
            if(sessionDO != null && sessionDO.getLsid() != null) {
                liveLoadBO.loadById(sessionDO.getLsid().longValue());
            }
        }
    }
}
