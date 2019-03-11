package cn.idongjia.divine.impl;

import cn.idongjia.divine.annotation.Elapsed;
import cn.idongjia.divine.biz.AuctionQueryBO;
import cn.idongjia.divine.lib.pojo.request.auction.AuctionQry;
import cn.idongjia.divine.lib.pojo.request.sort.SortType;
import cn.idongjia.divine.lib.pojo.response.MultiResponse;
import cn.idongjia.divine.lib.pojo.response.auction.AuctionCO;
import cn.idongjia.divine.lib.pojo.response.auction.AuctionPriceCO;
import cn.idongjia.divine.lib.pojo.response.auction.SessionAuctionCO;
import cn.idongjia.divine.lib.pojo.response.auction.SessionAuctionRel;
import cn.idongjia.divine.lib.service.AuctionService;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.divine.utils.exception.DivineException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lc
 * @create at 2018/8/18.
 */
@Service("auctionService")
public class AuctionServiceImpl implements AuctionService {

    @Resource
    private AuctionQueryBO auctionQueryBO;

    /**
     * 直播列表查询
     * 通用
     *
     * @param auctionQry
     * @return
     */
    @Override
    @Elapsed
    public MultiResponse<AuctionCO> search(AuctionQry auctionQry) {
        if(Utils.isNotEmpty(auctionQry.getItemTitle()) && auctionQry.getItemTitle().length() > 50) {
            throw DivineException.failure("查询长度不能超过50个字符");
        }
        return auctionQueryBO.query(auctionQry);

    }

    /**
     * 直播列表查询
     * 通用
     *
     * @param auctionQry
     * @return
     */
    @Override
    public MultiResponse<AuctionPriceCO> priceAggSearch(AuctionQry auctionQry) {
        return auctionQueryBO.priceAggSearch(auctionQry);
    }

    /**
     * tab查询
     *
     * @param auctionQry
     * @return
     */
    @Override
    @Elapsed
    public MultiResponse<AuctionCO> tab(AuctionQry auctionQry) {
        auctionQry.setSortType(SortType.TabSortType.TAB_SORT);
        return auctionQueryBO.query(auctionQry);
    }

    /**
     * 推荐查询
     *
     * @param auctionQry
     * @return
     */
    @Override
    public MultiResponse<AuctionCO> recommend(AuctionQry auctionQry) {
        auctionQry.setSortType(SortType.TabSortType.RECOMMEND);
        return auctionQueryBO.query(auctionQry);
    }

    /**
     * 按id获取数据
     *
     * @param auctionQry
     * @return
     */
    @Override
    public MultiResponse<AuctionCO> list(AuctionQry auctionQry) {

        auctionQry.setSortType(SortType.TabSortType.SELF);
        return auctionQueryBO.query(auctionQry);
    }

    /**
     * 获取专场关联的拍品 不少于3个 多余4个的只返回4个
     *
     * @param sessionIds
     * @return
     */
    @Override
    @Elapsed
    public MultiResponse<SessionAuctionRel> groupBySession(List<Long> sessionIds) {
        if(Utils.isEmpty(sessionIds)) {
            throw DivineException.failure("专场id不能为空");
        }
        AuctionQry auctionQry = new AuctionQry();
        auctionQry.setSessionIds(sessionIds);
        auctionQry.setLimit(sessionIds.size() * 100);
        auctionQry.setMinItemTotal(3);
        auctionQry.setMaxItemTotal(4);
        auctionQry.setSortType(SortType.TabSortType.AUCTION_GROUP);
        return auctionQueryBO.auctionGroup(auctionQry);
    }

    /**
     * 获取专场关联的拍品
     *
     * @param auctionQry
     * @return
     */
    @Override
    @Elapsed
    public MultiResponse<SessionAuctionRel> groupBySessionWithLimit(AuctionQry auctionQry) {
        List<Long> sessionIds = auctionQry.getSessionIds();
        if(Utils.isEmpty(sessionIds)) {
            throw DivineException.failure("专场id不能为空");
        }
        auctionQry.setLimit(sessionIds.size() * 100);
        auctionQry.setSortType(SortType.TabSortType.AUCTION_GROUP);
        return auctionQueryBO.auctionGroup(auctionQry);
    }

    @Override
    public MultiResponse<SessionAuctionCO> groupBySession(AuctionQry auctionQry) {
        List<Long> sessionIds = auctionQry.getSessionIds();
        if(Utils.isEmpty(sessionIds)) {
            throw DivineException.failure("专场id不能为空");
        }
        auctionQry.setLimit(sessionIds.size() * 100);
        auctionQry.setSortType(SortType.TabSortType.AUCTION_GROUP);
        return auctionQueryBO.groupBySession(auctionQry);
    }
}
