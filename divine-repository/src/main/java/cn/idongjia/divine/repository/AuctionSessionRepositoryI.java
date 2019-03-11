package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionDO;
import cn.idongjia.divine.db.mybatis.query.AuctionSessionQuery;

import java.util.List;
import java.util.Map;

/**
 * @author lc on 2018/8/6.
 * @class cn.idongjia.divine.repository.AuctionSessionRepositoryI
 */
public interface AuctionSessionRepositoryI {
    Map<Long, AuctionSessionDO> mapByLiveId(List<Long> liveIds);

    AuctionSessionDO getByPrimaryKey(int id);

    Map<Long,AuctionSessionDO> mapBySessionIds(List<Long> sessionIds);

    int count(AuctionSessionQuery auctionSessionQuery);

    List<AuctionSessionDO> select(AuctionSessionQuery auctionSessionQuery);
}
