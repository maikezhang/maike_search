package cn.idongjia.divine.lib.service;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * @author lc on 2018/7/25.
 * @class cn.idongjia.divine.lib.AuctionLoadService
 */
@Path("auctionLoad")
@Consumes(ContentType.APPLICATION_JSON_UTF_8)
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public interface AuctionLoadService {


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
     * @param itemId
     * @return
     */
    @GET
    @Path("loadByItemId/{itemId:\\d+}")
    boolean loadByItemId(@PathParam("itemId") Long itemId);

}
