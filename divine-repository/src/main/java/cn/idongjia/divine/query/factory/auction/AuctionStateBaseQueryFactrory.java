package cn.idongjia.divine.query.factory.auction;

import cn.idongjia.divine.lib.pojo.request.auction.AuctionQry;
import cn.idongjia.divine.lib.pojo.request.auction.AuctionStateQry;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.manager.ScriptSortManager;
import cn.idongjia.divine.manager.SegManager;
import cn.idongjia.divine.query.AuctionESQuery;
import cn.idongjia.divine.query.AuctionStateESQuery;
import cn.idongjia.divine.query.factory.AbstractQueryFactory;
import cn.idongjia.divine.utils.SpringBeanLoader;
import cn.idongjia.dw.lib.pojo.community.UserFavor;

/**
 * @author lc
 * @create at 2018/8/21.
 */
public abstract class AuctionStateBaseQueryFactrory extends AbstractQueryFactory<AuctionStateESQuery> {
    protected ScriptSortManager scriptSortManager = SpringBeanLoader.getBean("scriptSortManager",ScriptSortManager.class);
    protected SegManager        segManager        = SpringBeanLoader.getBean("segManager",SegManager.class);


    protected AuctionStateQry auctionStateQry;

    public AuctionStateBaseQueryFactrory(AuctionStateQry auctionStateQry) {
        this.auctionStateQry = auctionStateQry;
    }

    @Override
    public abstract AuctionStateESQuery getQuery();

    @Override
    public abstract ESSort getSort();
}
