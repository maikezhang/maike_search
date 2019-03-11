package cn.idongjia.divine.manager;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

/**
 * @author lc
 * @create at 2018/8/10.
 */
@Component
@Getter
@Slf4j
public class ScriptSortManager {

    private String recommendSort        = null;
    private String liveDefaultSort      = null;
    private String liveTabSort          = null;
    private String auctionTabSort       = null;
    private String auctionDefaultSort   = null;
    private String auctionGroupSort     = null;
    private String auctionRecommendSort = null;
    private String auctionCountSort     = null;
    private String sessionStateSort     = null;
    private String sessionListSort1     = null;
    private String sessionListSort2     = null;
    private String sessionListSort3     = null;
    private String sessionLiveSort      = null;
    private String homepageLiveSort     = null;


    ScriptSortManager() {
        URL liveDefaultSortURL    = this.getClass().getClassLoader().getResource("order/live_default.painless");
        URL liveTabSortURL        = this.getClass().getClassLoader().getResource("order/live_tab.painless");
        URL recommendURL          = this.getClass().getClassLoader().getResource("order/live_recommend.painless");
        URL auctionTabSortURL     = this.getClass().getClassLoader().getResource("order/auction_tab.painless");
        URL auctionDefaultSortURL = this.getClass().getClassLoader().getResource("order/auction_default.painless");
        URL auctionRecommendURL   = this.getClass().getClassLoader().getResource("order/auction_recommend.painless");
        URL auctionGroupURL       = this.getClass().getClassLoader().getResource("order/auction_group.painless");
        URL auctionCountURL       = this.getClass().getClassLoader().getResource("order/auction_count.painless");
        URL sessionStateURL       = this.getClass().getClassLoader().getResource("order/session_list.painless");
        URL sessionListUrl1       = this.getClass().getClassLoader().getResource("order/session_list_1_state.painless");
        URL sessionListUrl2       = this.getClass().getClassLoader().getResource("order/session_list_2_weight.painless");
        URL sessionListUrl3       = this.getClass().getClassLoader().getResource("order/session_list_3_time.painless");
        URL sessionLiveUrl        = this.getClass().getClassLoader().getResource("order/session_live.painless");
        URL homePageLive          = this.getClass().getClassLoader().getResource("order/home_page_live.painless");

        try {
            liveDefaultSort = IOUtils.toString(liveDefaultSortURL);
            auctionTabSort = IOUtils.toString(auctionTabSortURL);
            recommendSort = IOUtils.toString(recommendURL);
            liveTabSort = IOUtils.toString(liveTabSortURL);
            auctionDefaultSort = IOUtils.toString(auctionDefaultSortURL);
            auctionGroupSort = IOUtils.toString(auctionGroupURL);
            auctionRecommendSort = IOUtils.toString(auctionRecommendURL);
            auctionCountSort = IOUtils.toString(auctionCountURL);
            sessionStateSort = IOUtils.toString(sessionStateURL);
            sessionListSort1 = IOUtils.toString(sessionListUrl1);
            sessionListSort2 = IOUtils.toString(sessionListUrl2);
            sessionListSort3 = IOUtils.toString(sessionListUrl3);
            sessionLiveSort = IOUtils.toString(sessionLiveUrl);
            homepageLiveSort = IOUtils.toString(homePageLive);
        } catch (IOException e) {
            log.error("读取算失败{}", e);
        }
    }
}
