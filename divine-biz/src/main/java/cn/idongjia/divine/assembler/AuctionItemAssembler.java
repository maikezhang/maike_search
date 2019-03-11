package cn.idongjia.divine.assembler;

import cn.idongjia.divine.db.es.entity.AuctionEntity;
import cn.idongjia.divine.db.mybatis.pojo.AuctionDO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionManualRecommendDO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionOfferUserDO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionDO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionRelDO;
import cn.idongjia.divine.db.mybatis.pojo.CategoryDO;
import cn.idongjia.divine.db.mybatis.pojo.CountPO;
import cn.idongjia.divine.db.mybatis.pojo.CraftsmanDO;
import cn.idongjia.divine.db.mybatis.pojo.CustomerDO;
import cn.idongjia.divine.db.mybatis.pojo.ItemDO;
import cn.idongjia.divine.db.mybatis.pojo.ItemExtDO;
import cn.idongjia.divine.db.mybatis.pojo.MediaDO;
import cn.idongjia.divine.dto.AuctionItemDTO;
import cn.idongjia.divine.lib.pojo.Conf;
import cn.idongjia.divine.repository.AuctionBookRepositoryI;
import cn.idongjia.divine.repository.AuctionExtRepositoryI;
import cn.idongjia.divine.repository.AuctionManaualRecommendRepositoryI;
import cn.idongjia.divine.repository.AuctionOfferRepositoryI;
import cn.idongjia.divine.repository.AuctionSessionRelRepositoryI;
import cn.idongjia.divine.repository.AuctionSessionRepositoryI;
import cn.idongjia.divine.repository.CategoryRepositoryI;
import cn.idongjia.divine.repository.CraftsmanRepositoryI;
import cn.idongjia.divine.repository.CustomerRepositoryI;
import cn.idongjia.divine.repository.ItemExtRepositoryI;
import cn.idongjia.divine.repository.ItemRepositoryI;
import cn.idongjia.divine.repository.MediaRepositoryI;
import cn.idongjia.divine.repository.ZooRepositoryI;
import cn.idongjia.divine.utils.JsonUtils;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.divine.utils.exception.DivineException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/8/16.
 */
@Component
@Slf4j
public class AuctionItemAssembler implements AssemblerI<AuctionEntity, AuctionDO, AuctionItemDTO> {
    @Resource
    private CustomerRepositoryI                customerReposcustomerRepositoryI;
    @Resource
    private ItemRepositoryI                    itemRepositoryI;
    @Resource
    private AuctionManaualRecommendRepositoryI auctionManaualRecommendRepositoryI;
    @Resource
    private AuctionOfferRepositoryI            auctionOfferRepositoryI;
    @Resource
    private AuctionSessionRepositoryI          auctionSessionRepositoryI;
    @Resource
    private AuctionSessionRelRepositoryI       auctionSessionRelRepositoryI;
    @Resource
    private AuctionBookRepositoryI             auctionBookRepositoryI;
    @Resource
    private CategoryRepositoryI                categoryRepositoryI;
    @Resource
    private ItemExtRepositoryI                 itemExtRepositoryI;
    @Resource
    private ZooRepositoryI                     zooRepositoryI;
    @Resource
    private MediaRepositoryI                   mediaRepositoryI;
    @Resource
    private CraftsmanRepositoryI               craftsmanRepositoryI;
    @Resource
    private AuctionExtRepositoryI              auctionExtRepositoryI;

