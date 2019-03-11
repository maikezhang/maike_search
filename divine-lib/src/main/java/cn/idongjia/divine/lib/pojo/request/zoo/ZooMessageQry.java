package cn.idongjia.divine.lib.pojo.request.zoo;

import cn.idongjia.divine.lib.pojo.request.Page;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/11/22
 * Time: 下午2:26
 */
@Getter
@Setter
@ToString(callSuper = true)
public class ZooMessageQry extends Page {

    /**
     * 聊天室messageId
     */
    @QueryParam("zooMessageId")
    private Long          zooMessageId;
    /**
     * 批量messageId
     */
    @QueryParam("zooMessageIds")
    private List<Long>    zooMessageIds;
    /**
     * 聊天室id
     */
    @QueryParam("zooId")
    private Long          zooId;
    /**
     * 批量zooIds
     */
    @QueryParam("zooIds")
    private List<Long>    zooIds;
    /**
     * 用户id
     */
    @QueryParam("userId")
    private Long          userId;
    /**
     * 批量用户ids
     */
    @QueryParam("userIds")
    private List<Long>    userIds;
    /**
     * 评论消息内容
     */
    @QueryParam("wildContent")
    private String        wildContent;
    /**
     * 最大创建时间
     */
    @QueryParam("maxCreateTime")
    private Long          maxCreateTime;
    /**
     * 最小创建时间
     */
    @QueryParam("minCreateTime")
    private Long          minCreateTime;
    /**
     * 消息内容状态
     */
    @QueryParam("status")
    private List<Integer> status;
    /**
     * 消息类型
     */
    @QueryParam("types")
    private List<Integer> types;
}
