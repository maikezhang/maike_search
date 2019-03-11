package cn.idongjia.divine.lib.service;

import cn.idongjia.divine.lib.pojo.request.live.CraftsmanQry;
import cn.idongjia.divine.lib.pojo.response.MultiResponse;
import cn.idongjia.divine.lib.pojo.response.live.special.LiveSpecialCraftsmanCO;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author lc on 2018/9/3.
 * @class cn.idongjia.divine.lib.service.LiveSpecialCraftsmanService
 */
@Path("liveSpecialCraftsman")
@Consumes(ContentType.APPLICATION_JSON_UTF_8)
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public interface LiveSpecialCraftsmanService {
    @GET
    @Path("list")
    MultiResponse<LiveSpecialCraftsmanCO> list(CraftsmanQry liveSpecialQry);
}