    @Override
    public List<AuctionEntity> assemble(List<AuctionDO> auctionDOS) {
        if (Utils.isEmpty(auctionDOS)) {
            return new ArrayList<>();
        }

        List<Long>      itemIds         = new ArrayList<>();
        List<Long>      zooIds          = new ArrayList<>();
        Map<Long, Long> zooIdAuctionMap = new HashMap<>();
        auctionDOS.forEach(auctionDO -> {
            itemIds.add(auctionDO.getIid());
            zooIds.add(auctionDO.getZid());
            zooIdAuctionMap.put(auctionDO.getIid(), auctionDO.getZid());
        });
        Map<Long, AuctionSessionRelDO> sessionRelDOMap = auctionSessionRelRepositoryI.mapByItemIds(itemIds);
        List<Long>                     sessionIds      = null;
        Map<Long, AuctionSessionDO>    sessionDOMap    = new HashMap<>();
        if (Utils.isNotEmpty(sessionRelDOMap)) {
            sessionIds = sessionRelDOMap.entrySet().stream().map(sessionRelDO -> {
                return sessionRelDO.getValue().getAsid().longValue();
            }).distinct().collect(Collectors.toList());
            sessionDOMap = auctionSessionRepositoryI.mapBySessionIds(sessionIds);
        }
        Map<Long, ItemDO>             itemDOMap             = itemRepositoryI.mapByItemIds(itemIds);
        Map<Long, CategoryDO>         categoryDOMap         = new HashMap<>();
        Map<Long, ItemExtDO>          itemExtDOMap          = itemExtRepositoryI.mapByItemIds(itemIds);
        Map<Long, MediaDO>            mediaDOMap            = mediaRepositoryI.assembleByItemId(itemIds);
        Map<Long, AuctionOfferUserDO> auctionOfferUserDOMap = auctionOfferRepositoryI.groupByItemId(itemIds);
//        final Map<Long, AuctionExtDO> auctionExtMap         = auctionExtRepositoryI.mappingByItemIds(itemIds);

        Map<Long, CraftsmanDO> craftsmanDOMap = new HashMap<>();
        Map<Long, CustomerDO>  customerDOMap  = new HashMap<>();
        List<Long>             customerIds    = new ArrayList<>();
        //商品的匠人id
        if (Utils.isNotEmpty(itemExtDOMap)) {
            customerIds = itemExtDOMap.entrySet().stream()
                    .map(entry -> entry.getValue().getCuid()).filter(id -> id != null)
                    .distinct()
                    .collect(Collectors.toList());
            try {
                if (Utils.isNotEmpty(customerIds)) {
                    craftsmanDOMap = craftsmanRepositoryI.mapByCustomerId(customerIds).get();
                }
            } catch (Exception e) {
                log.error("获取匠人失败{}", e);
                throw DivineException.failure("获取匠人失败" + customerIds);
            }
        }
        //出价用户信息
        if (Utils.isNotEmpty(auctionOfferUserDOMap)) {
            final List<Long> offerUserIds = auctionOfferUserDOMap.entrySet().stream()
                    .map(entry -> entry.getValue().getUserId())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            for (Long userId : offerUserIds) {
                if (!customerIds.contains(userId)) {
                    customerIds.add(userId);
                }
            }
        }
        try {
            if (Utils.isNotEmpty(customerIds)) {
                customerDOMap = customerReposcustomerRepositoryI.mapByCustomerId(customerIds).get();
            }
        } catch (Exception e) {
            log.error("获取用户失败{}", e);
            throw DivineException.failure("获取用户失败" + customerIds);
        }
        Map<Long, List<AuctionManualRecommendDO>> auctionManualRecommendDOMap = auctionManaualRecommendRepositoryI.mapByItemId(itemIds);
        if (Utils.isNotEmpty(itemDOMap)) {
            List<Long> categoryIds = itemDOMap.entrySet().stream().map(entity -> {
                Integer categoryId = entity.getValue().getCategoryId();
                return categoryId == null ? null : categoryId.longValue();
            }).filter(id -> id != null).distinct().collect(Collectors.toList());
            if (Utils.isNotEmpty(categoryIds)) {
                categoryDOMap = categoryRepositoryI.mapByCategoryId(categoryIds);
            }
        }
        Map<Long, AuctionSessionDO> finalSessionDOMap   = sessionDOMap;
        Map<Long, CountPO>          itemBookMap         = auctionBookRepositoryI.countByContentId(itemIds, Conf.AUCTION_BOOK_TYPE);
        Map<Long, CategoryDO>       finalCategoryDOMap  = categoryDOMap;
        Map<Long, CustomerDO>       finalCustomerDOMap  = customerDOMap;
        Map<Long, CraftsmanDO>      finalCraftsmanDOMap = craftsmanDOMap;
        return auctionDOS.stream().map(auctionDO -> {
            try {
                if (auctionDO.getIid() == null || auctionDO.getIid() == 0) {
                    return null;
                }
                AuctionItemDTO auctionItemDTO = new AuctionItemDTO();
                setAuctionDOFields(auctionItemDTO, auctionDO);
                Long                itemId              = auctionDO.getIid();
                AuctionSessionRelDO auctionSessionRelDO = sessionRelDOMap.get(itemId);
                AuctionSessionDO    auctionSessionDO    = null;
                auctionItemDTO.setHot(Utils.getDefaultWeight(auctionDO.getHot()));
                if (auctionSessionRelDO != null) {
                    auctionSessionDO = finalSessionDOMap.get(auctionSessionRelDO.getAsid().longValue());
                    auctionItemDTO.setSessionWeight(Utils.getDefault(auctionSessionRelDO.getWeight()));
                }
                if (auctionSessionDO != null) {
                    setAuctionSessionDOFields(auctionItemDTO, auctionSessionDO);
                } else {
                    auctionItemDTO.setSessionId(Conf.defaultId);
                    auctionItemDTO.setSessionPreview(Conf.defaultEnum);
                    auctionItemDTO.setSessionState(Conf.defaultEnum);
                    auctionItemDTO.setSessionType(Conf.defaultEnum);
                    auctionItemDTO.setSessionStatus(Conf.defaultEnum);
                }
                CountPO itemBookPO = itemBookMap.get(itemId);
                if (itemBookPO != null) {
                    auctionItemDTO.setBookTotal(itemBookPO.getCount());
                } else {
                    auctionItemDTO.setBookTotal(Conf.defaultTotal);
                }
                ItemDO itemDO = itemDOMap.get(itemId);
                if (itemDO != null) {
                    MediaDO mediaDO = mediaDOMap.get(itemId);
                    if (mediaDO != null) {
                        auctionItemDTO.setItemPicture(Utils.getDefaultString(mediaDO.getMediaUrl()));
                    } else {
                        auctionItemDTO.setItemPicture(Utils.getDefaultString(itemDO.getPictures()));
                    }
                    Integer categoryId = itemDO.getCategoryId();
                    if (categoryId != null) {
                        CategoryDO categoryDO = finalCategoryDOMap.get(categoryId.longValue());
                        if (categoryDO != null) {
                            String     path        = categoryDO.getPath();
                            List<Long> categoryIds = JsonUtils.toList(path, Long.class);
                            if (Utils.isNotEmpty(categoryIds)) {
                                auctionItemDTO.setItemCategoryId(categoryIds.get(0));

                            }
                        } else {
                            auctionItemDTO.setItemCategoryId(Conf.defaultId);
                        }
                    } else {
                        auctionItemDTO.setItemCategoryId(Conf.defaultId);
                    }
                    auctionItemDTO.setItemPrice(Utils.getDefault(itemDO.getPrice()) * 100);
                    auctionItemDTO.setItemTitle(Utils.getDefaultString(itemDO.getTitle()));
                    auctionItemDTO.setItemStatus(Utils.getDefaultEnum(itemDO.getStatus()));
                    auctionItemDTO.setCreateTime(Utils.getDefault(itemDO.getCreatetm()) * 1000L);
                    auctionItemDTO.setUpdateTime(Utils.getDefault(itemDO.getUpdatetm()) * 1000L);
                } else {
                    auctionItemDTO.setItemCategoryId(Conf.defaultId);
                    auctionItemDTO.setItemPicture("");
                    auctionItemDTO.setItemPrice(0L);
                    auctionItemDTO.setItemTitle("");
                    auctionItemDTO.setItemStatus(Conf.defaultEnum);
                    auctionItemDTO.setCreateTime(Conf.defaultDate);
                    auctionItemDTO.setUpdateTime(Conf.defaultDate);
                }
                AuctionOfferUserDO auctionOfferUserDO = auctionOfferUserDOMap.get(itemId);
                if (auctionOfferUserDO != null) {
                    auctionItemDTO.setOfferUserTotal(auctionOfferUserDO.getOfferTimes());
                    auctionItemDTO.setCurrentPrice(auctionOfferUserDO.getPrice() * 100);
                    auctionItemDTO.setLastOfferUseId(auctionOfferUserDO.getUserId());
                    final CustomerDO customerDO = finalCustomerDOMap.get(auctionOfferUserDO.getUserId());
                    if (customerDO != null) {
                        auctionItemDTO.setLastOfferUserAvatar(customerDO.getAvatar());
                        auctionItemDTO.setLastOfferUserName(customerDO.getName());
                    } else {
                        auctionItemDTO.setLastOfferUserAvatar(Conf.defaultString);
                        auctionItemDTO.setLastOfferUserName(Conf.defaultString);
                    }
                } else {
                    auctionItemDTO.setOfferUserTotal(Conf.defaultTotal);
                    auctionItemDTO.setCurrentPrice(auctionItemDTO.getItemPrice());
                    auctionItemDTO.setLastOfferUseId(Conf.defaultId);
                    auctionItemDTO.setLastOfferUserAvatar(Conf.defaultString);
                    auctionItemDTO.setLastOfferUserName(Conf.defaultString);
                }
                ItemExtDO                      itemExtDO                 = itemExtDOMap.get(itemId);
                List<AuctionManualRecommendDO> auctionManualRecommendDOS = auctionManualRecommendDOMap.get(itemId);
                if (Utils.isNotEmpty(auctionManualRecommendDOS)) {
                    for (AuctionManualRecommendDO auctionManualRecommendDO : auctionManualRecommendDOS) {
                        if (auctionManualRecommendDO.getUserType().intValue() == Conf.AUCTION_RECOMMEND_NEW_USER.intValue()) {
                            auctionItemDTO.setNewRecommendWeight(auctionManualRecommendDO.getWeight());
                        }
                        if (auctionManualRecommendDO.getUserType().intValue() == Conf.AUCTION_RECOMMEND_OLD_USER.intValue()) {
                            auctionItemDTO.setOldRecommendWeight(auctionManualRecommendDO.getWeight());
                        }
                    }
                    if (auctionItemDTO.getNewRecommendWeight() == null) {
                        auctionItemDTO.setNewRecommendWeight(Conf.defaultWeight);
                    }
                    if (auctionItemDTO.getOldRecommendWeight() == null) {
                        auctionItemDTO.setOldRecommendWeight(Conf.defaultWeight);
                    }
                }
                if (auctionItemDTO.getNewRecommendWeight() == null) {
                    auctionItemDTO.setNewRecommendWeight(Conf.defaultWeight);
                }
                if (auctionItemDTO.getOldRecommendWeight() == null) {
                    auctionItemDTO.setOldRecommendWeight(Conf.defaultWeight);
                }
                if (itemExtDO != null) {
                    CustomerDO customerDO = finalCustomerDOMap.get(itemExtDO.getCuid());
                    if (customerDO != null) {
                        auctionItemDTO.setCustomerAvatar(Utils.getDefaultString(customerDO.getAvatar()));
                        auctionItemDTO.setCustomerName(Utils.getDefaultString(customerDO.getName()));
                    } else {
                        auctionItemDTO.setCustomerAvatar(Conf.defaultString);
                        auctionItemDTO.setCustomerName(Conf.defaultString);
                    }
                    CraftsmanDO craftsmanDO = finalCraftsmanDOMap.get(itemExtDO.getCuid());
                    if (craftsmanDO != null) {
                        auctionItemDTO.setCraftsmanStatus(craftsmanDO.getStatus());
                        auctionItemDTO.setCraftsmanTitle(craftsmanDO.getTitle());
                    } else {
                        //匠人状态 0 正常, 1 清退, 2 拉黑，添加默认值-1
                        auctionItemDTO.setCraftsmanStatus(Conf.defaultEnum);
                        auctionItemDTO.setCraftsmanTitle(Conf.defaultString);
                    }
                    auctionItemDTO.setItemExtStatus(Utils.getDefaultEnum(itemExtDO.getStatus()));
                    auctionItemDTO.setCraftsmanId(Utils.getDefaultId(itemExtDO.getCuid()));
                } else {
                    auctionItemDTO.setItemExtStatus(Conf.defaultEnum);
                    auctionItemDTO.setCraftsmanId(Conf.defaultId);
                    auctionItemDTO.setCraftsmanStatus(Conf.defaultEnum);
                }
//                final AuctionExtDO auctionExtDO = auctionExtMap.get(itemId);
//                if (auctionExtDO != null) {
//                    auctionItemDTO.setCreateTime(Utils.getDefault(auctionExtDO.getCreateTime()));
//                    auctionItemDTO.setUpdateTime(Utils.getDefaultTime(auctionExtDO.getUpdateTime()));
//                } else {
//                    auctionItemDTO.setCreateTime(Conf.defaultDate);
//                    auctionItemDTO.setUpdateTime(Conf.defaultDate);
//                }
                return assemble(auctionItemDTO);
            } catch (Exception e) {
                log.error("数据转换失败{}{}", e, auctionDO);
                return null;
            }
        }).filter(auctionItemEntity -> auctionItemEntity != null).collect(Collectors.toList());
    }

