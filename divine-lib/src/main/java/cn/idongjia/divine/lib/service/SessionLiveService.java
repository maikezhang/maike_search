package cn.idongjia.divine.lib.service;

import cn.idongjia.divine.lib.pojo.request.session.SessionLiveQry;
import cn.idongjia.divine.lib.pojo.response.MultiResponse;
import cn.idongjia.divine.lib.pojo.response.session.SessionLiveCO;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author yuexiaodong@idongjia.cn
 * @date 2018-11-30
 */
@Path("sessionLive")
@Consumes(ContentType.APPLICATION_JSON_UTF_8)
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public interface SessionLiveService {

    @Path("search")
    MultiResponse<SessionLiveCO> search(SessionLiveQry qry);

}
