package cn.idongjia.divine.mq.kafka.disruptor.handler.auction;

import javax.annotation.Resource;

import cn.idongjia.divine.assembler.AssemblerI;
import cn.idongjia.divine.db.mybatis.pojo.AuctionDO;
import cn.idongjia.divine.lib.pojo.Conf;
import org.springframework.stereotype.Component;

import cn.idongjia.divine.assembler.AuctionItemAssembler;
import cn.idongjia.divine.db.es.entity.AuctionEntity;
import cn.idongjia.divine.db.mybatis.pojo.AuctionBookDO;
import cn.idongjia.divine.dto.AuctionItemDTO;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionBookEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.divine.repository.AuctionBookRepositoryI;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
@Slf4j
public class AuctionBookHandler extends BaseEventHandler<AuctionBookEvent> {
	@Resource
	private AssemblerI<AuctionEntity,AuctionDO,AuctionItemDTO> auctionItemAssembler;
	@Resource
	private ESUpsertManager                                    esUpsertManager;
	@Resource
	private AuctionBookRepositoryI                             auctionBookRepository;

	@Override
	public void onEvent(AuctionBookEvent event) throws Exception {
		MysqlMessage<AuctionBookDO> messageBody = event.getValue();
		AuctionBookDO auctionBookDO = messageBody.getData();
		if (auctionBookDO.getContentType().equals(Conf.AUCTION_BOOK_TYPE)) {
			Long auctionId = auctionBookDO.getContentId();
			int bookTotal = auctionBookRepository.countByContentId(auctionId, Conf.AUCTION_BOOK_TYPE);
			AuctionItemDTO auctionItemDTO = new AuctionItemDTO();
			auctionItemDTO.setBookTotal(bookTotal);
			auctionItemDTO.setAuctionItemId(auctionId);
			AuctionEntity auctionItemEntity = auctionItemAssembler.assemble(auctionItemDTO);
			esUpsertManager.upsertAuctionEntity(AuctionEntity.indexName, AuctionEntity.typeName, auctionItemEntity);
		}
	}
}
