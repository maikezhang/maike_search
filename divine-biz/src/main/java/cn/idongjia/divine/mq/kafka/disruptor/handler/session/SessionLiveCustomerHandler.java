package cn.idongjia.divine.mq.kafka.disruptor.handler.session;

import cn.idongjia.divine.db.es.entity.SessionLiveEntity;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionDO;
import cn.idongjia.divine.db.mybatis.pojo.CustomerDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveShowDO;
import cn.idongjia.divine.db.mybatis.query.LiveShowQuery;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.mq.kafka.disruptor.event.CustomerEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.divine.repository.AuctionSessionRepositoryI;
import cn.idongjia.divine.repository.LiveShowRepositoryI;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.kafka.message.body.MysqlMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author yuexiaodong@idongjia.cn
 * @date 2018-12-13
 */
@Component
public class SessionLiveCustomerHandler extends BaseEventHandler<CustomerEvent> {

    private static final int LIVE_SESSION_TYPE = 2;

    @Resource
    private ESUpsertManager           esUpsertManager;
    @Resource
    private LiveShowRepositoryI       liveShowRepository;
    @Resource
    private AuctionSessionRepositoryI auctionSessionRepositoryI;


    @Override
    public void onEvent(CustomerEvent event) throws Exception {
        final MysqlMessage<CustomerDO> messageBody = event.getValue();
        final CustomerDO               customerDO  = messageBody.getData();
        final String                   type        = messageBody.getType();

        switch (type) {
            case UPDATE:
                List<LiveShowDO> liveShowDOS = liveShowRepository.select(
                        LiveShowQuery.builder()
                                .type(2)
                                .uid(customerDO.getMainUserId())
                                .build()
                );
                if (Utils.isEmpty(liveShowDOS)) {
                    return;
                }
                final List<Long> liveIds = liveShowDOS.parallelStream()
                        .map(LiveShowDO::getId)
                        .collect(Collectors.toList());
                final Map<Long, AuctionSessionDO> sessionMap = auctionSessionRepositoryI.mapByLiveId(liveIds);
                if (Utils.isEmpty(sessionMap)) {
                    return;
                }
                final List<SessionLiveEntity> entities = sessionMap.values().parallelStream()
                        .map(sessionDO -> {
                            if (sessionDO.getAsid() == null) {
                                return null;
                            }
                            if (sessionDO.getType() == null || sessionDO.getType() != LIVE_SESSION_TYPE) {
                                return null;
                            }
                            SessionLiveEntity entity = new SessionLiveEntity();
                            entity.setId(sessionDO.getAsid().toString());
                            entity.setLiveCraftsmanAvatar(customerDO.getAvatar());
                            entity.setLiveCraftsmanName(customerDO.getName());
                            return entity;
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                esUpsertManager.bulkSessionLiveUpsert(entities);
                break;
            default:
                break;

        }
    }
}
