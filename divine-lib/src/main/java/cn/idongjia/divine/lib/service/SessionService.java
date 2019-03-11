package cn.idongjia.divine.lib.service;

import cn.idongjia.divine.lib.pojo.request.live.LiveQry;
import cn.idongjia.divine.lib.pojo.request.session.SessionQry;
import cn.idongjia.divine.lib.pojo.response.MultiResponse;
import cn.idongjia.divine.lib.pojo.response.live.general.GeneralLiveCO;
import cn.idongjia.divine.lib.pojo.response.session.SessionCO;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author lc on 2018/7/25.
 * @class cn.idongjia.divine.lib.GeneralLiveService
 */
@Path("session")
@Consumes(ContentType.APPLICATION_JSON_UTF_8)
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public interface SessionService {
    /**
     * 直播列表查询
     * 通用
     *
     * @param sessionQry
     * @return
     */
    @POST
    @Path("search")
    @Consumes({ MediaType.APPLICATION_JSON })
    MultiResponse<SessionCO> search(SessionQry sessionQry);
}
