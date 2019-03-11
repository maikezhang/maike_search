package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.mapper.kaipao.TabRecommendMapper;
import cn.idongjia.divine.db.mybatis.pojo.TabRecommendDO;
import cn.idongjia.divine.db.mybatis.query.TabRecommendQuery;
import cn.idongjia.util.Utils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
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
public class LiveRecommendWeightRepository implements LiveRecommendWeightRepositoryI {

    @Resource
    private TabRecommendMapper tabRecommendMapper;

    @Async
    @Override
    public Future<Map<Long, TabRecommendDO>> assemble(List<Long> liveIds) {
        if (Utils.isEmpty(liveIds)) {
            return new AsyncResult<>(new HashMap<>(1));

        }
        TabRecommendQuery         tabRecommendQuery  = TabRecommendQuery.builder().ids(liveIds).type(5).build();
        List<TabRecommendDO>      liveRecommendPOS   = tabRecommendMapper.select(tabRecommendQuery);
        Map<Long, TabRecommendDO> liveRecommendPOMap = new HashMap<>();
        if (!Utils.isEmpty(liveRecommendPOS)) {
            Map<Long, TabRecommendDO> recommendPOMap = liveRecommendPOS.stream().collect(Collectors.toMap(TabRecommendDO::getId, v1 -> v1, (v1, v2) -> v1));
            liveRecommendPOMap.putAll(recommendPOMap);
        }
        return new AsyncResult<>(liveRecommendPOMap);
    }
}
