package cn.idongjia.divine.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import cn.idongjia.log.Log;
import cn.idongjia.log.LogFactory;
import cn.idongjia.zoo.pojo.ZooCountCO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Repository;

import cn.idongjia.divine.db.mybatis.mapper.zoo.ZooMapper;
import cn.idongjia.divine.db.mybatis.pojo.ZooDO;
import cn.idongjia.divine.db.mybatis.query.ZooQuery;
import cn.idongjia.util.Utils;
import cn.idongjia.zoo.api.ZooCountService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lc
 * @create at 2018/7/31.
 */
@Repository
@Slf4j
public class ZooRepository implements ZooRepositoryI {
    @Resource
    private ZooMapper zooMapper;

    @Resource
    private ZooCountService zooCountService;

    private static final Log LOGGER = LogFactory.getLog(ZooRepository.class);


    @Async
    @Override
    public Future<Map<Long, ZooDO>> assemble(List<Long> zooIds) {
        if (Utils.isEmpty(zooIds)) {
            return new AsyncResult<>(new HashMap<>(1));
        }
        ZooQuery    zooQuery = ZooQuery.builder().zooIds(zooIds).build();
        List<ZooDO> zooDOS   = zooMapper.select(zooQuery);
        if (Utils.isEmpty(zooDOS)) {
            return new AsyncResult<>(new HashMap<>(1));
        }
        return new AsyncResult<>(zooDOS.stream().collect(Collectors.toMap(ZooDO::getZid, v1 -> v1, (v1, v2) -> v1)));
    }

    @Async
    @Override
    public Future<Map<Long, Integer>> assemble(List<Long> zooIds, Map<Long, Long> zooLiveMap) {
        if (Utils.isEmpty(zooIds)) {
            return new AsyncResult<>(new HashMap<>(1));
        }
        Map<Long, Integer> uvMap = new HashMap<>();
        if (!Utils.isEmpty(zooIds)) {
            Map<Long, Integer> countZoos = zooCountService.batchCountZoo(zooIds);
            log.info("获取直播间热度{}", countZoos);
            for (int i = 0; i < zooIds.size(); i++) {
                Long zid = zooIds.get(i);
                uvMap.put(zooLiveMap.get(zid), countZoos.get(zid));
            }
        }
        return new AsyncResult<>(uvMap);
    }

    @Override
    public Future<Map<Long, ZooCountCO>> assembleCount(List<Long> zooIds) {

        if (Utils.isEmpty(zooIds)) {
            return new AsyncResult<>(new HashMap<>(1));
        }
        Map<Long, ZooCountCO> mapCount = new HashMap<>();
        try {
            mapCount = zooCountService.mapBatchZooCount(zooIds);

        } catch (Exception e) {
            LOGGER.warn("调用cn.idongjia.zoo.api.ZooCountService#mapBatchZooCount失败，param:{},exception:{}", zooIds, e);
            return new AsyncResult<>(new HashMap<>(1));
        }

        return new AsyncResult<>(mapCount);
    }
}
