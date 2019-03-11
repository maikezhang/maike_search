package cn.idongjia.divine.query.factory.auction;

import cn.idongjia.divine.lib.pojo.request.auction.AuctionQry;
import cn.idongjia.divine.lib.pojo.request.sort.AuctionSort;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.query.AuctionESQuery;
import cn.idongjia.dw.lib.pojo.community.UserFavor;
import cn.idongjia.se.lib.engine.query.Direction;
import cn.idongjia.se.lib.engine.query.sort.ScriptSort;

import java.util.Arrays;

/**
 * @author lc
 * @create at 2018/8/21.
 */
public class AuctionGroupQueryFactory extends AuctionBaseQueryFactrory {

    public AuctionGroupQueryFactory(AuctionQry auctionQry,UserFavor userFavor) {
        super(auctionQry,userFavor);
    }

    @Override
    public AuctionESQuery getQuery() {
        AuctionESQuery auctionESQuery = new AuctionESQuery();
        setCommonQuery(auctionESQuery, auctionQry);
        auctionESQuery.setCraftsmanIds(auctionQry.getCraftsmanIds());
        auctionESQuery.setSessionStatus(auctionQry.getSessionStatus());
        auctionESQuery.setAuctionState(auctionQry.getAuctionStates());
        auctionESQuery.setAuctionStatus(auctionQry.getAuctionStatus());
        auctionESQuery.setSessionStates(auctionQry.getSessionStates());
        auctionESQuery.setSessionIds(auctionQry.getSessionIds());
        auctionESQuery.setSessionPreviewStatus(auctionQry.getSessionPreview());
        auctionESQuery.setSessionTypes(auctionQry.getSessionTypes());
        auctionESQuery.setItemExtStatus(auctionQry.getItemExtStatus());
        auctionESQuery.setNum(auctionQry.getLimit());
		auctionESQuery.setAuctionStatus(Arrays.asList(0, 1, 2, 3, 4, 5, 6));
        return auctionESQuery;
    }

    @Override
    public ESSort getSort() {
        ESSort     esSort     = new AuctionSort();
        ScriptSort scriptSort = new ScriptSort();
		scriptSort.setScript(scriptSortManager.getAuctionGroupSort());
        scriptSort.setDirection(Direction.DESC);
        esSort.addSort(scriptSort);
        return esSort;
    }
}
