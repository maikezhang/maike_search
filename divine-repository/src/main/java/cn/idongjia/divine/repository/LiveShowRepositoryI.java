package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.pojo.LiveShowDO;
import cn.idongjia.divine.db.mybatis.query.LiveShowQuery;

import java.util.List;

/**
 * @author lc on 2018/8/6.
 * @class cn.idongjia.divine.repository.LiveShowRepositoryI
 */
public interface LiveShowRepositoryI extends RepositoryI {
    List<LiveShowDO> select(LiveShowQuery liveShowQuery);

    LiveShowDO getByPrimaryKey(Long liveId);

    int count(LiveShowQuery liveShowQuery);
}
