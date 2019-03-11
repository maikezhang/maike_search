package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.pojo.AuctionManualRecommendDO;

import java.util.List;
import java.util.Map;

/**
 * @author lc on 2018/8/16.
 * @class cn.idongjia.divine.repository.AuctionManaualRecommendRepositoryI
 */
public interface AuctionManaualRecommendRepositoryI {
    /**
     * 根据商品id获取推荐数据
     *
     * @param itemIds
     * @return
     */
    Map<Long,List<AuctionManualRecommendDO>> mapByItemId(List<Long> itemIds);
}
