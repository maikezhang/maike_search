package cn.idongjia.divine.lib.service;

import cn.idongjia.divine.lib.pojo.request.auction.AuctionQry;
import cn.idongjia.divine.lib.pojo.response.MultiResponse;
import cn.idongjia.divine.lib.pojo.response.auction.AuctionCO;
import cn.idongjia.divine.lib.pojo.response.auction.AuctionPriceCO;
import cn.idongjia.divine.lib.pojo.response.auction.SessionAuctionCO;
import cn.idongjia.divine.lib.pojo.response.auction.SessionAuctionRel;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author lc on 2018/7/25.
 * @class cn.idongjia.divine.lib.GeneralLiveService
 */
@Path("auction")
@Consumes(ContentType.APPLICATION_JSON_UTF_8)
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public interface AuctionService {

    /**
     * 直播列表查询
     * 通用
     *
     * @param auctionQry
     * @return
     */
    @POST
    @Path("search")
    @Consumes({ MediaType.APPLICATION_JSON })
    MultiResponse<AuctionCO> search(AuctionQry auctionQry);

    /**
     * 直播列表查询
     * 通用
     *
     * @param auctionQry
     * @return
     */
    @POST
    @Path("aggSearch")
    @Consumes({ MediaType.APPLICATION_JSON })
    MultiResponse<AuctionPriceCO> priceAggSearch(AuctionQry auctionQry);

    /**
     * tab查询
     *
     * @param auctionQry
     * @return
     */
    @POST
    @Path("tab")
    @Consumes({ MediaType.APPLICATION_JSON })
    MultiResponse<AuctionCO> tab(AuctionQry auctionQry);

    /**
     * 推荐查询
     *
     * @return
     */
    @POST
    @Path("recommend")
    @Consumes({ MediaType.APPLICATION_JSON })
    MultiResponse<AuctionCO> recommend(AuctionQry auctionQry);

    /**
     * 按id获取数据
     *
     * @param auctionQry
     * @return
     */
    @POST
    @Path("list")
    @Consumes({ MediaType.APPLICATION_JSON })
    MultiResponse<AuctionCO> list(AuctionQry auctionQry);

    @POST
    @Path("groupBySession")
    @Consumes({ MediaType.APPLICATION_JSON })
    MultiResponse<SessionAuctionRel> groupBySession(@QueryParam("sessionIds") List<Long> sessionIds);


    @POST
    @Path("groupBySessionWithLimit")
    @Consumes({ MediaType.APPLICATION_JSON })
    MultiResponse<SessionAuctionRel> groupBySessionWithLimit(AuctionQry auctionQry);


    @POST
    @Path("groupBySession")
    @Consumes({ MediaType.APPLICATION_JSON })
    MultiResponse<SessionAuctionCO> groupBySession(AuctionQry auctionQry);

}