    @Override
    public AuctionEntity assemble(AuctionItemDTO auctionItemDTO) {
        if (auctionItemDTO == null) {
            return null;
        }
        AuctionEntity auctionItemEntity = new AuctionEntity();
        auctionItemEntity.setBookTotal(auctionItemDTO.getBookTotal());
        auctionItemEntity.setEndTime(auctionItemDTO.getAuctionEndTime());
        auctionItemEntity.setGroundId(auctionItemDTO.getAuctionGroundId());
        auctionItemEntity.setItemId(auctionItemDTO.getAuctionItemId());
        auctionItemEntity.setState(auctionItemDTO.getAuctionState());
        auctionItemEntity.setPlanEndTime(auctionItemDTO.getAuctionPlanEndTime());
        auctionItemEntity.setStartTime(auctionItemDTO.getAuctionStartTime());
        auctionItemEntity.setStatus(auctionItemDTO.getAuctionStatus());
        auctionItemEntity.setId(auctionItemDTO.getAuctionItemId().toString());
        auctionItemEntity.setSessionId(auctionItemDTO.getSessionId());
        auctionItemEntity.setSessionPreviewStatus(auctionItemDTO.getSessionPreview());
        auctionItemEntity.setSessionState(auctionItemDTO.getSessionState());
        auctionItemEntity.setSessionStatus(auctionItemDTO.getSessionStatus());
        auctionItemEntity.setSessionType(auctionItemDTO.getSessionType());
        auctionItemEntity.setItemCategoryId(auctionItemDTO.getItemCategoryId());
        auctionItemEntity.setCraftsmaneAvatar(auctionItemDTO.getCustomerAvatar());
        auctionItemEntity.setCraftsmanName(auctionItemDTO.getCustomerName());
        auctionItemEntity.setAuctionType(auctionItemDTO.getAuctionType());
        auctionItemEntity.setWeight(auctionItemDTO.getSessionWeight());
        auctionItemEntity.setCover(auctionItemDTO.getItemPicture());
        auctionItemEntity.setItemTitle(auctionItemDTO.getItemTitle());
        auctionItemEntity.setItemStatus(auctionItemDTO.getItemStatus());
        auctionItemEntity.setPrice(auctionItemDTO.getItemPrice());
        auctionItemEntity.setExtStatus(auctionItemDTO.getItemExtStatus());
        auctionItemEntity.setCraftsmanId(auctionItemDTO.getCraftsmanId());
        auctionItemEntity.setCurrentPrice(auctionItemDTO.getCurrentPrice());
        auctionItemEntity.setOfferTotal(auctionItemDTO.getOfferUserTotal());
        auctionItemEntity.setOfferUserId(auctionItemDTO.getLastOfferUseId());
        auctionItemEntity.setZooId(auctionItemDTO.getZooId());
        auctionItemEntity.setOldRecommendWeight(auctionItemDTO.getOldRecommendWeight());
        auctionItemEntity.setNewRecommendWeight(auctionItemDTO.getNewRecommendWeight());
        auctionItemEntity.setCellingPrice(auctionItemDTO.getCellingPrice());
        auctionItemEntity.setCraftsmanStatus(auctionItemDTO.getCraftsmanStatus());
        auctionItemEntity.setOfferUserAvatar(auctionItemDTO.getLastOfferUserAvatar());
        auctionItemEntity.setOfferUserName(auctionItemDTO.getLastOfferUserName());
        auctionItemEntity.setCraftsmanTitle(auctionItemDTO.getCraftsmanTitle());
        auctionItemEntity.setMinOfferInterval(auctionItemDTO.getMinOfferInterval());
        auctionItemEntity.setMaxOfferInterval(auctionItemDTO.getMaxOfferInterval());
        auctionItemEntity.setMuid(auctionItemDTO.getMuid());
        auctionItemEntity.setLadderId(auctionItemDTO.getLadderId());
        auctionItemEntity.setNextItemId(auctionItemDTO.getNextItemId());
        auctionItemEntity.setMaxContinuousOffer(auctionItemDTO.getMaxContinuousOffer());
        auctionItemEntity.setCreateTime(auctionItemDTO.getCreateTime());
        auctionItemEntity.setHot(auctionItemDTO.getHot());
        auctionItemEntity.setUpdateTime(auctionItemDTO.getUpdateTime());
        return auctionItemEntity;
    }

