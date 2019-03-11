package cn.idongjia.divine.lib.service;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * @author lc
 * @create at 2018/9/3.
 */
@Path("liveSpecialCraftsmanLoad")
@Consumes(ContentType.APPLICATION_JSON_UTF_8)
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public interface LiveSpecialCraftsmanLoadService {
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
    @Path("loadByCraftsmanId/{craftsmanId:\\d+}")
    boolean loadByCraftsmanId(@PathParam("craftsmanId") Long craftsmanId);
}
