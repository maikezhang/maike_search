package cn.idongjia.divine.mq.kafka.disruptor.handler.session;

import cn.idongjia.divine.db.es.entity.SessionEntity;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionDO;
import cn.idongjia.divine.db.mybatis.pojo.CustomerDO;
import cn.idongjia.divine.db.mybatis.query.AuctionSessionQuery;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.mq.kafka.disruptor.event.CustomerEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.divine.repository.AuctionSessionRepositoryI;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
@Slf4j
public class SessionCustomerHandler extends BaseEventHandler<CustomerEvent> {

    @Resource
    private AuctionSessionRepositoryI auctionSessionRepositoryI;
    @Resource
    private ESUpsertManager           esUpsertManager;

    @Override
    public void onEvent(CustomerEvent event) throws Exception {
        MysqlMessage<CustomerDO> messageBody = event.getValue();
        String                   type        = messageBody.getType();
        CustomerDO               customerDO  = messageBody.getData();
        switch(type) {
            case UPDATE:
                List<AuctionSessionDO> auctionSessionDOS = auctionSessionRepositoryI.select(AuctionSessionQuery.builder().uid(customerDO.getMainUserId()).build());
                if(Utils.isNotEmpty(auctionSessionDOS)) {
                    auctionSessionDOS.stream().forEach(auctionSessionDO -> {
                        if(customerDO != null && auctionSessionDO.getUtype().intValue() == 0) {
                            SessionEntity sessionEntity = new SessionEntity();
                            sessionEntity.setId(auctionSessionDO.getAsid().toString());
                            sessionEntity.setCraftsmanAvatar(customerDO.getAvatar());
                            sessionEntity.setCraftsmanName(customerDO.getName());
                            esUpsertManager.sessionUpsert(sessionEntity);
                        }

                    });
                }
                break;
            default:
                break;
        }
    }
}
