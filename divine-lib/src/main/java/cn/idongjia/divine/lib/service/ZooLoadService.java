package cn.idongjia.divine.lib.service;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * @author zhangyingjie on 2018/11/22.
 * @class cn.idongjia.divine.lib.ZooLoadService
 */
@Path("zooLoad")
@Consumes(ContentType.APPLICATION_JSON_UTF_8)
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public interface ZooLoadService {


    /**
     * 全量更新缓存
     * 通用
     *
     * @return
     */
    @GET
    @Path("loadZooMessage")
    Integer loadZooMessage();

    /**
     * 按id更新缓存
     * @param zooMessageId
     * @return
     */
    @GET
    @Path("loadByZooMessageId/{zooMessageId:\\d+}")
    boolean loadByZooMessageId(@PathParam("zooMessageId") Long zooMessageId);

}
