package cn.idongjia.divine.lib.service;

import cn.idongjia.divine.lib.pojo.request.live.LiveQry;
import cn.idongjia.divine.lib.pojo.response.MultiResponse;
import cn.idongjia.divine.lib.pojo.response.live.general.GeneralLiveCO;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * @author lc on 2018/7/25.
 * @class cn.idongjia.divine.lib.GeneralLiveService
 */
@Path("liveLoad")
@Consumes(ContentType.APPLICATION_JSON_UTF_8)
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public interface LiveLoadService {

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
    @Path("loadById/{liveId:\\d+}")
    boolean loadById(@PathParam("liveId") Long liveId);

}
