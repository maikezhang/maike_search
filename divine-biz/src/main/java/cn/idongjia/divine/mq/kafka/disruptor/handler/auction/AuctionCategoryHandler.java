package cn.idongjia.divine.mq.kafka.disruptor.handler.auction;

import cn.idongjia.divine.assembler.AssemblerI;
import cn.idongjia.divine.db.es.entity.AuctionEntity;
import cn.idongjia.divine.db.mybatis.pojo.AuctionDO;
import cn.idongjia.divine.db.mybatis.pojo.CategoryDO;
import cn.idongjia.divine.db.mybatis.pojo.ItemDO;
import cn.idongjia.divine.db.mybatis.query.ItemQuery;
import cn.idongjia.divine.dto.AuctionItemDTO;
import cn.idongjia.divine.lib.pojo.Conf;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.mq.kafka.disruptor.event.CategoryEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.divine.repository.AuctionRepositoryI;
import cn.idongjia.divine.repository.ItemRepositoryI;
import cn.idongjia.divine.utils.JsonUtils;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/8/17.
 */
@Component
@Slf4j
public class AuctionCategoryHandler extends BaseEventHandler<CategoryEvent> {

    @Resource
    private AssemblerI<AuctionEntity,AuctionDO,AuctionItemDTO> auctionItemAssembler;

    @Resource
	private ESUpsertManager		 esUpsertManager;

	@Resource
	private AuctionRepositoryI	 auctionRepository;

	@Resource
	private ItemRepositoryI		 itemRepository;

    @Override
    public void onEvent(CategoryEvent event) throws Exception {
        MysqlMessage<CategoryDO> messageBody = event.getValue();
        CategoryDO               categoryDO  = messageBody.getData();
        String                   type        = messageBody.getType();
        switch(type) {
            case UPDATE:
				ItemQuery itemQuery = ItemQuery.builder().categoryId(categoryDO.getId()).build();
				List<ItemDO> itmeDOS = itemRepository.list(itemQuery);
				String     path        = categoryDO.getPath();
                List<Long> categoryIds = JsonUtils.toList(path,Long.class);
				if (Utils.isNotEmpty(itmeDOS)) {
					List<Long> itemIds = itmeDOS.stream().map(ItemDO::getIid).collect(Collectors.toList());
					if(Utils.isNotEmpty(itemIds)) {
						itemIds.stream().forEach(itemId->{
							AuctionEntity auctionEntity=new AuctionEntity();
							auctionEntity.setItemId(itemId);
							auctionEntity.setId(itemId.toString());
							if(Utils.isNotEmpty(categoryIds)) {
								auctionEntity.setItemCategoryId(categoryIds.get(0));
	                        } else {
	                        	auctionEntity.setItemCategoryId(Conf.defaultId);
		                    }
							esUpsertManager.upsertAuctionEntity(AuctionEntity.indexName, AuctionEntity.typeName, auctionEntity);
						});
					}
				}
                break;
            default: break;
        }
    }
}
