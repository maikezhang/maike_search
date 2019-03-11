package cn.idongjia.divine.query.factory;

import cn.idongjia.divine.factor.UserFavorFactor;
import cn.idongjia.divine.lib.pojo.request.Page;
import cn.idongjia.divine.lib.pojo.request.UserInfo;
import cn.idongjia.divine.lib.pojo.request.auction.AuctionQry;
import cn.idongjia.divine.lib.pojo.request.auction.AuctionStateQry;
import cn.idongjia.divine.lib.pojo.request.live.CraftsmanQry;
import cn.idongjia.divine.lib.pojo.request.live.LiveQry;
import cn.idongjia.divine.lib.pojo.request.session.SessionLiveQry;
import cn.idongjia.divine.lib.pojo.request.session.SessionQry;
import cn.idongjia.divine.lib.pojo.request.sort.BizType;
import cn.idongjia.divine.lib.pojo.request.sort.SortType;
import cn.idongjia.divine.lib.pojo.request.zoo.ZooMessageQry;
import cn.idongjia.divine.query.AuctionESQuery;
import cn.idongjia.divine.query.AuctionStateESQuery;
import cn.idongjia.divine.query.CraftsmanESQuery;
import cn.idongjia.divine.query.LiveESQuery;
import cn.idongjia.divine.query.SessionESQuery;
import cn.idongjia.divine.query.SessionLiveESQuery;
import cn.idongjia.divine.query.ZooMessageESQuery;
import cn.idongjia.divine.query.factory.auction.AuctionGroupQueryFactory;
import cn.idongjia.divine.query.factory.auction.AuctionRecommendQueryFactory;
import cn.idongjia.divine.query.factory.auction.AuctionSearchQueryFactory;
import cn.idongjia.divine.query.factory.auction.AuctionSelfQueryFactory;
import cn.idongjia.divine.query.factory.auction.AuctionStateQueryFactory;
import cn.idongjia.divine.query.factory.auction.AuctionTabQueryFactory;
import cn.idongjia.divine.query.factory.craftsman.LiveSpecialCraftsmanQueryFactory;
import cn.idongjia.divine.query.factory.live.HomeLiveSortQueryFactory;
import cn.idongjia.divine.query.factory.live.LiveListQueryFactory;
import cn.idongjia.divine.query.factory.live.LiveRecommendQueryFactory;
import cn.idongjia.divine.query.factory.live.LiveSearchQueryFactory;
import cn.idongjia.divine.query.factory.live.LiveSelfQueryFactory;
import cn.idongjia.divine.query.factory.live.LiveTabQueryFactory;
import cn.idongjia.divine.query.factory.session.SessionLiveQueryFactory;
import cn.idongjia.divine.query.factory.session.SessionQueryFactory;
import cn.idongjia.divine.query.factory.session.SessionStateQueryFactory;
import cn.idongjia.divine.query.factory.zoo.ZooMessageQueryFactory;
import cn.idongjia.divine.utils.SpringBeanLoader;
import cn.idongjia.dw.lib.pojo.community.UserFavor;
import cn.idongjia.se.lib.engine.query.LeafQuery;

import static cn.idongjia.divine.manager.SortFactorManager.AUCTION_USER_FAVOR;
import static cn.idongjia.divine.manager.SortFactorManager.LIVE_USER_FAVOR;

/**
 * @author lc
 * @create at 2018/8/21.
 */
public class QueryFactory {

    public static <P extends Page,Q extends LeafQuery> AbstractQueryFactory<Q> getFactory(BizType.ModuleType moduleType,P p) {
        AbstractQueryFactory abstractQueryFactory = null;
        switch(moduleType) {
            case LIVE:
                LiveQry liveQry = (LiveQry)p;
                abstractQueryFactory = buildLive(liveQry);
                break;
            case AUCTION:
                AuctionQry auctionQry = (AuctionQry)p;
                abstractQueryFactory = buildAuction(auctionQry);
                break;
            case AUCTION_STATE:
                AuctionStateQry auctionStateQry = (AuctionStateQry)p;
                abstractQueryFactory = buildAuctionState(auctionStateQry);
                break;
            case CRAFTSMAN:
                CraftsmanQry craftsmanQry = (CraftsmanQry)p;
                abstractQueryFactory = buildCraftsman(craftsmanQry);
                break;
            case SESSION:
                SessionQry sessionQry = (SessionQry)p;
                abstractQueryFactory = buildSession(sessionQry);
                break;
            case SESSION_LIVE:
                SessionLiveQry sessionLiveQry = (SessionLiveQry) p;
                abstractQueryFactory = buildSessionLive(sessionLiveQry);
                break;
            case ZOO_MESSAGE:
                ZooMessageQry zooMessageQry=(ZooMessageQry)p;
                abstractQueryFactory=buildZooMessage(zooMessageQry);
                break;
            default:
                break;
        }

        return abstractQueryFactory;
    }

