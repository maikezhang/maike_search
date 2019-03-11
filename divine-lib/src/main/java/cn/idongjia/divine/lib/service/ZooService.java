package cn.idongjia.divine.lib.service;

import cn.idongjia.divine.lib.pojo.request.zoo.ZooMessageQry;
import cn.idongjia.divine.lib.pojo.response.MultiResponse;
import cn.idongjia.divine.lib.pojo.response.zoo.ZooMessageCO;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author zhangyingjie on 2018/11/22.
 */
@Path("zoo")
@Consumes(ContentType.APPLICATION_JSON_UTF_8)
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public interface ZooService {

    /**
     * 聊天室查询
     * 通用
     * @param zooMessageQry
     * @return
     */
    @POST
    @Path("search")
    @Consumes({ MediaType.APPLICATION_JSON })
    MultiResponse<ZooMessageCO> search(ZooMessageQry zooMessageQry);

}
