package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.mapper.kaipao.LivePlaybackMapper;
import cn.idongjia.divine.db.mybatis.pojo.CountPO;
import cn.idongjia.divine.db.mybatis.query.LivePlaybackQuery;
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
public class PlayBackRepository implements PlayBackRepositoryI {


    @Resource
    private LivePlaybackMapper livePlaybackMapper;

    @Async
    @Override
    public Future<Map<Long, CountPO>> assemble(List<Long> liveIds) {
        if (Utils.isEmpty(liveIds)) {
            return new AsyncResult<>(new HashMap<>());
        }
        LivePlaybackQuery livePlaybackQuery = LivePlaybackQuery.builder().minDuration(300000L).liveIds(liveIds).build();
        List<CountPO>     countPOS          = livePlaybackMapper.countValid(livePlaybackQuery);
        if (Utils.isEmpty(countPOS)) {
            return new AsyncResult<>(new HashMap<>());
        }
        return new AsyncResult<>(countPOS.stream().collect(Collectors.toMap(CountPO::getId, v1 -> v1, (v1, v2) -> v1)));
    }
}
