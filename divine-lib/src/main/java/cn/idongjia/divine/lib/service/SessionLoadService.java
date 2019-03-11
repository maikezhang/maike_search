package cn.idongjia.divine.lib.service;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * @author lc on 2018/7/25.
 * @class cn.idongjia.divine.lib.GeneralLiveService
 */
@Path("sessionLoad")
@Consumes(ContentType.APPLICATION_JSON_UTF_8)
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public interface SessionLoadService {

    /**
     * 全量更新缓存
     * 通用
     *
     * @return
     */
    @GET
    @Path("load")
    Integer load();

    /**
     * 按id更新缓存
     * 通用
     *
     * @return
     */
    @GET
    @Path("loadById/{sessionId:\\d+}")
    boolean loadById(@PathParam("sessionId") Long sessionId);

}
