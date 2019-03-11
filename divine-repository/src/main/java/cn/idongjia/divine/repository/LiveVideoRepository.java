package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.mapper.kaipao.LiveVideoCoverMapper;
import cn.idongjia.divine.db.mybatis.pojo.LiveVideoCoverDO;
import cn.idongjia.divine.db.mybatis.query.LiveVideoCoverQuery;
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
public class LiveVideoRepository implements LiveVideoRepositoryI {
    @Resource
    private LiveVideoCoverMapper liveVideoCoverMapper;

    @Async
    @Override
    public Future<Map<Long, LiveVideoCoverDO>> assemble(List<Long> liveIds) {
        if (Utils.isEmpty(liveIds)) {
            return new AsyncResult<>(new HashMap<>(1));
        }
        LiveVideoCoverQuery liveVideoCoverQuery= LiveVideoCoverQuery.builder().liveIds(liveIds).build();
        List<LiveVideoCoverDO>      liveVideoPOS = liveVideoCoverMapper.select(liveVideoCoverQuery);
        Map<Long, LiveVideoCoverDO> videoPOMap   = new HashMap<>();
        if (!Utils.isEmpty(liveVideoPOS)) {
            Map<Long, LiveVideoCoverDO> liveVideoPOMap = liveVideoPOS.stream().collect(Collectors.toMap(LiveVideoCoverDO::getLiveId, v1 -> v1, (v1, v2) -> v1));
            videoPOMap.putAll(liveVideoPOMap);
        }
        return new AsyncResult<>(videoPOMap);
    }
}