    public static void setAuctionDOFields(AuctionItemDTO auctionItemDTO, AuctionDO auctionDO) {
        auctionItemDTO.setAuctionEndTime(Utils.getDefaultShortTime(auctionDO.getEndtm()) * 1000);
        auctionItemDTO.setAuctionGroundId(Utils.getDefaultId(auctionDO.getGround()).intValue());
        auctionItemDTO.setAuctionId(auctionDO.getId());
        auctionItemDTO.setAuctionItemId(auctionDO.getIid());
        auctionItemDTO.setAuctionStartTime(Utils.getDefaultShortTime(auctionDO.getStarttm()) * 1000);
        auctionItemDTO.setAuctionPlanEndTime(Utils.getDefaultShortTime(auctionDO.getPlanetime()) * 1000);
        auctionItemDTO.setAuctionState(Utils.getDefaultEnum(auctionDO.getState()));
        auctionItemDTO.setAuctionType(Utils.getDefaultEnum(auctionDO.getType()));
        auctionItemDTO.setAuctionStatus(Utils.getDefaultEnum(auctionDO.getStatus()));
        auctionItemDTO.setMuid(Utils.getDefaultId(auctionDO.getMuid()));
        auctionItemDTO.setLadderId(Utils.getDefault(auctionDO.getLid()));
        auctionItemDTO.setNextItemId(Utils.getDefaultId(auctionDO.getNextiid()));
        auctionItemDTO.setMaxContinuousOffer(Utils.getDefault(auctionDO.getMaxContinuousOffer()));
        auctionItemDTO.setZooId(Utils.getDefaultId(auctionDO.getZid()));
        final BigDecimal minOfferInterval = auctionDO.getInterval();
        if (minOfferInterval != null) {
            auctionItemDTO.setMinOfferInterval(minOfferInterval.longValue() * 100);
        } else {
            auctionItemDTO.setMinOfferInterval(Conf.defaultPrice);
        }
        final BigDecimal maxOfferInterval = auctionDO.getMaxinterval();
        if (maxOfferInterval != null) {
            auctionItemDTO.setMaxOfferInterval(maxOfferInterval.longValue() * 100);
        } else {
            auctionItemDTO.setMaxOfferInterval(Conf.defaultPrice);
        }
        if (auctionDO.getCellingPrice() != null) {
            auctionItemDTO.setCellingPrice(auctionDO.getCellingPrice().longValue() * 100);
        } else {
            auctionItemDTO.setCellingPrice(Conf.defaultPrice);
        }
        auctionItemDTO.setHot(Utils.getDefaultWeight(auctionDO.getHot()));
    }

    public static void setAuctionSessionDOFields(AuctionItemDTO auctionItemDTO, AuctionSessionDO auctionSessionDO) {
        auctionItemDTO.setSessionId(Utils.getDefaultId(auctionSessionDO.getAsid()));
        auctionItemDTO.setSessionPreview(Utils.getDefaultEnum(auctionSessionDO.getPreview()));
        auctionItemDTO.setSessionState(Utils.getDefaultEnum(auctionSessionDO.getState()));
        auctionItemDTO.setSessionType(Utils.getDefaultEnum(auctionSessionDO.getType()));
        auctionItemDTO.setSessionStatus(Utils.getDefaultEnum(auctionSessionDO.getStatus()));
    }

}
