package cn.idongjia.divine.mq.kafka.disruptor.handler.auction;

import javax.annotation.Resource;

import cn.idongjia.divine.assembler.AssemblerI;
import cn.idongjia.divine.db.mybatis.pojo.AuctionDO;
import cn.idongjia.divine.lib.pojo.Conf;
import org.springframework.stereotype.Component;

import cn.idongjia.divine.assembler.AuctionItemAssembler;
import cn.idongjia.divine.db.es.entity.AuctionEntity;
import cn.idongjia.divine.db.mybatis.pojo.MediaDO;
import cn.idongjia.divine.dto.AuctionItemDTO;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.mq.kafka.disruptor.event.MediaEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
@Slf4j
public class AuctionMediaHandler extends BaseEventHandler<MediaEvent> {

	@Resource
	private AssemblerI<AuctionEntity,AuctionDO,AuctionItemDTO> auctionItemAssembler;
	@Resource
	private ESUpsertManager                                    esUpsertManager;

	@Override
	public void onEvent(MediaEvent event) throws Exception {
		MysqlMessage<MediaDO> messageBody = event.getValue();
		MediaDO mediaDO = messageBody.getData();
		String type = messageBody.getType();
		switch (type) {
			case UPDATE:
				if (mediaDO.getSourceType().equals(Conf.MEAID_SOURCE_ITEM) && mediaDO.getMediaType().equals(Conf.MEAID_TYPE_C0VER)) {
					AuctionItemDTO auctionItemDTO = new AuctionItemDTO();
					auctionItemDTO.setItemPicture(mediaDO.getMediaUrl());
					auctionItemDTO.setAuctionItemId(mediaDO.getSourceId());
					AuctionEntity auctionEntity = auctionItemAssembler.assemble(auctionItemDTO);
					esUpsertManager.upsertAuctionEntity(AuctionEntity.indexName, AuctionEntity.typeName, auctionEntity);
				}

				break;
			default:
				break;
		}
	}
}
