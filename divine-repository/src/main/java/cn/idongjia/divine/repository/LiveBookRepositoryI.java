package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.pojo.CountPO;

import java.util.List;
import java.util.Map;

/**
 * @author lc on 2018/8/10.
 * @class cn.idongjia.divine.repository.LiveBookRepositoryI
 */
public interface LiveBookRepositoryI {
    /**
     * 根据直播id统计订阅数据
     * @param liveId
     * @return
     */
    public int countByLiveId(Long liveId);

    public Map<Long,CountPO> countByLiveId(List<Long> liveIds);

}
