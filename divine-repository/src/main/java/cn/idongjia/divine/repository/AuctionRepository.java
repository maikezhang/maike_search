package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.mapper.kaipao.AuctionMapper;
import cn.idongjia.divine.db.mybatis.pojo.AuctionDO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionStateDO;
import cn.idongjia.divine.db.mybatis.query.AuctionQuery;
import cn.idongjia.divine.utils.Utils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lc
 * @create at 2018/8/16.
 */
@Repository
public class AuctionRepository implements AuctionRepositoryI {
    @Resource
    private AuctionMapper auctionMapper;
    @Override
    public AuctionDO getByItemId(Long auctionId) {
        return auctionMapper.getByItemId(auctionId);
    }

    @Override
    public int count(AuctionQuery auctionQuery) {
        return auctionMapper.count(auctionQuery);
    }

	@Override
	public List<AuctionDO> list(List<Long> itemIds) {
		if (Utils.isEmpty(itemIds)) {
			return new ArrayList<>();
		}
		AuctionQuery auctionQuery = AuctionQuery.builder().statusArray(Arrays.asList(0, 1, 2, 3, 4, 5, 6)).itemIds(itemIds).build();
		return auctionMapper.select(auctionQuery);
	}

	@Override
	public List<AuctionDO> list(AuctionQuery auctionQuery) {
		return auctionMapper.select(auctionQuery);
	}

	@Override
	public List<AuctionStateDO> groupCraftsmanAuctionBystate(List<Long> userIds, Long itemId) {
		return auctionMapper.groupCraftsmanAuctionBystate(userIds,null,null, itemId);
	}

	@Override
	public Long countCraftsmanAuctionByState() {
		return auctionMapper.countCraftsmanAuctionByState();
	}

}
