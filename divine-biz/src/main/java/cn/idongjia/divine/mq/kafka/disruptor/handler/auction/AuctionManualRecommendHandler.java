package cn.idongjia.divine.mq.kafka.disruptor.handler.auction;

import javax.annotation.Resource;

import cn.idongjia.divine.assembler.AssemblerI;
import cn.idongjia.divine.db.mybatis.pojo.AuctionDO;
import cn.idongjia.divine.lib.pojo.Conf;
import org.springframework.stereotype.Component;

import cn.idongjia.divine.assembler.AuctionItemAssembler;
import cn.idongjia.divine.db.es.entity.AuctionEntity;
import cn.idongjia.divine.db.mybatis.pojo.AuctionManualRecommendDO;
import cn.idongjia.divine.dto.AuctionItemDTO;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionManualRecommendEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;

import static cn.idongjia.divine.lib.pojo.Conf.AUCTION_RECOMMEND_DELETED;
import static cn.idongjia.divine.lib.pojo.Conf.AUCTION_RECOMMEND_NOT_DELETED;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
@Slf4j
public class AuctionManualRecommendHandler extends BaseEventHandler<AuctionManualRecommendEvent> {
    @Resource
    private ESUpsertManager                                    esUpsertManager;
    @Resource
    private AssemblerI<AuctionEntity,AuctionDO,AuctionItemDTO> auctionItemAssembler;

    @Override
    public void onEvent(AuctionManualRecommendEvent event) throws Exception {
        MysqlMessage<AuctionManualRecommendDO> messageBody       = event.getValue();
        AuctionItemDTO                         auctionItemDTO    = new AuctionItemDTO();
        AuctionManualRecommendDO               manualRecommendDO = messageBody.getData();
        if(manualRecommendDO.getIsDelete().equals(AUCTION_RECOMMEND_NOT_DELETED) && manualRecommendDO.getUserType().equals(1)) {
            auctionItemDTO.setNewRecommendWeight(manualRecommendDO.getWeight());
        }
        if(manualRecommendDO.getIsDelete().equals(AUCTION_RECOMMEND_NOT_DELETED) && manualRecommendDO.getUserType().equals(2)) {
            auctionItemDTO.setOldRecommendWeight(manualRecommendDO.getWeight());
        }
        if(manualRecommendDO.getIsDelete().equals(AUCTION_RECOMMEND_DELETED) && manualRecommendDO.getUserType().equals(1)) {
            auctionItemDTO.setNewRecommendWeight(Conf.defaultWeight);
        }
        if(manualRecommendDO.getIsDelete().equals(AUCTION_RECOMMEND_DELETED) && manualRecommendDO.getUserType().equals(2)) {
            auctionItemDTO.setOldRecommendWeight(Conf.defaultWeight);
        }
		auctionItemDTO.setAuctionItemId(manualRecommendDO.getItemId());
        AuctionEntity auctionItemEntity = auctionItemAssembler.assemble(auctionItemDTO);
        esUpsertManager.upsertAuctionEntity(AuctionEntity.indexName,AuctionEntity.typeName,auctionItemEntity);

    }
}
