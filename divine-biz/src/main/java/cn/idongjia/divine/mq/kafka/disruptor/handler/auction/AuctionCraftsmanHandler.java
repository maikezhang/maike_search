package cn.idongjia.divine.mq.kafka.disruptor.handler.auction;

import cn.idongjia.divine.db.es.entity.AuctionEntity;
import cn.idongjia.divine.db.mybatis.pojo.CraftsmanDO;
import cn.idongjia.divine.db.mybatis.pojo.ItemExtDO;
import cn.idongjia.divine.db.mybatis.query.ItemExtQuery;
import cn.idongjia.divine.lib.pojo.Conf;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.mq.kafka.disruptor.event.CraftsmanEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.divine.repository.ItemExtRepositoryI;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yuexiaodong@idongjia.cn
 * @date 2018/10/15
 */
@Component
@Slf4j
public class AuctionCraftsmanHandler extends BaseEventHandler<CraftsmanEvent> {

    @Resource
    private ItemExtRepositoryI itemExtRepository;
    @Resource
    private ESUpsertManager    esUpsertManager;


    @Override
    public void onEvent(CraftsmanEvent event) throws Exception {
        MysqlMessage<CraftsmanDO> messageBody = event.getValue();
        CraftsmanDO               craftsmanDO = messageBody.getData();
        String                    type        = messageBody.getType();
        switch (type) {
            case UPDATE:
                List<ItemExtDO> itemExtDOS = itemExtRepository.list(ItemExtQuery.builder().cuid(craftsmanDO.getCustomerId().longValue()).build());
                if (Utils.isNotEmpty(itemExtDOS)) {
                    itemExtDOS.stream().forEach(itemExtDO -> {
                        AuctionEntity auctionEntity = new AuctionEntity();
                        if (craftsmanDO != null) {
                            auctionEntity.setCraftsmanStatus(craftsmanDO.getStatus());
                        } else {
                            auctionEntity.setCraftsmanStatus(Conf.defaultEnum);
                        }
                        auctionEntity.setId(itemExtDO.getIid().toString());
                        auctionEntity.setItemId(itemExtDO.getIid());
                        esUpsertManager.upsertAuctionEntity(AuctionEntity.indexName, AuctionEntity.typeName, auctionEntity);
                    });
                }
                break;
            default:
                break;

        }

    }
}
