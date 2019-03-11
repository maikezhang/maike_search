package cn.idongjia.divine.mq.kafka.disruptor.handler.auction;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import cn.idongjia.divine.assembler.AssemblerI;
import cn.idongjia.divine.dto.AuctionItemDTO;
import org.springframework.stereotype.Component;

import cn.idongjia.divine.assembler.AuctionItemAssembler;
import cn.idongjia.divine.db.es.entity.AuctionEntity;
import cn.idongjia.divine.db.mybatis.pojo.AuctionDO;
import cn.idongjia.divine.db.mybatis.pojo.ZooDO;
import cn.idongjia.divine.db.mybatis.query.AuctionQuery;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.mq.kafka.disruptor.event.ZooEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.divine.repository.AuctionRepositoryI;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
@Slf4j
public class AuctionZooHandler extends BaseEventHandler<ZooEvent> {
	@Resource
	private AssemblerI<AuctionEntity,AuctionDO,AuctionItemDTO> auctionItemAssembler;
	@Resource
	private ESUpsertManager                                    esUpsertManager;
	@Resource
	private AuctionRepositoryI                                 auctionRepository;

	@Override
    public void onEvent(ZooEvent event) throws Exception {
        MysqlMessage<ZooDO> messageBody = event.getValue();
        String              type        = messageBody.getType();
		ZooDO zooDO = messageBody.getData();

        switch(type) {
            case UPDATE:
				List<AuctionDO> auctionDOS = auctionRepository.list(AuctionQuery.builder().zooId(zooDO.getZid()).build());
				if(Utils.isNotEmpty(auctionDOS)) {
					List<AuctionEntity> auctionEntities = auctionDOS.stream().map(auctionDO -> {
						AuctionEntity auctionEntity=new AuctionEntity();
						auctionEntity.setZooId(zooDO.getZid());
						auctionEntity.setId(auctionDO.getIid().toString());
					return auctionEntity;
					}).collect(Collectors.toList());
					esUpsertManager.bulkAuctionUpsert(AuctionEntity.indexName, AuctionEntity.typeName, auctionEntities);
				}
				break;
            default:
                break;
        }
    }
}
