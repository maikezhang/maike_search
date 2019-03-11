package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.pojo.LiveVideoCoverDO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author lc on 2018/8/6.
 * @class cn.idongjia.divine.repository.LiveVideoRepositoryI
 */
public interface LiveVideoRepositoryI extends RepositoryI {
    Future<Map<Long, LiveVideoCoverDO>> assemble(List<Long> liveIds);
}
