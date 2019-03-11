package cn.idongjia.divine.lib.pojo.response.zoo;

import cn.idongjia.divine.lib.pojo.response.ClientObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/11/22
 * Time: 下午2:12
 */
@Getter
@Setter
@ToString
public class ZooMessageCO extends ClientObject {

    /**
     * 聊天室messageId
     */
    private Long    zooMessageId;
    /**
     * 聊天室id
     */
    private Long    zooId;
    /**
     * 用户id
     */
    private Long    userId;
    /**
     * 评论消息内容
     */
    private String  content;
    /**
     * 操作时间
     */
    private Long    createTime;
    /**
     * 消息类型
     */
    private Integer type;
    /**
     * 消息状态
     */
    private Integer status;


}
