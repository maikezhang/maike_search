package cn.idongjia.divine.lib.service;

import cn.idongjia.divine.lib.pojo.request.auction.AuctionStateQry;
import cn.idongjia.divine.lib.pojo.request.session.SessionQry;
import cn.idongjia.divine.lib.pojo.response.MultiResponse;
import cn.idongjia.divine.lib.pojo.response.auction.AuctionCO;
import cn.idongjia.divine.lib.pojo.response.session.AuctionStateCO;
import cn.idongjia.divine.lib.pojo.response.session.SessionCO;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author lc on 2018/7/25.
 * @class cn.idongjia.divine.lib.GeneralLiveService
 */
@Path("auctionState")
@Consumes(ContentType.APPLICATION_JSON_UTF_8)
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public interface AuctionStateService {

    @POST
    @Path("search")
    @Consumes({ MediaType.APPLICATION_JSON })
    MultiResponse<AuctionStateCO> search(AuctionStateQry auctionStateQry);
}
