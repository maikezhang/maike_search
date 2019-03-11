package cn.idongjia.divine.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import cn.idongjia.divine.db.mybatis.pojo.CountPO;
import org.apache.kafka.common.metrics.stats.Count;
import org.springframework.stereotype.Repository;

import cn.idongjia.divine.db.mybatis.mapper.kaipao.AuctionOfferMapper;
import cn.idongjia.divine.db.mybatis.pojo.AuctionOfferDO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionOfferUserDO;
import cn.idongjia.divine.db.mybatis.query.AuctionOfferQuery;
import cn.idongjia.divine.utils.Utils;

/**
 * @author lc
 * @create at 2018/8/16.
 */
@Repository
public class AuctionOfferRepository implements AuctionOfferRepositoryI {
	@Resource
	private AuctionOfferMapper auctionOfferMapper;

	@Override
	public Map<Long, AuctionOfferUserDO> groupByItemId(List<Long> itemIds) {
		if (Utils.isEmpty(itemIds)) {
			return new HashMap<>();
		}
		Map<Long, AuctionOfferUserDO> auctionOfferDOMap = new HashMap<>();
		AuctionOfferQuery auctionOfferQuery = AuctionOfferQuery.builder().status(0).itemIds(itemIds).build();
		List<AuctionOfferDO> auctionOfferDOS = auctionOfferMapper.select(auctionOfferQuery);
		if (Utils.isNotEmpty(auctionOfferDOS)) {
			Map<Long, List<AuctionOfferDO>> itemOfferMap = auctionOfferDOS.stream().collect(Collectors.groupingBy(AuctionOfferDO::getIid));
			auctionOfferDOMap = itemOfferMap.entrySet().stream().map(entry -> {
				AuctionOfferUserDO auctionOfferUserDO = new AuctionOfferUserDO();
				List<AuctionOfferDO> itemOfferDOS = entry.getValue();
				List<Long> userOffers = new ArrayList();
				AuctionOfferDO maxOffer = null;
				for (AuctionOfferDO auctionOfferDO : itemOfferDOS) {
					if (maxOffer == null || auctionOfferDO.getPrice().compareTo(maxOffer.getPrice()) > 0) {
						maxOffer = auctionOfferDO;
					}
					Long userId = auctionOfferDO.getUid();
					if (!userOffers.contains(userId)) {
						userOffers.add(userId);
					}
				}
				auctionOfferUserDO.setItemId(entry.getKey());
				auctionOfferUserDO.setOfferTimes(userOffers.size());
				auctionOfferUserDO.setPrice(maxOffer.getPrice().longValue());
				auctionOfferUserDO.setUserId(maxOffer.getUid());
				return auctionOfferUserDO;
			}).collect(Collectors.toMap(AuctionOfferUserDO::getItemId, v1 -> v1, (v1, v2) -> v1));
		}
		return auctionOfferDOMap;
	}

	@Override
	public Map<Long,CountPO> groupBySessionIds(List<Long> sessionIds) {
		Map<Long,CountPO> countPOMap=new HashMap<>();
		List<CountPO>     countPOS  = auctionOfferMapper.groupOffer(sessionIds,null,null);
		if(Utils.isNotEmpty(countPOS)){
			countPOMap = countPOS.stream().collect(Collectors.toMap(CountPO::getId,v1 -> v1,(v1,v2) -> v1));
		}
		return countPOMap;
	}
}
