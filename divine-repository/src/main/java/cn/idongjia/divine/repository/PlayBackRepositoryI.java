package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.pojo.CountPO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author lc on 2018/8/6.
 * @class cn.idongjia.divine.repository.PlayBackRepositoryI
 */
public interface PlayBackRepositoryI extends RepositoryI {
    Future<Map<Long, CountPO>> assemble(List<Long> liveIds);
}
