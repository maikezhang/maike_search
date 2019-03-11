package cn.idongjia.divine.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Repository;

import cn.idongjia.divine.db.mybatis.mapper.kaipao.LiveResourceMapper;
import cn.idongjia.divine.db.mybatis.pojo.CountPO;
import cn.idongjia.divine.db.mybatis.pojo.ItemRelPO;
import cn.idongjia.divine.db.mybatis.query.LiveResourceQuery;
import cn.idongjia.util.Utils;

/**
 * @author lc
 * @create at 2018/7/28.
 */
@Repository
public class LiveItemCountRepository implements LiveItemCountRepositoryI {

    @Resource
    private LiveResourceMapper liveResourceMapper;

    @Async
    @Override
    public Future<Map<Long,CountPO>> assemble(List<Long> pureLiveIds) {
        if(Utils.isEmpty(pureLiveIds)) {
            return new AsyncResult<>(Collections.emptyMap());
        }
        Map<Long,CountPO> liveItemCountMap = null;
        if(!Utils.isEmpty(pureLiveIds)) {
            LiveResourceQuery liveResourceQuery = LiveResourceQuery.builder().liveIds(pureLiveIds).status(0).build();
            List<CountPO>     countPOS          = liveResourceMapper.countValid(liveResourceQuery);
            if(!Utils.isEmpty(countPOS)) {
                liveItemCountMap = countPOS.stream().collect(Collectors.toMap(CountPO::getId,v1 -> v1,(v1,v2) -> v1));
            }
        }
        if(liveItemCountMap == null) {
            liveItemCountMap = new HashMap<>();
        }
        return new AsyncResult<>(liveItemCountMap);
    }

    @Override
    public Future<Map<Long,List<ItemRelPO>>> assembleItems(List<Long> pureLiveIds) {
        if(Utils.isEmpty(pureLiveIds)) {
            return new AsyncResult<>(Collections.emptyMap());
        }
        Map<Long,List<ItemRelPO>> liveItemCountMap = null;
        if(!Utils.isEmpty(pureLiveIds)) {
			LiveResourceQuery liveResourceQuery = LiveResourceQuery.builder().resType(1).liveIds(pureLiveIds).status(0).build();
            List<ItemRelPO>   itemRelPOS        = liveResourceMapper.listItem(liveResourceQuery);
            if(!Utils.isEmpty(itemRelPOS)) {
                liveItemCountMap = itemRelPOS.stream().collect(Collectors.groupingBy(ItemRelPO::getId));
            }
        }
        if(liveItemCountMap == null) {
            liveItemCountMap = new HashMap<>();
        }
        return new AsyncResult<>(liveItemCountMap);
    }

    @Override
    public List<Long> assembleByItemIds(List<Long> itemIds) {
        if(Utils.isEmpty(itemIds)) {
            return new ArrayList<>();
        }
        List<Long> liveIds = null;
        if(!Utils.isEmpty(itemIds)) {
            LiveResourceQuery liveResourceQuery = LiveResourceQuery.builder().resourceIds(itemIds).status(0).build();
            liveIds= liveResourceMapper.listLive(liveResourceQuery);

        }

        return liveIds;
    }
}
