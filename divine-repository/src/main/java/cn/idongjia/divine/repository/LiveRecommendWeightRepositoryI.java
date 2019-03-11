package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.pojo.TabRecommendDO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author lc on 2018/8/6.
 * @class cn.idongjia.divine.repository.LiveRecommendWeightRepositoryI
 */
public interface LiveRecommendWeightRepositoryI {
    Future<Map<Long, TabRecommendDO>> assemble(List<Long> liveIds);
}