    private static AbstractQueryFactory<LiveESQuery> buildLive(LiveQry liveQry) {
        UserFavorFactor                   userFavorFactor      = SpringBeanLoader.getBean("userFavorFactor",UserFavorFactor.class);
        SortType.TabSortType              sortType             = liveQry.getSortType();
        AbstractQueryFactory<LiveESQuery> abstractQueryFactory = null;
        switch(sortType) {
            case SELF:
                abstractQueryFactory = new LiveSelfQueryFactory(liveQry,null);
                break;
            case SEARCH:
                abstractQueryFactory = new LiveSearchQueryFactory(liveQry,null);
                break;
            case RECOMMEND:
                UserInfo userInfo = liveQry.getUserInfo();
                UserFavor userFavor = userFavorFactor.getUserFavor(userInfo.getUserId(),userInfo.getDeviceId(),LIVE_USER_FAVOR);
                abstractQueryFactory = new LiveRecommendQueryFactory(liveQry,userFavor);
                break;
            case LIVE_DEFAULT:
                abstractQueryFactory = new LiveListQueryFactory(liveQry,null);
                break;
            case TAB_SORT:
                abstractQueryFactory = new LiveTabQueryFactory(liveQry,null);
                break;
            case HOME_PAGE_LIVE:
                abstractQueryFactory = new HomeLiveSortQueryFactory(liveQry,null);
                break;
            default:
        }
        return abstractQueryFactory;
    }

    private static AbstractQueryFactory<AuctionESQuery> buildAuction(AuctionQry auctionQry) {
        UserFavorFactor userFavorFactor = SpringBeanLoader.getBean("userFavorFactor",UserFavorFactor.class);

        SortType.TabSortType                 sortType             = auctionQry.getSortType();
        AbstractQueryFactory<AuctionESQuery> abstractQueryFactory = null;
        switch(sortType) {
            case AUCTION_GROUP:
                abstractQueryFactory = new AuctionGroupQueryFactory(auctionQry,null);
                break;
            case SELF:
                abstractQueryFactory = new AuctionSelfQueryFactory(auctionQry,null);
                break;
            case SEARCH:
                abstractQueryFactory = new AuctionSearchQueryFactory(auctionQry,null);
                break;
            case RECOMMEND:
                UserInfo userInfo = auctionQry.getUserInfo();
                UserFavor userFavor = userFavorFactor.getUserFavor(userInfo.getUserId(),userInfo.getDeviceId(),AUCTION_USER_FAVOR);
                abstractQueryFactory = new AuctionRecommendQueryFactory(auctionQry,userFavor);
                break;
            case TAB_SORT:
                abstractQueryFactory = new AuctionTabQueryFactory(auctionQry,null);
                break;
            default:
        }
        return abstractQueryFactory;
    }

    private static AbstractQueryFactory<CraftsmanESQuery> buildCraftsman(CraftsmanQry craftsmanQry) {
        return new LiveSpecialCraftsmanQueryFactory(craftsmanQry);
    }

    private static AbstractQueryFactory<SessionESQuery> buildSession(SessionQry sessionQry) {
        SortType.TabSortType                 sortType             = sessionQry.getSortType();
        AbstractQueryFactory<SessionESQuery> abstractQueryFactory = null;

        switch(sortType) {
            case SESSION_DEFAULT:
                abstractQueryFactory = new SessionQueryFactory(sessionQry);
                break;
            case SESSION_STATE:
                abstractQueryFactory = new SessionStateQueryFactory(sessionQry);
                break;
            default: break;
        }
        return abstractQueryFactory;
    }

    private static AbstractQueryFactory<AuctionStateESQuery> buildAuctionState(AuctionStateQry auctionStateQry) {
        return new AuctionStateQueryFactory(auctionStateQry);
    }

    private static AbstractQueryFactory<SessionLiveESQuery> buildSessionLive(SessionLiveQry sessionLiveQry) {
        return new SessionLiveQueryFactory(sessionLiveQry);
    }

    private static AbstractQueryFactory<ZooMessageESQuery> buildZooMessage(ZooMessageQry zooMessageQry){

        return new ZooMessageQueryFactory(zooMessageQry);
    }
}
