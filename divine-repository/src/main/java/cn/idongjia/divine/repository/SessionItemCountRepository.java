package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.mapper.kaipao.AuctionSessionRelMapper;
import cn.idongjia.divine.db.mybatis.pojo.CountPO;
import cn.idongjia.divine.db.mybatis.pojo.ItemRelPO;
import cn.idongjia.divine.db.mybatis.query.AuctionSessionRelQuery;
import cn.idongjia.divine.utils.Utils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/7/28.
 */
@Repository
public class SessionItemCountRepository implements SessionItemCountRepositoryI {
    @Resource
    private AuctionSessionRelMapper auctionSessionRelMapper;

    @Async
    @Override
    public Future<Map<Long, CountPO>> assemble(List<Long> sessionIds, Map<Long, Long> sessionLiveIdMap) {
        if (Utils.isEmpty(sessionIds)) {
            return new AsyncResult<>(new HashMap<>());
        }
        Map<Long, CountPO> itemCountMap = new HashMap<>();
        if (!Utils.isEmpty(sessionIds)) {
            AuctionSessionRelQuery auctionSessionQuery = AuctionSessionRelQuery.builder().sessionIds(sessionIds).build();
            List<CountPO>          countPOS            = auctionSessionRelMapper.countValid(auctionSessionQuery);
            if (!Utils.isEmpty(countPOS)) {
                Map<Long, CountPO> countPOMap = countPOS.stream().collect(Collectors.toMap(countPO -> sessionLiveIdMap.get(countPO.getId()), v1 -> v1, (v1, v2) -> v1));
                itemCountMap.putAll(countPOMap);
            }
        }
        return new AsyncResult<>(itemCountMap);
    }

    @Override
    public Map<Long, Integer> assembleRelCount(List<Long> sessionIds) {
        if (Utils.isEmpty(sessionIds)) {
            return Collections.emptyMap();
        }
        AuctionSessionRelQuery auctionSessionQuery = AuctionSessionRelQuery.builder().sessionIds(sessionIds).build();
        List<CountPO>          countPOS            = auctionSessionRelMapper.countValid(auctionSessionQuery);
        final Map<Long, Integer> countMap = countPOS.parallelStream()
                .collect(Collectors.toMap(x -> x.getId(), x -> x.getCount(), (x1, x2) -> x1));
        return countMap;
    }

    @Async
    @Override
    public Future<Map<Long, List<ItemRelPO>>> assembleItems(List<Long> sessionIds, Map<Long, Long> sessionLiveIdMap) {
        Map<Long, List<ItemRelPO>> liveItemRelMap = new HashMap<>();

        if (Utils.isNotEmpty(sessionIds)) {
            AuctionSessionRelQuery auctionSessionQuery = AuctionSessionRelQuery.builder().sessionIds(sessionIds).build();
            List<ItemRelPO>        itemRelPOS          = auctionSessionRelMapper.listItem(auctionSessionQuery);
            if (Utils.isNotEmpty(itemRelPOS)) {
                liveItemRelMap = itemRelPOS.stream().collect(Collectors.groupingBy(itemRelPO -> {
                    return sessionLiveIdMap.get(itemRelPO.getId());
                }));
            }
        }
        return new AsyncResult(liveItemRelMap);
    }
}
