package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.pojo.LivePureDO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author lc on 2018/8/6.
 * @class cn.idongjia.divine.repository.LivePureRepositoryI
 */
public interface LivePureRepositoryI extends RepositoryI {
    Future<Map<Long, LivePureDO>> assemble(List<Long> liveIds);
}
