package cn.idongjia.divine.assembler;

import cn.idongjia.divine.db.es.entity.CategoryEntity;
import cn.idongjia.divine.db.es.entity.ItemEntity;
import cn.idongjia.divine.db.es.entity.LiveEntity;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionDO;
import cn.idongjia.divine.db.mybatis.pojo.CountPO;
import cn.idongjia.divine.db.mybatis.pojo.CraftsmanCategoryRelDO;
import cn.idongjia.divine.db.mybatis.pojo.CraftsmanDO;
import cn.idongjia.divine.db.mybatis.pojo.CustomerDO;
import cn.idongjia.divine.db.mybatis.pojo.ItemRelPO;
import cn.idongjia.divine.db.mybatis.pojo.LiveAnchorBlackWhiteDO;
import cn.idongjia.divine.db.mybatis.pojo.LivePureDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveShowDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveUserStageRelDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveVideoCoverDO;
import cn.idongjia.divine.db.mybatis.pojo.MediaDO;
import cn.idongjia.divine.db.mybatis.pojo.ZooDO;
import cn.idongjia.divine.dto.LiveDTO;
import cn.idongjia.divine.lib.pojo.Conf;
import cn.idongjia.divine.repository.AuctionBookRepositoryI;
import cn.idongjia.divine.repository.AuctionSessionRepositoryI;
import cn.idongjia.divine.repository.CraftsmanCategoryAuthRepostoryI;
import cn.idongjia.divine.repository.CraftsmanRepositoryI;
import cn.idongjia.divine.repository.CustomerRepositoryI;
import cn.idongjia.divine.repository.CustomerUserRelRepositoryI;
import cn.idongjia.divine.repository.LiveAnchorBlackWhiteRepostoryI;
import cn.idongjia.divine.repository.LiveBookRepositoryI;
import cn.idongjia.divine.repository.LiveItemCountRepositoryI;
import cn.idongjia.divine.repository.LivePureRepositoryI;
import cn.idongjia.divine.repository.LiveUserStageRelRepositoryI;
import cn.idongjia.divine.repository.LiveVideoRepositoryI;
import cn.idongjia.divine.repository.MediaRepositoryI;
import cn.idongjia.divine.repository.PlayBackRepositoryI;
import cn.idongjia.divine.repository.SessionItemCountRepositoryI;
import cn.idongjia.divine.repository.ZooMessageRepository;
import cn.idongjia.divine.repository.ZooMessageRepositoryI;
import cn.idongjia.divine.repository.ZooRepositoryI;
import cn.idongjia.divine.utils.DateTimeUtil;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.log.Log;
import cn.idongjia.log.LogFactory;
import cn.idongjia.zoo.pojo.ZooCountCO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/7/30.
 */
@Repository

public class LiveAssembler implements AssemblerI<LiveEntity, LiveShowDO, LiveDTO> {

    private static final Log logger = LogFactory.getLog(LiveAssembler.class);
    @Resource
    private LiveItemCountRepositoryI        liveItemCountRepositoryI;
    @Resource
    private CustomerRepositoryI             customerRepositoryI;
    @Resource
    private SessionItemCountRepositoryI     sessionItemCountRepositoryI;
    @Resource
    private LiveVideoRepositoryI            liveVideoRepositoryI;
    //    @Resource
//    private              LiveRecommendWeightRepositoryI  liveRecommendWeightRepositoryI;
    @Resource
    private CustomerUserRelRepositoryI      customerUserRelRepositoryI;
    @Resource
    private AuctionSessionRepositoryI       auctionSessionRepository;
    @Resource
    private PlayBackRepositoryI             playBackRepositoryI;
    @Resource
    private LivePureRepositoryI             livePureRepositoryI;
    @Resource
    private CraftsmanRepositoryI            craftsmanRepositoryI;
    @Resource
    private ZooRepositoryI                  zooRepositoryI;
    @Resource
    private AuctionBookRepositoryI          auctionBookRepositoryI;
    @Resource
    private LiveBookRepositoryI             liveBookRepositoryI;
    @Resource
    private CraftsmanCategoryAuthRepostoryI craftsmanCategoryAuthRepostoryI;
    @Resource
    private LiveUserStageRelRepositoryI     liveUserStageRelRepositoryI;
    @Resource
    private MediaRepositoryI                mediaRepositoryI;
    @Resource
    private LiveAnchorBlackWhiteRepostoryI  liveAnchorBlackWhiteRepostoryI;
    @Resource
    private ZooMessageRepositoryI           zooMessageRepositoryI;

