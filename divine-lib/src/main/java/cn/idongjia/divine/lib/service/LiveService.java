package cn.idongjia.divine.lib.service;

import cn.idongjia.divine.lib.pojo.request.live.LiveQry;
import cn.idongjia.divine.lib.pojo.response.MultiResponse;
import cn.idongjia.divine.lib.pojo.response.live.general.GeneralLiveCO;
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
@Path("live")
@Consumes(ContentType.APPLICATION_JSON_UTF_8)
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public interface LiveService {
    /**
     * 直播列表查询
     * 通用
     *
     * @param liveQry
     * @return
     */
    @POST
    @Path("search")
    @Consumes({ MediaType.APPLICATION_JSON })
    MultiResponse<GeneralLiveCO> search(LiveQry liveQry);

    /**
     * tab查询
     *
     * @param liveQry
     * @return
     */
    @POST
    @Path("tab")
    @Consumes({ MediaType.APPLICATION_JSON })
    MultiResponse<GeneralLiveCO> tab(LiveQry liveQry);

    /**
     * 推荐查询
     *
     * @param liveQry
     * @return
     */
    @POST
    @Path("recommend")
    @Consumes({ MediaType.APPLICATION_JSON })
    MultiResponse<GeneralLiveCO> recommend(LiveQry liveQry);

    /**
     * 按id获取数据
     *
     * @param ids
     * @return
     */
    @POST
    @Path("list")
    @Consumes({ MediaType.APPLICATION_JSON })
    MultiResponse<GeneralLiveCO> list(List<Long> ids);

}
