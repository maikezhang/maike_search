package cn.idongjia.divine.mq.kafka.disruptor.handler.auction;

import cn.idongjia.divine.db.es.entity.AuctionEntity;
import cn.idongjia.divine.db.mybatis.pojo.CustomerDO;
import cn.idongjia.divine.db.mybatis.pojo.ItemExtDO;
import cn.idongjia.divine.db.mybatis.query.ItemExtQuery;
import cn.idongjia.divine.lib.pojo.Conf;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.mq.kafka.disruptor.event.CustomerEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.divine.repository.ItemExtRepositoryI;
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
public class AuctionCustomerHandler extends BaseEventHandler<CustomerEvent> {

    @Resource
    private ItemExtRepositoryI itemExtRepository;
    @Resource
    private ESUpsertManager    esUpsertManager;

    @Override
    public void onEvent(CustomerEvent event) throws Exception {
        MysqlMessage<CustomerDO> messageBody = event.getValue();
        String                   type        = messageBody.getType();
        CustomerDO               customerDO  = messageBody.getData();
        switch(type) {
            case UPDATE:
                List<ItemExtDO> itemExtDOS = itemExtRepository.list(ItemExtQuery.builder().cuid(customerDO.getMainUserId().longValue()).build());
                if(Utils.isNotEmpty(itemExtDOS)) {
                    itemExtDOS.stream().forEach(itemExtDO -> {
                        AuctionEntity auctionEntity = new AuctionEntity();
                        if(customerDO != null) {
                            auctionEntity.setCraftsmaneAvatar(customerDO.getAvatar());
                            auctionEntity.setCraftsmanName(customerDO.getName());
                        } else {
                            auctionEntity.setCraftsmaneAvatar(Conf.defaultString);
                            auctionEntity.setCraftsmanName(Conf.defaultString);
                        }
                        auctionEntity.setId(itemExtDO.getIid().toString());
                        auctionEntity.setItemId(itemExtDO.getIid());
                        esUpsertManager.upsertAuctionEntity(AuctionEntity.indexName,AuctionEntity.typeName,auctionEntity);
                    });
                }
                break;
            default:
                break;
        }
    }
}