    @Override
    public List<LiveEntity> assemble(List<LiveShowDO> liveShowDOS) {
        if (Utils.isEmpty(liveShowDOS)) {
            return new ArrayList<>();
        }
        List<Long>      sessionLiveIds = new ArrayList<>();
        List<Long>      userIds        = new ArrayList<>();
        List<Long>      pureLiveIds    = new ArrayList<>();
        List<Long>      liveIds        = new ArrayList<>();
        List<Long>      zooIds         = new ArrayList<>();
        Map<Long, Long> zooLiveMap     = new HashMap<>();
        List<Long>      itemIds        = new ArrayList<>();
        List<Long>      bookLiveIds    = new ArrayList<>();
        liveShowDOS.stream().forEach(liveShowDO -> {
            if (liveShowDO.getUid() != null) {
                userIds.add(liveShowDO.getUid().longValue());
            }
            liveIds.add(liveShowDO.getId());
            if (liveShowDO.getType().equals(Conf.LIVE_TYPE_SESSION)) {
                sessionLiveIds.add(liveShowDO.getId());
            } else {
                pureLiveIds.add(liveShowDO.getId());
            }
            bookLiveIds.add(liveShowDO.getId());
            zooIds.add(liveShowDO.getZid());
            zooLiveMap.put(liveShowDO.getZid(), liveShowDO.getId());
        });
        try {
            Future<Map<Long, Long>>       userCustomerFuture   = customerUserRelRepositoryI.assemble(userIds);
            Map<Long, CustomerDO>         customerDOMap;
            Map<Long, Long>               userCustomerMap      = userCustomerFuture.get();
            Future<Map<Long, CustomerDO>> customerFuture       = null;
            Map<Long, CraftsmanDO>        customerCraftsmanDOMap;
            Map<Long, Long>               customerCraftsmanMap = new HashMap<>();
            List<Long>                    craftsmanIds         = new ArrayList<>();
            // 这里面有三层关联 user<->customer<->craftsman
            if (!Utils.isEmpty(userCustomerMap)) {
                List<Long> customerIds = userCustomerMap.entrySet().stream().map(entity -> entity.getValue()).collect(Collectors.toList());
                customerFuture = customerRepositoryI.mapByCustomerId(customerIds);
                customerCraftsmanDOMap = craftsmanRepositoryI.mapByCustomerId(customerIds).get();
                if (Utils.isNotEmpty(customerCraftsmanDOMap)) {
                    customerCraftsmanDOMap.entrySet().stream().forEach(craftsmanEntry -> {
                        Long craftsmanId = craftsmanEntry.getValue().getId();
                        craftsmanIds.add(craftsmanId);
                        customerCraftsmanMap.put(craftsmanEntry.getKey(), craftsmanId);
                    });
                }
            } else {
                customerCraftsmanDOMap = new HashMap<>();
            }
            Map<Long, CountPO> bookTotalMap = new HashMap<>();
            if (Utils.isNotEmpty(bookLiveIds)) {
                Map<Long, CountPO> liveBookMap = liveBookRepositoryI.countByLiveId(bookLiveIds);
                if (Utils.isNotEmpty(liveBookMap)) {
                    bookTotalMap.putAll(liveBookMap);
                }
            }

            Map<Long, LiveAnchorBlackWhiteDO> liveAnchorBlackWhiteDOMap = liveAnchorBlackWhiteRepostoryI.mapStateByCraftsmanIds(userIds);
            Future<Map<Long, LivePureDO>>     livePureFuture            = livePureRepositoryI.assemble(pureLiveIds);

            Future<Map<Long, CountPO>> playBackFuture = playBackRepositoryI.assemble(liveIds);

            Map<Long, AuctionSessionDO> auctionSessionDOMap = auctionSessionRepository.mapByLiveId(sessionLiveIds);

            Map<Long, List<ItemRelPO>>         itemCountMap           = new HashMap<>();
            Future<Map<Long, List<ItemRelPO>>> sessionItemCountFuture = null;
            if (!Utils.isEmpty(auctionSessionDOMap)) {
                List<Long>      sessionIds     = auctionSessionDOMap.entrySet().stream().map(sessionDOEntry -> sessionDOEntry.getValue().getAsid().longValue()).collect(Collectors.toList());
                Map<Long, Long> sessionLiveMap = auctionSessionDOMap.entrySet().stream().collect(Collectors.toMap(entity -> entity.getValue().getAsid().longValue(), entity -> entity.getValue().getLsid().longValue(), (v1, v2) -> v1));

                sessionItemCountFuture = sessionItemCountRepositoryI.assembleItems(sessionIds, sessionLiveMap);
//                Map<Long, CountPO> sessionnBookTotalMap = auctionBookRepositoryI.countByContentId(sessionIds, Conf.SESSION_BOOK_TYPE);
//                if (Utils.isNotEmpty(sessionnBookTotalMap)) {
//                    sessionnBookTotalMap.entrySet().forEach(entity -> {
//                        Long liveId = sessionLiveMap.get(entity.getKey());
//                        bookTotalMap.put(liveId, entity.getValue());
//                    });
//                }
            }
            // 获取商品数量统计

            Future<Map<Long, List<ItemRelPO>>> liveItemFuture = liveItemCountRepositoryI.assembleItems(pureLiveIds);

            // 获取封面图数据

            Future<Map<Long, LiveVideoCoverDO>> videoFuture = liveVideoRepositoryI.assemble(liveIds);

            // 获取推荐权重

//            Future<Map<Long,TabRecommendDO>> tabRecommendFuture = liveRecommendWeightRepositoryI.assemble(liveIds);

            // 获取在线人数

//            Future<Map<Long, Integer>>              zooCountFuture = zooRepositoryI.assemble(zooIds, zooLiveMap);
            Map<Long, List<CraftsmanCategoryRelDO>> craftsmanCategoryMap;
            if (Utils.isNotEmpty(craftsmanIds)) {
                craftsmanCategoryMap = craftsmanCategoryAuthRepostoryI.assemble(craftsmanIds);
            } else {
                craftsmanCategoryMap = new HashMap<>();
            }

            Future<Map<Long, ZooCountCO>> mapCountCOFuture = zooRepositoryI.assembleCount(zooIds);

            //获取累计的发言次数
            Map<Long, Integer> mapZooMessageCount = zooMessageRepositoryI.assembleZooMessageCount(zooIds, Arrays.asList(0));


            Map<Long, List<LiveUserStageRelDO>> userStageRelMap = liveUserStageRelRepositoryI.assembleByLiveId(liveIds);

            Future<Map<Long, ZooDO>> zooFuture = zooRepositoryI.assemble(zooIds);
            if (sessionItemCountFuture != null) {
                Map<Long, List<ItemRelPO>> sessionItemCountMap = sessionItemCountFuture.get();
                itemCountMap.putAll(sessionItemCountMap);

            }
            Map<Long, List<ItemRelPO>> liveItemCountMap = liveItemFuture.get();
            if (!Utils.isEmpty(liveItemCountMap)) {
                itemCountMap.putAll(liveItemCountMap);
            }
            if (customerFuture != null) {
                customerDOMap = customerFuture.get();
            } else {
                customerDOMap = new HashMap<>();
            }
            if (Utils.isNotEmpty(itemCountMap)) {
                itemCountMap.values().stream().forEach(itemRelPOS -> {
                    List<Long> tmpItemIds = itemRelPOS.stream().map(itemRelPO -> {
                        return itemRelPO.getItemId();
                    }).collect(Collectors.toList());
                    if (Utils.isNotEmpty(tmpItemIds)) {
                        itemIds.addAll(tmpItemIds);
                    }
                });
            }
            Map<Long, MediaDO> itemCoverMap = null;
            if (Utils.isNotEmpty(itemIds)) {
                itemCoverMap = mediaRepositoryI.assembleByItemId(itemIds);
            } else {
                itemCoverMap = new HashMap<>();
            }
            Map<Long, List<ItemRelPO>>  finalItemCountMap = itemCountMap;
            Map<Long, CountPO>          playBackCountMap  = playBackFuture.get();
            Map<Long, LivePureDO>       livePureDOMap     = livePureFuture.get();
            Map<Long, LiveVideoCoverDO> videoPOMap        = videoFuture.get();
//            Map<Long, Integer>          uvMap             = zooCountFuture.get();
            Map<Long, ZooDO>      zooDOMap      = zooFuture.get();
            Map<Long, ZooCountCO> mapZooCountCO = mapCountCOFuture.get();


            Map<Long, MediaDO> finalItemCoverMap = itemCoverMap;
            return liveShowDOS.stream().map(liveShowDO -> {
                try {
                    Integer          zooMessageCount  = mapZooMessageCount.get(liveShowDO.getZid());
                    LiveDTO          liveDTO          = new LiveDTO();
                    Long             liveId           = liveShowDO.getId();
                    LivePureDO       livePureDO       = livePureDOMap.get(liveId);
                    AuctionSessionDO auctionSessionDO = auctionSessionDOMap.get(liveId);
                    Integer          sessionLiveType  = Conf.LIVE_TYPE_SESSION;
                    ZooCountCO       countCO          = mapZooCountCO.get(liveShowDO.getZid());
                    Integer          uv               = Conf.defaultTotal;
                    Integer          realUV           = Conf.defaultTotal;
                    if (Objects.nonNull(countCO)) {
                        uv = countCO.getCount();
                        realUV = countCO.getRealCount();

                    }
                    if (!liveShowDO.getType().equals(sessionLiveType)) {
                        liveDTO.setTitle(liveShowDO.getTitle());
                        if (livePureDO != null) {
                            liveDTO.setPic(livePureDO.getPic() == null ? "" : livePureDO.getPic());
                        }
                        liveDTO.setSessionId(Conf.defaultId);

                    } else if (liveShowDO.getType().equals(sessionLiveType) && auctionSessionDO != null) {
                        liveDTO.setTitle(Utils.getDefaultString(auctionSessionDO.getTitle()));
                        liveDTO.setSessionId(Utils.getDefaultId(auctionSessionDO.getAsid()));
                        liveDTO.setPic(Utils.getDefaultString(auctionSessionDO.getPic()));
                    } else {
                        liveDTO.setTitle(Conf.defaultString);
                        liveDTO.setPic(Conf.defaultString);
                        liveDTO.setSessionId(Conf.defaultId);
                    }
                    Integer                userId                 = liveShowDO.getUid();
                    Long                   userIdKey              = new Long(userId.longValue());
                    LiveAnchorBlackWhiteDO liveAnchorBlackWhiteDO = liveAnchorBlackWhiteDOMap.get(userIdKey);
                    if (liveAnchorBlackWhiteDO != null) {
                        liveDTO.setShowLocation(liveAnchorBlackWhiteDO.getType());
                    } else {
                        liveDTO.setShowLocation(1);
                    }
                    CustomerDO customerDO    = customerDOMap.get(userIdKey);
                    Integer    customerId    = customerDO.getId();
                    Long       customerIdKey = new Long(customerId.longValue());
                    if (customerDO != null) {
                        liveDTO.setCraftsmanAvatar(customerDO.getAvatar());
                        liveDTO.setCraftsmanName(customerDO.getName());
                    }
                    CraftsmanDO craftsmanDO = customerCraftsmanDOMap.get(customerIdKey);
                    if (craftsmanDO != null) {
                        liveDTO.setCraftsmanCity(Utils.getDefaultString(craftsmanDO.getCity()));
                        liveDTO.setCraftsmanTitle(Utils.getDefaultString(craftsmanDO.getTitle()));
                    }
                    List<ItemRelPO> itemRelPOS = finalItemCountMap.get(liveId);
                    if (Utils.isNotEmpty(itemRelPOS)) {
                        itemRelPOS.stream().forEach(itemRelPO -> {
                            Long    itemId  = itemRelPO.getItemId();
                            MediaDO mediaDO = finalItemCoverMap.get(itemId);
                            if (mediaDO != null) {
                                itemRelPO.setPicture(mediaDO.getMediaUrl());
                            }
                        });
                        liveDTO.setItemDOS(itemRelPOS);
                    } else {
                        liveDTO.setItemDOS(new ArrayList<>());
                    }
                    liveDTO.setUv(uv == null || liveShowDO.getState().intValue() == 1 || liveShowDO.getState().intValue() == 3 ? Conf.defaultTotal : uv);
                    liveDTO.setRealUV(Objects.isNull(realUV) || liveShowDO.getState().intValue() == 3 ? Conf.defaultTotal : realUV);
                    liveDTO.setTotalMessageCount(zooMessageCount == null ? 0 : zooMessageCount);

                    if (liveShowDO != null) {
                        liveDTO.setPreEndTime(DateTimeUtil.date2Millis(liveShowDO.getPreendtm()));
                        liveDTO.setPreStartTime(DateTimeUtil.date2Millis(liveShowDO.getPrestarttm()));
                        liveDTO.setScreenDirection(liveShowDO.getScreenDirection());
                        liveDTO.setStartTime(DateTimeUtil.date2Millis(liveShowDO.getStarttm()));
                        liveDTO.setState(Utils.getDefaultEnum(liveShowDO.getState()));
                        liveDTO.setStatus(Utils.getDefaultEnum(liveShowDO.getStatus()));
                        liveDTO.setUserId(Utils.getDefaultId(liveShowDO.getUid()));
                        liveDTO.setUpdateTime(Utils.getDefaultTime(liveShowDO.getModifiedtm()));
                        liveDTO.setVideoId(Utils.getDefaultId(liveShowDO.getVideoCoverId()));
                        liveDTO.setZooId(Utils.getDefaultId(liveShowDO.getZid()));
                        liveDTO.setPreviewTime(Utils.getDefaultTime(liveShowDO.getPreviewtm()));
                        liveDTO.setCreateTime(Utils.getDefaultTime(liveShowDO.getCreatetm()));
                        liveDTO.setEndTime(Utils.getDefaultTime(liveShowDO.getEndtm()));
                        liveDTO.setGeneralWeight(liveShowDO.getGeneralWeight());
                        liveDTO.setLiveType(Utils.getDefaultEnum(liveShowDO.getType()));
                        liveDTO.setOnline(Utils.getDefaultEnum(liveShowDO.getOnline()));
                        liveDTO.setRoomId(Utils.getDefaultId(liveShowDO.getRoomId()));
                    } else {
                        return null;
                    }
                    LiveVideoCoverDO liveVideoCoverDO = videoPOMap.get(liveId);
                    if (liveVideoCoverDO != null) {
                        liveDTO.setVideoDuration(Utils.getDefault(liveVideoCoverDO.getDuration()).longValue());
                        liveDTO.setVideoId(Utils.getDefaultId(liveVideoCoverDO.getId()));
                        liveDTO.setVideoPic(Utils.getDefaultString(liveVideoCoverDO.getPic()));
                        liveDTO.setVideoURL(Utils.getDefaultString(liveVideoCoverDO.getUrl()));
                    } else {
                        liveDTO.setVideoDuration(Conf.defaultDuration);
                        liveDTO.setVideoId(Conf.defaultId);
                        liveDTO.setVideoPic(Conf.defaultString);
                        liveDTO.setVideoURL(Conf.defaultString);
                    }
                    List<LiveUserStageRelDO> liveUserStageRelDOS = userStageRelMap.get(liveId);
                    if (Utils.isNotEmpty(liveUserStageRelDOS)) {
                        // 设置新老用户推荐权重
                        liveUserStageRelDOS.forEach(userStageRelDO -> {
                            if (userStageRelDO.getStage().intValue() == Conf.NEW_USER_STAGE.intValue()) {
                                liveDTO.setNewUserWeight(userStageRelDO.getWeight());
                            }
                            if (userStageRelDO.getStage().intValue() == Conf.OLD_USER_STAGE.intValue()) {
                                liveDTO.setOldUserWeight(userStageRelDO.getWeight());
                            }
                        });
                        if (liveDTO.getNewUserWeight() == null) {
                            liveDTO.setNewUserWeight(Conf.defaultWeight);
                        }
                        if (liveDTO.getOldUserWeight() == null) {
                            liveDTO.setOldUserWeight(Conf.defaultWeight);
                        }
                    } else {
                        liveDTO.setNewUserWeight(Conf.defaultWeight);
                        liveDTO.setOldUserWeight(Conf.defaultWeight);

                    }
                    CountPO countPO = playBackCountMap.get(liveId);
                    if (countPO != null) {
                        liveDTO.setHasPlayBack(countPO.getCount() > 0);
                    } else {
                        liveDTO.setHasPlayBack(false);
                    }
                    if (craftsmanDO != null) {
                        List<CraftsmanCategoryRelDO> categoryDOS = craftsmanCategoryMap.get(craftsmanDO.getId());
                        liveDTO.setCategoryDOS(categoryDOS);
                    }
//                    TabRecommendDO tabRecommendDO = liveRecommendPOMap.get(userIdKey);
//                    if(tabRecommendDO != null) {
//                        liveDTO.setRecommendWeight(tabRecommendDO.getWeight());
//                    } else {
//                        liveDTO.setRecommendWeight(Conf.defaultWeight);
//                    }
                    ZooDO zooDO = zooDOMap.get(liveShowDO.getZid());
                    if (zooDO != null) {
                        liveDTO.setZrc(zooDO.getZrc());
                        liveDTO.setSuid(zooDO.getUid());
                    }
                    liveDTO.setId(liveId.toString());
                    CountPO bookCountPO = bookTotalMap.get(liveId);
                    if (bookCountPO != null) {
                        liveDTO.setBookTotal(bookCountPO.getCount());
                    } else {
                        liveDTO.setBookTotal(Conf.defaultTotal);
                    }
                    return assemble(liveDTO);
                } catch (Exception e) {
                    logger.error("转换数据失败{}{}", e, liveShowDO);
                    return null;
                }

            }).filter(liveEntity -> liveEntity != null).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("保存通用直播列表失败{}", e);
        }
        return new ArrayList<>();
    }

