package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.pojo.AuctionOfferDO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionOfferUserDO;
import cn.idongjia.divine.db.mybatis.pojo.CountPO;

import java.util.List;
import java.util.Map;

/**
 * @author lc on 2018/8/16.
 * @class cn.idongjia.divine.repository.AuctionOfferRepositoryI
 */
public interface AuctionOfferRepositoryI {
    Map<Long,AuctionOfferUserDO> groupByItemId(List<Long> itemIds);
    Map<Long,CountPO> groupBySessionIds(List<Long> sessionIds);
}
