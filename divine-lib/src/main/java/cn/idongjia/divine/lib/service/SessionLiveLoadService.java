package cn.idongjia.divine.lib.service;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * @author yuexiaodong@idongjia.cn
 * @date 2018-11-30
 */
@Path("sessionLiveLoad")
@Consumes(ContentType.APPLICATION_JSON_UTF_8)
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public interface SessionLiveLoadService {

    /**
     * 全量更新
     *
     * @return
     */
    @GET
    @Path("load")
    Integer load();

    /**
     * 根据专场id更新
     *
     * @param sessionId
     * @return
     */
    @GET
    @Path("loadById/{sessionId:\\d+}")
    boolean loadById(@PathParam("sessionId") Long sessionId);

}
