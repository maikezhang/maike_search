package cn.idongjia.divine.mq.kafka.disruptor.handler.auction;

import cn.idongjia.divine.assembler.AssemblerI;
import cn.idongjia.divine.db.es.entity.AuctionEntity;
import cn.idongjia.divine.db.mybatis.pojo.AuctionDO;
import cn.idongjia.divine.db.mybatis.pojo.ItemExtDO;
import cn.idongjia.divine.dto.AuctionItemDTO;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.mq.kafka.disruptor.event.ItemExtEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.divine.repository.AuctionRepositoryI;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/17.
 */
@Component
@Slf4j
public class AuctionItemExtHandler extends BaseEventHandler<ItemExtEvent> {
    @Resource
    private ESUpsertManager                                      esUpsertManager;
    @Resource
    private AssemblerI<AuctionEntity, AuctionDO, AuctionItemDTO> auctionItemAssembler;
    @Resource
    private AuctionRepositoryI                                   auctionRepository;

    @Override
    public void onEvent(ItemExtEvent event) throws Exception {
        MysqlMessage<ItemExtDO> messageBody = event.getValue();
        String                  type        = messageBody.getType();
        switch (type) {
            case UPDATE:
                ItemExtDO itemExtDO = messageBody.getData();
                if (itemExtDO == null || itemExtDO.getIid() == null) {
                    return;
                }
                AuctionDO auctionDO = auctionRepository.getByItemId(itemExtDO.getIid());
                if (auctionDO == null) {
                    return;
                }
                AuctionItemDTO auctionItemDTO = new AuctionItemDTO();
                auctionItemDTO.setItemExtStatus(Utils.getDefaultEnum(itemExtDO.getStatus()));
                auctionItemDTO.setCraftsmanId(Utils.getDefaultId(itemExtDO.getCuid()));
                auctionItemDTO.setAuctionItemId(Utils.getDefaultId(itemExtDO.getIid()));
                AuctionEntity auctionEntity = auctionItemAssembler.assemble(auctionItemDTO);
                esUpsertManager.upsertAuctionEntity(AuctionEntity.indexName, AuctionEntity.typeName, auctionEntity);
                break;
            default:
                break;

        }
    }
}
