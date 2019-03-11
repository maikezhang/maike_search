package cn.idongjia.divine.query.factory.auction;

import cn.idongjia.divine.lib.pojo.request.auction.AuctionQry;
import cn.idongjia.divine.lib.pojo.request.sort.AuctionSort;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.query.AuctionESQuery;
import cn.idongjia.dw.lib.pojo.community.UserFavor;
import cn.idongjia.se.lib.engine.query.Direction;
import cn.idongjia.se.lib.engine.query.sort.ScriptSort;

import java.util.List;

/**
 * @author lc
 * @create at 2018/8/21.
 */
public class AuctionTabQueryFactory extends AuctionBaseQueryFactrory {

    public AuctionTabQueryFactory(AuctionQry auctionQry,UserFavor userFavor) {
        super(auctionQry,userFavor);
    }

    @Override
    public AuctionESQuery getQuery() {
        AuctionESQuery auctionESQuery = new AuctionESQuery();
        setCommonQuery(auctionESQuery, auctionQry);
        auctionESQuery.setCategoryIds(auctionQry.getCategoryIds());
        auctionESQuery.setNoCategoryIds(auctionQry.getExcludeCategoryIds());
        auctionESQuery.setStart(auctionQry.getOffset());
        auctionESQuery.setNum(auctionQry.getLimit());
        auctionESQuery.setItemExtStatus(auctionQry.getItemExtStatus());
        auctionESQuery.setItemStatus(auctionQry.getItemStatus());
        Integer sessionPreview = auctionQry.getSessionPreview();
        auctionESQuery.setSessionPreviewStatus(sessionPreview);
        List<Integer> sessionStates = auctionQry.getSessionStates();
        auctionESQuery.setSessionStates(sessionStates);
        List<Integer> sessionStatus = auctionQry.getSessionStatus();
        auctionESQuery.setSessionStatus(sessionStatus);
        auctionESQuery.setSessionTypes(auctionQry.getSessionTypes());
        auctionESQuery.setCraftsmanIds(auctionQry.getCraftsmanIds());
        auctionESQuery.setSessionIds(auctionQry.getSessionIds());
        List<Long> groundIds = auctionQry.getGroundIds();
        auctionESQuery.setGroundIds(groundIds);
        List<Long> excludeGroundIds = auctionQry.getExcludeGroundIds();
        auctionESQuery.setNoGroundIds(excludeGroundIds);
        auctionESQuery.setAuctionStatus(auctionQry.getAuctionStatus());
        auctionESQuery.setAuctionTypes(auctionQry.getAuctionTypes());
        auctionESQuery.setAuctionState(auctionQry.getAuctionStates());
        return auctionESQuery;
    }

    @Override
    public ESSort getSort() {
        ESSort     esSort     = new AuctionSort();
        ScriptSort scriptSort = new ScriptSort();
        scriptSort.setScript(scriptSortManager.getAuctionTabSort());
        scriptSort.setDirection(Direction.DESC);
        esSort.addSort(scriptSort);

        return esSort;
    }
}
