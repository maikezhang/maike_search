package cn.idongjia.divine.aggregation;

import cn.idongjia.divine.aggregation.factory.AbstractAggregationFactory;
import cn.idongjia.divine.aggregation.factory.AuctionPriceAggregationFactory;
import cn.idongjia.divine.db.es.entity.AuctionPriceDTO;
import cn.idongjia.divine.lib.pojo.request.Page;
import cn.idongjia.divine.lib.pojo.request.auction.AuctionPriceGroupQry;
import cn.idongjia.divine.lib.pojo.request.auction.AuctionQry;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.se.lib.engine.query.LeafQuery;
import cn.idongjia.se.lib.engine.query.QueryArgs;
import org.elasticsearch.search.sort.SortOrder;

import java.util.List;

/**
 * @author lc
 * @create at 2018/9/6.
 */
public class AggregationFactory {
    public static <Q extends Page> AbstractAggregationFactory get(AggregationEnums.AggregationType aggregationType,QueryArgs queryArgs,LeafQuery query,Q q) {
        AbstractAggregationFactory abstractAggregationFactory = null;
        switch(aggregationType) {
            case AUCTION_PRICE:
                AuctionQry auctionQry = (AuctionQry)q;
                AggregateProperty aggregateField = new AggregateProperty();
                AuctionPriceGroupQry auctionPriceGroupQry = auctionQry.getAuctionPriceGroupQry();
                aggregateField.setAggField(auctionPriceGroupQry.getFirstAgg());
                aggregateField.setSortField(auctionPriceGroupQry.getSecondAgg());
                aggregateField.setSortOrder(SortOrder.DESC);
                aggregateField.setLimit(auctionPriceGroupQry.getSubLimit());
                List<Long> categoryIds = auctionQry.getCraftsmanIds();
                int size=10;
                if(Utils.isNotEmpty(categoryIds)){
                    size=categoryIds.size();
                }
                abstractAggregationFactory = new AuctionPriceAggregationFactory(queryArgs,query,aggregateField,size);
                break;
            default: break;
        }
        return abstractAggregationFactory;
    }
}