    @Override
    public LiveEntity assemble(LiveDTO liveDTO) {
        List<ItemRelPO>              itemDOS     = liveDTO.getItemDOS();
        Integer                      userType    = liveDTO.getUserType();
        List<CraftsmanCategoryRelDO> categoryDOS = liveDTO.getCategoryDOS();
        LiveEntity                   liveEntity  = new LiveEntity();
        if (Utils.isNotEmpty(categoryDOS)) {
            List<CategoryEntity> categoryEntities = categoryDOS.stream().map(categoryDO -> {
                CategoryEntity categoryEntity = new CategoryEntity();
                categoryEntity.setCategoryId(categoryDO.getCategoryId().longValue());
                categoryEntity.setCategoryName(categoryDO.getCategoryName());
                return categoryEntity;
            }).collect(Collectors.toList());
            liveEntity.setCategoryEntities(categoryEntities);
        }

        liveEntity.setOldUserWeight(liveDTO.getOldUserWeight());
        liveEntity.setNewUserWeight(liveDTO.getNewUserWeight());

        liveEntity.setCrftsmanTitle(liveDTO.getCraftsmanTitle());
        liveEntity.setCity(liveDTO.getCraftsmanCity());
        liveEntity.setSuid(liveDTO.getSuid());
        liveEntity.setPreEndTime(liveDTO.getPreEndTime());
        liveEntity.setPreStartTime(liveDTO.getPreStartTime());
        liveEntity.setScreenDirection(liveDTO.getScreenDirection());
        liveEntity.setStartTime(liveDTO.getStartTime());
        liveEntity.setState(liveDTO.getState());
        liveEntity.setStatus(liveDTO.getStatus());
        liveEntity.setUserId(liveDTO.getUserId());
        liveEntity.setUpdateTime(liveDTO.getUpdateTime());
        liveEntity.setUv(liveDTO.getUv());
        liveEntity.setRealUV(liveDTO.getRealUV());
        liveEntity.setVideoId(liveDTO.getVideoId());
        liveEntity.setZid(liveDTO.getZooId());
        liveEntity.setPreViewTm(liveDTO.getPreviewTime());
        liveEntity.setCreateTime(liveDTO.getCreateTime());
        liveEntity.setEndTime(liveDTO.getEndTime());
        liveEntity.setGeneralWeight(liveDTO.getGeneralWeight());
        liveEntity.setLiveType(liveDTO.getLiveType());
        liveEntity.setOnline(liveDTO.getOnline());
        liveEntity.setTitle(liveDTO.getTitle());
        liveEntity.setRoomId(liveDTO.getRoomId());
        liveEntity.setBookTotal(liveDTO.getBookTotal());
        liveEntity.setSessionId(liveDTO.getSessionId());
        liveEntity.setShowLocation(liveDTO.getShowLocation());
        liveEntity.setAvatar(liveDTO.getCraftsmanAvatar());
        liveEntity.setCraftsmanName(liveDTO.getCraftsmanName());

        liveEntity.setId(liveDTO.getId());
        liveEntity.setHasPlayBack(liveDTO.getHasPlayBack());
        liveEntity.setUserType(userType);
        liveEntity.setTotalMessageCount(liveDTO.getTotalMessageCount());

        List<ItemEntity> itemEntities = null;
        if (Utils.isNotEmpty(itemDOS)) {
            itemEntities = itemDOS.stream().map(itemRelPO -> {
                ItemEntity itemEntity = new ItemEntity();
                itemEntity.setItemId(itemRelPO.getItemId());
                itemEntity.setPicture(itemRelPO.getPicture());
                itemEntity.setPrice(itemRelPO.getPrice());
                itemEntity.setTitle(itemRelPO.getTitle());
                return itemEntity;
            }).collect(Collectors.toList());
            liveEntity.setItems(itemEntities);
        } else if (itemDOS != null && itemDOS.size() == 0) {
            liveEntity.setItems(new ArrayList<>());
        }
        liveEntity.setPic(liveDTO.getPic());
        liveEntity.setTitle(liveDTO.getTitle());

        liveEntity.setVideoUrl(liveDTO.getVideoURL());
        liveEntity.setDuration(liveDTO.getVideoDuration());
        liveEntity.setVideoPic(liveDTO.getVideoPic());
//        liveEntity.setRecommendWeight(liveDTO.getRecommendWeight());
        liveEntity.setZrc(liveDTO.getZrc());
        return liveEntity;
    }

}
