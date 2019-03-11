package cn.idongjia.divine.assembler;

import cn.idongjia.divine.db.es.entity.SessionLiveEntity;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionDO;
import cn.idongjia.divine.db.mybatis.pojo.CraftsmanDO;
import cn.idongjia.divine.db.mybatis.pojo.CustomerDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveShowDO;
import cn.idongjia.divine.db.mybatis.query.LiveShowQuery;
import cn.idongjia.divine.dto.SessionLiveDTO;
import cn.idongjia.divine.lib.pojo.Conf;
import cn.idongjia.divine.repository.CraftsmanRepositoryI;
import cn.idongjia.divine.repository.CustomerRepositoryI;
import cn.idongjia.divine.repository.LiveShowRepositoryI;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.divine.utils.exception.DivineException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author yuexiaodong@idongjia.cn
 * @date 2018-11-30
 */
@Repository
@Slf4j
public class SessionLiveAssembler implements AssemblerI<SessionLiveEntity, AuctionSessionDO, SessionLiveDTO> {
    @Resource
    private LiveShowRepositoryI  liveShowRepositoryI;
    @Resource
    private CraftsmanRepositoryI craftsmanRepositoryI;
    @Resource
    private CustomerRepositoryI  customerReposcustomerRepositoryI;

    @Override
    public List<SessionLiveEntity> assemble(List<AuctionSessionDO> dos) {
        if (Utils.isEmpty(dos)) {
            return Collections.emptyList();
        }
        List<Long>             liveCraftsmanIds = new ArrayList<>();
        Map<Long, CraftsmanDO> craftmanMap      = new HashMap<>();
        Map<Long, CustomerDO>  customerMap      = new HashMap<>();
        final List<Long> liveIds = dos.parallelStream()
                .map(sessionDO -> {
                    final Integer lsid = sessionDO.getLsid();
                    if (lsid == null) {
                        return null;
                    } else {
                        return lsid.longValue();
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        //直播id不为空
        Map<Long, LiveShowDO> LiveShowMap = Collections.emptyMap();
        if (!Utils.isEmpty(liveIds)) {
            LiveShowQuery liveShowQuery = LiveShowQuery.builder()
                    .liveIds(liveIds).build();
            final List<LiveShowDO> liveShowDOList = liveShowRepositoryI.select(liveShowQuery);
            if (!Utils.isEmpty(liveShowDOList)) {
                LiveShowMap = liveShowDOList.parallelStream()
                        .peek(liveShowDO -> {
                            final Integer craftsmanId = liveShowDO.getUid();
                            if (craftsmanId != null) {
                                liveCraftsmanIds.add(craftsmanId.longValue());
                            }
                        })
                        .collect(Collectors.toMap(x -> x.getId(), x -> x, (x1, x2) -> x1));
            }
        }
        try {
            craftmanMap = craftsmanRepositoryI.mapByCustomerId(liveCraftsmanIds).get();
        } catch (Exception e) {
            log.error("获取匠人失败{}", e);
            throw DivineException.failure("获取匠人失败" + liveCraftsmanIds);
        }
        try {
            customerMap = customerReposcustomerRepositoryI.mapByCustomerId(liveCraftsmanIds).get();
        } catch (Exception e) {
            log.error("获取用户失败{}", e);
            throw DivineException.failure("获取用户失败" + liveCraftsmanIds);
        }
        Map<Long, LiveShowDO>  finalLiveShowMap = LiveShowMap;
        Map<Long, CraftsmanDO> finalCraftmanMap = craftmanMap;
        Map<Long, CustomerDO>  finalCustomerMap = customerMap;
        final List<SessionLiveEntity> entities = dos.parallelStream()
                .map(sessionDO -> {
                    try {
                        if (sessionDO == null || sessionDO.getAsid() == null) {
                            return null;
                        }
                        if (sessionDO.getLsid() == null) {
                            return null;
                        }

                        final long       liveId     = sessionDO.getLsid().longValue();
                        final LiveShowDO liveShowDO = finalLiveShowMap.get(liveId);
                        if (liveShowDO == null || liveShowDO.getId() == null) {
                            return null;
                        }
                        SessionLiveEntity entity = new SessionLiveEntity();
                        //专场数据组装
                        entity.setId(sessionDO.getAsid().toString());
                        entity.setPlanEndTime(Utils.getDefaultShortTime(sessionDO.getPlanetime()) * 1000);
                        entity.setPlanStartTime(Utils.getDefaultShortTime(sessionDO.getPlanatime()) * 1000);
                        entity.setSessionId(Utils.getDefaultId(sessionDO.getAsid()));
                        entity.setSessionPreview(Utils.getDefaultEnum(sessionDO.getPreview()));
                        entity.setSessionState(Utils.getDefaultEnum(sessionDO.getState()));
                        entity.setSessionStatus(Utils.getDefaultEnum(sessionDO.getStatus()));
                        entity.setSessionWeight(Utils.getDefaultWeight(sessionDO.getWeight()));
                        entity.setSessionPic(Utils.getDefaultString(sessionDO.getPic()));
                        entity.setSessionTitle(Utils.getDefaultString(sessionDO.getTitle()));

                        //直播数据组装
                        entity.setLiveId(liveId);
                        entity.setLivePreStarTime(Utils.getDefaultTime(liveShowDO.getPrestarttm()));
                        entity.setLiveState(Utils.getDefaultEnum(liveShowDO.getState()));
                        entity.setLivePreViewTime(Utils.getDefaultTime(liveShowDO.getPreviewtm()));
                        if (liveShowDO.getUid() != null) {
                            final long        userId      = liveShowDO.getUid().longValue();
                            final CraftsmanDO craftsmanDO = finalCraftmanMap.get(userId);
                            final CustomerDO  customerDO  = finalCustomerMap.get(userId);
                            if (customerDO != null) {
                                entity.setLiveCraftsmanName(customerDO.getName());
                                entity.setLiveCraftsmanAvatar(customerDO.getAvatar());
                            } else {
                                entity.setLiveCraftsmanName(Conf.defaultString);
                                entity.setLiveCraftsmanAvatar(Conf.defaultString);
                            }
                            if (craftsmanDO != null) {
                                entity.setLiveCraftsmanTitle(craftsmanDO.getTitle());
                            } else {
                                entity.setLiveCraftsmanTitle(Conf.defaultString);
                            }
                        } else {
                            entity.setLiveCraftsmanName(Conf.defaultString);
                            entity.setLiveCraftsmanAvatar(Conf.defaultString);
                            entity.setLiveCraftsmanTitle(Conf.defaultString);
                        }
                        return entity;
                    } catch (Exception e) {
                        log.error("组装SessionLiveEntity数据失败 {}", sessionDO, e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return entities;
    }

    @Override
    public SessionLiveEntity assemble(SessionLiveDTO sessionLiveDTO) {
        return null;
    }

}
