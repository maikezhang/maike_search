package cn.idongjia.divine.query.factory.auction;

import cn.idongjia.desert.dto.WordsDTO;
import cn.idongjia.desert.query.SegQuery;
import cn.idongjia.divine.lib.pojo.request.auction.AuctionQry;
import cn.idongjia.divine.lib.pojo.request.auction.AuctionStateQry;
import cn.idongjia.divine.lib.pojo.request.sort.AuctionSort;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.query.AuctionESQuery;
import cn.idongjia.divine.query.AuctionStateESQuery;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.dw.lib.pojo.community.UserFavor;
import cn.idongjia.se.lib.engine.query.sort.Sort;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/8/21.
 */
public class AuctionStateQueryFactory extends AuctionStateBaseQueryFactrory {

    public AuctionStateQueryFactory(AuctionStateQry auctionStateQry) {
        super(auctionStateQry);
    }

    @Override
    public AuctionStateESQuery getQuery() {
        AuctionStateESQuery auctionESQuery = new AuctionStateESQuery();
        auctionESQuery.setCraftsmanIds(auctionStateQry.getCraftsmanIds());
        auctionESQuery.setStart(auctionStateQry.getOffset());
        auctionESQuery.setNum(auctionStateQry.getLimit());
        return auctionESQuery;
    }

    @Override
    public ESSort getSort() {
        ESSort     esSort = new AuctionSort();
        List<Sort> sorts  = auctionStateQry.getSorts();
        if(Utils.isNotEmpty(sorts)) {
            esSort.addSorts(sorts);
        }
        return esSort;
    }
}
