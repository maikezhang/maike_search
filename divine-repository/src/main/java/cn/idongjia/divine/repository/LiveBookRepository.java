package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.mapper.kaipao.LiveBookMapper;
import cn.idongjia.divine.db.mybatis.pojo.CountPO;
import cn.idongjia.divine.db.mybatis.query.LiveBookQuery;
import cn.idongjia.divine.utils.Utils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/8/10.
 */
@Repository
public class LiveBookRepository implements LiveBookRepositoryI {
    @Resource
    private LiveBookMapper liveBookMapper;

    /**
     * 根据直播id统计订阅数据
     *
     * @param liveId
     * @return
     */
    @Override
    public int countByLiveId(Long liveId) {
        LiveBookQuery liveBookQuery = new LiveBookQuery();
        liveBookQuery.setLid(liveId);
        liveBookQuery.setStatus(0);
        return liveBookMapper.count(liveBookQuery);
    }

    @Override
    public Map<Long,CountPO> countByLiveId(List<Long> liveIds) {
        List<CountPO>     countPOS = liveBookMapper.groupByLiveIds(liveIds);
        Map<Long,CountPO> map      = null;
        if(Utils.isNotEmpty(countPOS)) {
            map = countPOS.stream().collect(Collectors.toMap(CountPO::getId,v1 -> v1,(v1,v2) -> v1));
        }
        if(map == null) {
            map = new HashMap<>();
        }
        return map;
    }
}
