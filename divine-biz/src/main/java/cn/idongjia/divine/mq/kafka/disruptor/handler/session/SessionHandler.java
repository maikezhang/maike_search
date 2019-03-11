package cn.idongjia.divine.mq.kafka.disruptor.handler.session;

import cn.idongjia.divine.biz.SessionLoadBO;
import cn.idongjia.divine.db.es.entity.SessionEntity;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveShowDO;
import cn.idongjia.divine.lib.pojo.Conf;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionSessionEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.divine.repository.LiveShowRepositoryI;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/9/3.
 */
@Component
@Slf4j
public class SessionHandler extends BaseEventHandler<AuctionSessionEvent> {
    @Resource
    private SessionLoadBO       sessionLoadBO;
    @Resource
    private ESUpsertManager     esUpsertManager;
    @Resource
    private LiveShowRepositoryI liveShowRepositoryI;


    @Override
    public void onEvent(AuctionSessionEvent event) throws Exception {
        MysqlMessage<AuctionSessionDO> message          = event.getValue();
        AuctionSessionDO               auctionSessionDO = message.getData();
        switch (message.getType()) {
            case UPDATE:
                updateSession(auctionSessionDO);
                break;
            case INSERT:
                sessionLoadBO.loadById(auctionSessionDO.getAsid().longValue());
                break;
            default:
                break;
        }
    }

    private void updateSession(AuctionSessionDO auctionSessionDO) {
        if (auctionSessionDO.getAsid() == null) {
            return;
        }
        SessionEntity entity = new SessionEntity();
        entity.setCreatorId(Utils.getDefaultId(auctionSessionDO.getUid()));
        entity.setCreatorType(Utils.getDefaultEnum(auctionSessionDO.getUtype()));
        entity.setForNewUser(Utils.getDefaultEnum(auctionSessionDO.getForNewUser()));
        entity.setHotWeight(Utils.getDefaultWeight(auctionSessionDO.getHot()));
        entity.setPic(Utils.getDefaultString(auctionSessionDO.getPic()));
        entity.setPlanEndTime(Utils.getDefaultShortTime(auctionSessionDO.getPlanetime()) * 1000);
        entity.setPlanStartTime(Utils.getDefaultShortTime(auctionSessionDO.getPlanatime()) * 1000);
        entity.setPreview(Utils.getDefaultEnum(auctionSessionDO.getPreview()));
        entity.setSessionId(auctionSessionDO.getAsid().longValue());
        entity.setState(Utils.getDefaultEnum(auctionSessionDO.getState()));
        entity.setStatus(Utils.getDefaultEnum(auctionSessionDO.getStatus()));
        entity.setSessionType(Utils.getDefaultEnum(auctionSessionDO.getType()));
        entity.setTitle(Utils.getDefaultString(auctionSessionDO.getTitle()));
        entity.setWeight(Utils.getDefaultWeight(auctionSessionDO.getWeight()));
        entity.setAinterval(Utils.getDefault(auctionSessionDO.getAinterval()));
        entity.setDeposit(Utils.getDefault(auctionSessionDO.getDeposit()));
        entity.setCreateTime(Utils.getDefaultShortTime(auctionSessionDO.getTimestamp()) * 1000);
        entity.setId(auctionSessionDO.getAsid().toString());
        if (auctionSessionDO.getType().intValue() == Conf.LIVE_TYPE_SESSION.intValue()
                && auctionSessionDO.getLsid() != null) {
            Long       liveId     = Long.valueOf(auctionSessionDO.getLsid().longValue());
            LiveShowDO liveShowDO = liveShowRepositoryI.getByPrimaryKey(liveId);
            if (liveShowDO != null) {
                entity.setLiveId(liveShowDO.getId());
                entity.setLivePreStarTime(Utils.getDefaultTime(liveShowDO.getPrestarttm()));
                entity.setLiveState(Utils.getDefaultEnum(liveShowDO.getState()));
            }
        }
        esUpsertManager.sessionUpsert(entity);
    }
}
