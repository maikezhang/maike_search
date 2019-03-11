package cn.idongjia.divine.mq.kafka.disruptor.handler.auction;

import cn.idongjia.divine.db.es.entity.AuctionEntity;
import cn.idongjia.divine.db.mybatis.pojo.AuctionExtDO;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionExtEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yuexiaodong@idongjia.cn
 * @date 2018-12-18
 */
@Component
public class AuctionExtHandler extends BaseEventHandler<AuctionExtEvent> {

    @Resource
    private ESUpsertManager esUpsertManager;


    @Override
    public void onEvent(AuctionExtEvent event) throws Exception {
        final MysqlMessage<AuctionExtDO> mysqlMessage = event.getValue();
        final AuctionExtDO               ext          = mysqlMessage.getData();
        switch (mysqlMessage.getType()) {
            case INSERT:
            case UPDATE:
                if (ext.getItemId() == null || ext.getCreateTime() == null) {
                    return;
                }
                AuctionEntity entity = new AuctionEntity();
                entity.setId(ext.getItemId().toString());
                entity.setItemId(ext.getItemId());
                entity.setCreateTime(ext.getCreateTime());
                entity.setUpdateTime(ext.getUpdateTime());
                esUpsertManager.upsertAuctionEntity(AuctionEntity.indexName, AuctionEntity.typeName, entity);
                break;
            default:
                break;
        }
    }
}
