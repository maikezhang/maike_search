package cn.idongjia.divine.timer;

import cn.idongjia.divine.biz.LiveLoadBO;
import cn.idongjia.divine.db.es.entity.LiveEntity;
import cn.idongjia.divine.db.mybatis.mapper.kaipao.LiveShowMapper;
import cn.idongjia.divine.db.mybatis.pojo.LiveShowDO;
import cn.idongjia.divine.db.mybatis.query.LiveShowQuery;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.repository.ZooMessageRepositoryI;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.schedule.AbstractScheduleTask;
import cn.idongjia.zoo.api.ZooCountService;
import cn.idongjia.zoo.pojo.ZooCountCO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/8/24.
 */
@Component
@Slf4j
public class LiveUVUpdateTimer {
//    private static final String UV_UPDATE_TASK = "UV_UPDATE_TASK";

    @Resource
    private ZooCountService zooCountService;

    @Resource
    private LiveShowMapper liveShowMapper;

    @Resource
    private ESUpsertManager esUpsertManager;

    @Resource
    private ZooMessageRepositoryI zooMessageRepositoryI;

    public void update() {
        log.info("开始更新直接热度........");
        LiveShowQuery    liveShowQuery = LiveShowQuery.builder().statusArray(LiveLoadBO.ALL_LIVE_STATUS).states(Arrays.asList(1, 2)).build();
        List<LiveShowDO> liveShowDOS   = liveShowMapper.select(liveShowQuery);
        if (Utils.isNotEmpty(liveShowDOS)) {
            List<Long>      zooIds      = new ArrayList<>();
            Map<Long, Long> zooLiveMap  = new HashMap<>();
            List<Long>      unstartLive = new ArrayList<>();
            liveShowDOS.stream().forEach(liveShowDO -> {
                zooIds.add(liveShowDO.getZid());
                if (liveShowDO.getState().intValue() == 1) {
                    unstartLive.add(liveShowDO.getId());
                }
                zooLiveMap.put(liveShowDO.getZid(), liveShowDO.getId());
            });
            List<Long>            zids         = zooIds.stream().distinct().collect(Collectors.toList());
            Map<Long, ZooCountCO> countZoos    = zooCountService.mapBatchZooCount(zids);
            List<LiveEntity>      liveEntities = new ArrayList<>();

            //获取直播的累计发言次数
            Map<Long, Integer> mapMessageCount = zooMessageRepositoryI.assembleZooMessageCount(zids, Arrays.asList(0));

            liveShowDOS.forEach(liveShowDO -> {
                LiveEntity liveEntity   = new LiveEntity();
                Integer    messageCount = mapMessageCount.get(liveShowDO.getZid());
                liveEntity.setTotalMessageCount(Objects.isNull(messageCount) ? 0 : messageCount);
                ZooCountCO countCO = countZoos.get(liveShowDO.getZid());
                if (unstartLive.contains(liveShowDO.getId())) {
                    liveEntity.setUv(0);
                } else {
                    liveEntity.setUv(Objects.isNull(countCO) ? 0 : countCO.getCount());
                }
                liveEntity.setRealUV(Objects.isNull(countCO) ? 0 : countCO.getRealCount());
                liveEntity.setId(liveShowDO.getId().toString());
                liveEntities.add(liveEntity);
            });
//
//            for (int i = countZoos.size() - 1; i >= 0; i--) {
//                Long       zooId      = zooIds.get(i);
//                LiveEntity liveEntity = new LiveEntity();
//                Long       liveId     = zooLiveMap.get(zooId);
//                if (unstartLive.contains(liveId)) {
//                    liveEntity.setUv(0);
//                } else {
//                    liveEntity.setUv(countZoos.get(zooId));
//                }
//                liveEntity.setId(liveId.toString());
//                liveEntities.add(liveEntity);
//            }
            esUpsertManager.bulkLiveUpsert(liveEntities);

        }
    }
}
