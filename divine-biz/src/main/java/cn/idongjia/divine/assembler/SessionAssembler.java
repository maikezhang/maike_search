package cn.idongjia.divine.assembler;

import cn.idongjia.divine.db.es.entity.SessionEntity;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionDO;
import cn.idongjia.divine.db.mybatis.pojo.CountPO;
import cn.idongjia.divine.db.mybatis.pojo.CraftsmanDO;
import cn.idongjia.divine.db.mybatis.pojo.CustomerDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveShowDO;
import cn.idongjia.divine.db.mybatis.query.LiveShowQuery;
import cn.idongjia.divine.dto.SessionDTO;
import cn.idongjia.divine.lib.pojo.Conf;
import cn.idongjia.divine.manager.AuctionManager;
import cn.idongjia.divine.repository.AuctionOfferRepositoryI;
import cn.idongjia.divine.repository.CraftsmanRepositoryI;
import cn.idongjia.divine.repository.CustomerRepositoryI;
import cn.idongjia.divine.repository.CustomerUserRelRepositoryI;
import cn.idongjia.divine.repository.LiveShowRepositoryI;
import cn.idongjia.divine.repository.SessionItemCountRepositoryI;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.log.Log;
import cn.idongjia.log.LogFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/7/30.
 */
@Repository
public class SessionAssembler implements AssemblerI<SessionEntity, AuctionSessionDO, SessionDTO> {
    private static final Log                         logger = LogFactory.getLog(SessionAssembler.class);
    @Resource
    private              CustomerRepositoryI         customerRepositoryI;
    @Resource
    private              CustomerUserRelRepositoryI  customerUserRelRepositoryI;
    @Resource
    private              AuctionManager              auctionManager;
    @Resource
    private              CraftsmanRepositoryI        craftsmanRepositoryI;
    @Resource
    private              LiveShowRepositoryI         liveShowRepositoryI;
    @Resource
    private              AuctionOfferRepositoryI     auctionOfferRepositoryI;
    @Resource
    private              SessionItemCountRepositoryI sessionItemCountRepository;

