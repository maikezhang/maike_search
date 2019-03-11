package cn.idongjia.divine.mq.kafka.disruptor.handler.auction;

import cn.idongjia.divine.assembler.AssemblerI;
import cn.idongjia.divine.db.es.entity.AuctionEntity;
import cn.idongjia.divine.db.mybatis.pojo.AuctionDO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionOfferUserDO;
import cn.idongjia.divine.db.mybatis.pojo.CategoryDO;
import cn.idongjia.divine.db.mybatis.pojo.ItemDO;
import cn.idongjia.divine.db.mybatis.pojo.MediaDO;
import cn.idongjia.divine.dto.AuctionItemDTO;
import cn.idongjia.divine.lib.pojo.Conf;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.mq.kafka.disruptor.event.ItemEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.divine.repository.AuctionOfferRepositoryI;
import cn.idongjia.divine.repository.AuctionRepositoryI;
import cn.idongjia.divine.repository.CategoryRepositoryI;
import cn.idongjia.divine.repository.MediaRepositoryI;
import cn.idongjia.divine.utils.JsonUtils;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author lc
 * @create at 2018/8/17.
 */
@Component
@Slf4j
public class AuctionItemHandler extends BaseEventHandler<ItemEvent> {

    @Resource
    private ESUpsertManager                                      esUpsertManager;
    @Resource
    private AssemblerI<AuctionEntity, AuctionDO, AuctionItemDTO> auctionItemAssembler;
    @Resource
    private CategoryRepositoryI                                  categoryRepository;
    @Resource
    private MediaRepositoryI                                     mediaRepository;
    @Resource
    private AuctionOfferRepositoryI                              auctionOfferRepository;
    @Resource
    private AuctionRepositoryI                                   auctionRepository;

    @Override
    public void onEvent(ItemEvent event) throws Exception {
        MysqlMessage<ItemDO> messageBody = event.getValue();
        String               type        = messageBody.getType();
        ItemDO               itemDO      = messageBody.getData();
        switch (type) {
            case UPDATE:
                if (itemDO == null) {
                    return;
                }
                AuctionDO auctionDO = auctionRepository.getByItemId(itemDO.getIid());
                if (auctionDO == null) {
                    return;
                }
                AuctionItemDTO auctionItemDTO = new AuctionItemDTO();
                MediaDO mediaDO = mediaRepository.getByItemId(itemDO.getIid());
                CategoryDO categoryDO = categoryRepository.getById(itemDO.getCategoryId().longValue());
                if (mediaDO != null) {
                    auctionItemDTO.setItemPicture(Utils.getDefaultString(mediaDO.getMediaUrl()));
                } else {
                    auctionItemDTO.setItemPicture(Utils.getDefaultString(itemDO.getPictures()));
                }
                if (categoryDO != null) {
                    String     path        = categoryDO.getPath();
                    List<Long> categoryIds = JsonUtils.toList(path, Long.class);
                    auctionItemDTO.setItemCategoryId(categoryIds.get(0));
                } else {
                    auctionItemDTO.setItemCategoryId(Conf.defaultId);
                }
                auctionItemDTO.setAuctionItemId(itemDO.getIid());
                auctionItemDTO.setItemPrice(Utils.getDefault(itemDO.getPrice()) * 100);
                auctionItemDTO.setItemTitle(Utils.getDefaultString(itemDO.getTitle()));
                auctionItemDTO.setItemStatus(Utils.getDefaultEnum(itemDO.getStatus()));
                Map<Long, AuctionOfferUserDO> auctionOfferUserDOMap = auctionOfferRepository.groupByItemId(Arrays.asList(itemDO.getIid()));
                AuctionOfferUserDO auctionOfferUserDO = auctionOfferUserDOMap.get(itemDO.getIid());
                if (auctionOfferUserDO != null) {
                    auctionItemDTO.setOfferUserTotal(auctionOfferUserDO.getOfferTimes());
                    auctionItemDTO.setCurrentPrice(auctionOfferUserDO.getPrice() * 100);
                    auctionItemDTO.setLastOfferUseId(auctionOfferUserDO.getUserId());
                } else {
                    auctionItemDTO.setOfferUserTotal(Conf.defaultTotal);
                    auctionItemDTO.setCurrentPrice(auctionItemDTO.getItemPrice());
                    auctionItemDTO.setLastOfferUseId(Conf.defaultId);
                }
                AuctionEntity auctionEntity = auctionItemAssembler.assemble(auctionItemDTO);
                esUpsertManager.upsertAuctionEntity(AuctionEntity.indexName, AuctionEntity.typeName, auctionEntity);
                break;
            default:
                break;

        }
    }
}
