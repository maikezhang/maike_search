package cn.idongjia.divine.mq.kafka.disruptor.handler.auction;

import cn.idongjia.divine.assembler.AssemblerI;
import cn.idongjia.divine.assembler.AuctionItemAssembler;
import cn.idongjia.divine.db.es.entity.AuctionEntity;
import cn.idongjia.divine.db.mybatis.pojo.AuctionDO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionDO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionRelDO;
import cn.idongjia.divine.dto.AuctionItemDTO;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionSessionEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.divine.repository.AuctionSessionRelRepositoryI;
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
public class AuctionSessionHandler extends BaseEventHandler<AuctionSessionEvent> {
    @Resource
    private ESUpsertManager                                    esUpsertManager;
    @Resource
    private AssemblerI<AuctionEntity,AuctionDO,AuctionItemDTO> auctionItemAssembler;
    @Resource
    private AuctionSessionRelRepositoryI                       auctionSessionRelRepository;

    @Override
    public void onEvent(AuctionSessionEvent event) throws Exception {
        MysqlMessage<AuctionSessionDO> messageBody      = event.getValue();
        AuctionSessionDO               auctionSessionDO = messageBody.getData();
        try {
            List<AuctionSessionRelDO> auctionSessionRels = auctionSessionRelRepository.listBySessionId(auctionSessionDO.getAsid());
            if(Utils.isNotEmpty(auctionSessionRels)) {
                auctionSessionRels.stream().forEach(auctionSessionRel -> {
                    AuctionItemDTO auctionItemDTO = new AuctionItemDTO();
                    AuctionItemAssembler.setAuctionSessionDOFields(auctionItemDTO, auctionSessionDO);
                    auctionItemDTO.setAuctionItemId(auctionSessionRel.getAid());
                    AuctionEntity auctionEntity = auctionItemAssembler.assemble(auctionItemDTO);
                    esUpsertManager.upsertAuctionEntity(AuctionEntity.indexName,AuctionEntity.typeName,auctionEntity);
                });
            }
        } catch(Exception e) {
            log.error("update live session failed {}",e);
        }
    }
}
