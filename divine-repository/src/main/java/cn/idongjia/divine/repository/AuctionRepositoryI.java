package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.pojo.AuctionDO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionStateDO;
import cn.idongjia.divine.db.mybatis.query.AuctionQuery;

import java.util.List;

/**
 * @author lc on 2018/8/16.
 * @class cn.idongjia.divine.repository.AuctionRepository
 */
public interface AuctionRepositoryI {
    AuctionDO getByItemId(Long itemId);

    int count(AuctionQuery auctionQuery);

	List<AuctionDO> list(List<Long> itemIds);

	List<AuctionDO> list(AuctionQuery auctionQuery);

	List<AuctionStateDO> groupCraftsmanAuctionBystate(List<Long> userIds, Long itemId);

	Long countCraftsmanAuctionByState();
}