    @Override
    public List<SessionEntity> assemble(List<AuctionSessionDO> auctionSessionDOS) {
        if (Utils.isEmpty(auctionSessionDOS)) {
            return new ArrayList<>();
        }
        List<Long> sessionIds = new ArrayList<>();
        List<Long> userIds    = new ArrayList<>();
        List<Long> liveIds    = new ArrayList<>();
        auctionSessionDOS.stream().forEach(auctionSessionDO -> {
            sessionIds.add(auctionSessionDO.getAsid().longValue());
            if (auctionSessionDO.getType().equals(Conf.LIVE_TYPE_SESSION) && auctionSessionDO.getLsid() != null) {
                liveIds.add(auctionSessionDO.getLsid().longValue());
            }
            if (auctionSessionDO.getUtype().intValue() == 0) {
                userIds.add(auctionSessionDO.getUid().longValue());
            }
        });
        Map<Long, LiveShowDO> liveShowDOMap;
        if (Utils.isNotEmpty(liveIds)) {
            List<LiveShowDO> liveShowDOS = liveShowRepositoryI.select(LiveShowQuery.builder().liveIds(liveIds).build());
            liveShowDOMap = liveShowDOS.stream().collect(Collectors.toMap(LiveShowDO::getId, v1 -> v1, (v1, v2) -> v1));
        } else {
            liveShowDOMap = new HashMap<>();
        }
        try {
            Map<Long, CountPO>       countPOMap         = auctionOfferRepositoryI.groupBySessionIds(sessionIds);
            Future<Map<Long, Long>>  userCustomerFuture = customerUserRelRepositoryI.assemble(userIds);
            Map<Long, Long>          userCustomerMap    = userCustomerFuture.get();
            Map<Long, CustomerDO>    customerDOMap;
            Map<Long, CraftsmanDO>   craftsmanDOMap     = craftsmanRepositoryI.mapByCustomerId(userIds).get();
            final Map<Long, Integer> relatedCountMap    = sessionItemCountRepository.assembleRelCount(sessionIds);
            // 这里面有三层关联 user<->customer<->craftsman
            if (!Utils.isEmpty(userCustomerMap)) {
                List<Long> customerIds = userCustomerMap.entrySet().stream().map(entity -> entity.getValue()).collect(Collectors.toList());
                customerDOMap = customerRepositoryI.mapByCustomerId(customerIds).get();
            } else {
                customerDOMap = new HashMap<>();
            }
            List<Long> djtCraftsmanIds = auctionManager.listDJT(userIds);
            return auctionSessionDOS.stream().map(auctionSessionDO -> {
                try {
                    if (auctionSessionDO.getAsid() == null || auctionSessionDO.getAsid() == 0) {
                        return null;
                    }
                    SessionDTO sessionDTO          = new SessionDTO();
                    Integer    auctionSessionDOUid = auctionSessionDO.getUid();
                    CustomerDO customerDO          = customerDOMap.get(auctionSessionDOUid.longValue());
                    if (customerDO != null && auctionSessionDO.getUtype().intValue() == 0) {
                        sessionDTO.setCraftsmanAvatar(customerDO.getAvatar());
                        sessionDTO.setCraftsmanName(customerDO.getName());
                    }
                    if (djtCraftsmanIds.contains(Long.valueOf(auctionSessionDO.getUid().longValue()))) {
                        sessionDTO.setDjtFlag(1);
                    } else {
                        sessionDTO.setDjtFlag(0);

                    }
                    CraftsmanDO craftsmanDO = craftsmanDOMap.get(auctionSessionDO.getUid().longValue());
                    if (craftsmanDO != null) {
                        sessionDTO.setCraftsmanTitle(craftsmanDO.getTitle());
                    } else {
                        sessionDTO.setCraftsmanTitle(Conf.defaultString);
                    }
                    boolean isSessionLive = false;
                    if (auctionSessionDO.getType().intValue() == Conf.LIVE_TYPE_SESSION.intValue() && auctionSessionDO.getLsid() != null) {
                        Long       liveId     = Long.valueOf(auctionSessionDO.getLsid().longValue());
                        LiveShowDO liveShowDO = liveShowDOMap.get(liveId);
                        if (liveShowDO != null) {
                            sessionDTO.setLiveId(liveShowDO.getId());
                            sessionDTO.setLivePreStartTime(Utils.getDefaultTime(liveShowDO.getPrestarttm()));
                            sessionDTO.setLiveState(Utils.getDefaultEnum(liveShowDO.getState()));
                            isSessionLive = true;
                        }
                    }
                    CountPO countPO = countPOMap.get(auctionSessionDO.getAsid().longValue());
                    if (countPO != null) {
                        sessionDTO.setOfferTotal(countPO.getCount());
                    } else {
                        sessionDTO.setOfferTotal(0);
                    }
                    if (!isSessionLive) {
                        sessionDTO.setLiveId(Conf.defaultId);
                        sessionDTO.setLivePreStartTime(Conf.defaultDate);
                        sessionDTO.setLiveState(Conf.defaultEnum);
                    }
                    final Integer relatedCount = relatedCountMap.getOrDefault(auctionSessionDO.getAsid().longValue(), 0);
                    sessionDTO.setRelatedCount(relatedCount);
                    sessionDTO.setCreatorId(Utils.getDefaultId(auctionSessionDO.getUid()));
                    sessionDTO.setCreatorType(Utils.getDefaultEnum(auctionSessionDO.getUtype()));
                    sessionDTO.setForNewUser(Utils.getDefaultEnum(auctionSessionDO.getForNewUser()));
                    sessionDTO.setHotWeight(Utils.getDefaultWeight(auctionSessionDO.getHot()));
                    sessionDTO.setPic(Utils.getDefaultString(auctionSessionDO.getPic()));
                    sessionDTO.setPlanEndTime(Utils.getDefaultShortTime(auctionSessionDO.getPlanetime()) * 1000);
                    sessionDTO.setPlanStartTime(Utils.getDefaultShortTime(auctionSessionDO.getPlanatime()) * 1000);
                    sessionDTO.setPreview(Utils.getDefaultEnum(auctionSessionDO.getPreview()));
                    sessionDTO.setSessionId(auctionSessionDO.getAsid().longValue());
                    sessionDTO.setState(Utils.getDefaultEnum(auctionSessionDO.getState()));
                    sessionDTO.setStatus(Utils.getDefaultEnum(auctionSessionDO.getStatus()));
                    sessionDTO.setSessionType(Utils.getDefaultEnum(auctionSessionDO.getType()));
                    sessionDTO.setTitle(Utils.getDefaultString(auctionSessionDO.getTitle()));
                    sessionDTO.setWeight(Utils.getDefaultWeight(auctionSessionDO.getWeight()));
                    sessionDTO.setAinterval(Utils.getDefault(auctionSessionDO.getAinterval()));
                    sessionDTO.setDeposit(Utils.getDefault(auctionSessionDO.getDeposit()));
                    sessionDTO.setCreateTime(Utils.getDefaultShortTime(auctionSessionDO.getTimestamp()) * 1000);
                    return assemble(sessionDTO);
                } catch (Exception e) {
                    logger.error("转换数据失败{}{}", e, auctionSessionDO);
                    return null;
                }

            }).filter(liveEntity -> liveEntity != null).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("保存专场失败{}", e);
        }
        return new ArrayList<>();
    }

    @Override
    public SessionEntity assemble(SessionDTO dto) {
        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setCraftsmanAvatar(dto.getCraftsmanAvatar());
        sessionEntity.setCraftsmanName(dto.getCraftsmanName());
        sessionEntity.setCreatorId(dto.getCreatorId());
        sessionEntity.setCreatorType(dto.getCreatorType());
        sessionEntity.setDjtFlag(dto.getDjtFlag());
        sessionEntity.setForNewUser(dto.getForNewUser());
        sessionEntity.setHotWeight(dto.getHotWeight());
        sessionEntity.setLiveId(dto.getLiveId());
        sessionEntity.setLivePreStarTime(dto.getLivePreStartTime());
        sessionEntity.setLiveState(dto.getLiveState());
        sessionEntity.setPic(dto.getPic());
        sessionEntity.setPlanEndTime(dto.getPlanEndTime());
        sessionEntity.setPlanStartTime(dto.getPlanStartTime());
        sessionEntity.setPreview(dto.getPreview());
        sessionEntity.setSessionId(dto.getSessionId());
        sessionEntity.setSessionType(dto.getSessionType());
        sessionEntity.setState(dto.getState());
        sessionEntity.setId(dto.getSessionId().toString());
        sessionEntity.setStatus(dto.getStatus());
        sessionEntity.setTitle(dto.getTitle());
        sessionEntity.setOfferTotal(dto.getOfferTotal());
        sessionEntity.setWeight(dto.getWeight());
        sessionEntity.setCraftsmanTitle(dto.getCraftsmanTitle());
        sessionEntity.setAinterval(dto.getAinterval());
        sessionEntity.setDeposit(dto.getDeposit());
        sessionEntity.setRelatedCount(dto.getRelatedCount());
        sessionEntity.setCreateTime(dto.getCreateTime());
        return sessionEntity;
    }

}
