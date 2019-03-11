package cn.idongjia.divine.mq.kafka.disruptor.handler.auction;

import cn.idongjia.divine.assembler.AssemblerI;
import cn.idongjia.divine.assembler.AuctionItemAssembler;
import cn.idongjia.divine.db.es.entity.AuctionEntity;
import cn.idongjia.divine.db.mybatis.pojo.AuctionDO;
import cn.idongjia.divine.dto.AuctionItemDTO;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lc
 * @create at 2018/8/17.
 */
@Component
@Slf4j
public class AuctionHandler extends BaseEventHandler<AuctionEvent> {
    @Resource
    private ESUpsertManager                                      esUpsertManager;
    @Resource
    private AssemblerI<AuctionEntity, AuctionDO, AuctionItemDTO> auctionItemAssembler;

    @Override
    public void onEvent(AuctionEvent event) throws Exception {
        MysqlMessage<AuctionDO> mysqlMessage = event.getValue();
        AuctionDO               auctionDO    = mysqlMessage.getData();
        switch (mysqlMessage.getType()) {
            case INSERT:
                final List<AuctionEntity> entities = auctionItemAssembler.assemble(Lists.newArrayList(auctionDO));
                esUpsertManager.bulkAuctionUpsert(AuctionEntity.indexName, AuctionEntity.typeName, entities);
                break;
            case UPDATE:
                AuctionItemDTO auctionItemDTO = new AuctionItemDTO();
                AuctionItemAssembler.setAuctionDOFields(auctionItemDTO, auctionDO);
                AuctionEntity auctionItemEntity = auctionItemAssembler.assemble(auctionItemDTO);
                esUpsertManager.upsertAuctionEntity(AuctionEntity.indexName, AuctionEntity.typeName, auctionItemEntity);
                break;
            default:
                break;
        }
    }
}
