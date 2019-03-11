package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.mapper.kaipao.AuctionManualRecommendMapper;
import cn.idongjia.divine.db.mybatis.pojo.AuctionManualRecommendDO;
import cn.idongjia.divine.db.mybatis.query.AuctionManualRecommendQuery;
import cn.idongjia.divine.utils.Utils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/8/16.
 */
@Repository
public class AuctionManaualRecommendRepository implements AuctionManaualRecommendRepositoryI {
    @Resource
    private AuctionManualRecommendMapper auctionManualRecommendMapper;

    /**
     * 根据商品id获取推荐数据
     *
     * @param itemIds
     * @return
     */
    @Override
    public Map<Long,List<AuctionManualRecommendDO>> mapByItemId(List<Long> itemIds) {
        if(Utils.isEmpty(itemIds)) {
            return new HashMap<>();
        }
        List<AuctionManualRecommendDO>     auctionManualRecommendDOS   = auctionManualRecommendMapper.select(AuctionManualRecommendQuery.builder().isDelete(0).itemIds(itemIds).build());
        Map<Long,List<AuctionManualRecommendDO>> auctionManualRecommendDOMap = new HashMap<>();
        if(Utils.isNotEmpty(auctionManualRecommendDOS)) {
            auctionManualRecommendDOMap = auctionManualRecommendDOS.stream().collect(Collectors.groupingBy(auctionManualRecommendDO -> auctionManualRecommendDO.getItemId()));
        }
        return auctionManualRecommendDOMap;
    }
}
