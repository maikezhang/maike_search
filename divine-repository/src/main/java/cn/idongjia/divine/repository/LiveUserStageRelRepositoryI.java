package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.pojo.LiveUserStageRelDO;

import java.util.List;
import java.util.Map;

/**
 * @author lc on 2018/8/16.
 * @class cn.idongjia.divine.repository.LiveUserStageRelRepositoryI
 */
public interface LiveUserStageRelRepositoryI {
    public List<LiveUserStageRelDO> list(Long liveId);

    public Map<Long,List<LiveUserStageRelDO>>  assembleByLiveId(List<Long> liveIds);
}
