package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.mapper.kaipao.LivePureMapper;
import cn.idongjia.divine.db.mybatis.pojo.LivePureDO;
import cn.idongjia.divine.db.mybatis.query.LivePureQuery;
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
 * @create at 2018/7/31.
 */
@Repository
public class LivePureRepository implements LivePureRepositoryI {
    @Resource
    private LivePureMapper livePureMapper;

    @Async
    @Override
    public Future<Map<Long, LivePureDO>> assemble(List<Long> liveIds) {
        if (Utils.isEmpty(liveIds)) {
            return new AsyncResult<>(new HashMap<>());
        }
        LivePureQuery    livePureQuery = LivePureQuery.builder().liveIds(liveIds).build();
        List<LivePureDO> livePureDOS   = livePureMapper.select(livePureQuery);
        if (Utils.isEmpty(livePureDOS)) {
            return new AsyncResult<>(new HashMap<>());
        }
        return new AsyncResult<>(livePureDOS.stream().collect(Collectors.toMap(LivePureDO::getId, v1 -> v1, (v1, v2) -> v1)));
    }
}
