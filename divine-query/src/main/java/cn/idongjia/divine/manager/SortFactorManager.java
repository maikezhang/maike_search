package cn.idongjia.divine.manager;

import cn.idongjia.dw.lib.pojo.community.UserFavor;
import cn.idongjia.dw.lib.search.community.AuctionSearchSearch;
import cn.idongjia.dw.lib.service.community.AuctionSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/18.
 */
@Component
@Slf4j
public class SortFactorManager {
    @Resource
    protected AuctionSearchService recommendAuctionSearchService;
    public static int LIVE_USER_FAVOR    = 0;
    public static int AUCTION_USER_FAVOR = 1;

    public UserFavor getUserFavor(Long uid,String deviceId,int dataType) {
        UserFavor userFavor = null;
        try {
            AuctionSearchSearch auctionSearchSearch = new AuctionSearchSearch();
            auctionSearchSearch.setDeviceID(deviceId);
            auctionSearchSearch.setUid(uid);
            auctionSearchSearch.setAuctionOrLive(dataType);
            userFavor = recommendAuctionSearchService.getUserFavor(auctionSearchSearch);
        } catch(Exception e) {
            log.error("调用算法组接口获取用户偏好数据异常，error:{}",e.getMessage(),e);
        }

        return userFavor;
    }

}
