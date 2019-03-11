package cn.idongjia.divine.query.factory.auction;

import cn.idongjia.divine.lib.pojo.request.auction.AuctionQry;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.manager.ScriptSortManager;
import cn.idongjia.divine.manager.SegManager;
import cn.idongjia.divine.query.AuctionESQuery;
import cn.idongjia.divine.query.RangeBuilder;
import cn.idongjia.divine.query.factory.AbstractQueryFactory;
import cn.idongjia.divine.utils.SpringBeanLoader;
import cn.idongjia.dw.lib.pojo.community.UserFavor;
import cn.idongjia.se.lib.engine.query.logical.Range;

/**
 * @author lc
 * @create at 2018/8/21.
 */
public abstract class AuctionBaseQueryFactrory extends AbstractQueryFactory<AuctionESQuery> {
    protected ScriptSortManager scriptSortManager = SpringBeanLoader.getBean("scriptSortManager", ScriptSortManager.class);
    protected SegManager        segManager        = SpringBeanLoader.getBean("segManager", SegManager.class);

    protected UserFavor userFavor;

    protected AuctionQry auctionQry;

    public AuctionBaseQueryFactrory(AuctionQry auctionQry, UserFavor userFavor) {
        this.auctionQry = auctionQry;
        this.userFavor = userFavor;
    }

    /**
     * 设置一下共用的查询条件
     *
     * @param esQuery    es查询条件的封装
     * @param auctionQry 对外查询封装
     */
    public void setCommonQuery(AuctionESQuery esQuery, AuctionQry auctionQry) {
        if (esQuery == null || auctionQry == null) {
            return;
        }
        //是否关联专场
        if (auctionQry.getRelatedSession() != null) {
            if (auctionQry.getRelatedSession()) {
                //包含下界
                final Range<Long> sessionIdRange = RangeBuilder.build(1L);
                esQuery.setSessionIdRange(sessionIdRange);
            } else {
                Range<Long> sessionIdRage = new Range<>();
                sessionIdRage.setUpValue(0L);
                esQuery.setSessionIdRange(sessionIdRage);
            }
        }
        esQuery.setWildCraftsmanName(auctionQry.getWildCraftsmanName());
        esQuery.setPlanEndTimeRange(auctionQry.getPlanEndTimeRange());
        esQuery.setCraftsmanId(auctionQry.getCraftsmanId());
        esQuery.setHotRange(auctionQry.getHotRange());
        //模糊标题查询
        esQuery.setWildItemTitle(auctionQry.getWildItemTitle());
        esQuery.setCreateTimeRange(auctionQry.getCreateTimeRange());
        esQuery.setPriceRange(auctionQry.getPriceRange());
        esQuery.setItemExtStatusList(auctionQry.getItemExtStatusList());
    }

    @Override
    public abstract AuctionESQuery getQuery();

    @Override
    public abstract ESSort getSort();
}
