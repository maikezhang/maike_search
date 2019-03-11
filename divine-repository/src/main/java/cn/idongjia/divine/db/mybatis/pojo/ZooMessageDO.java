package cn.idongjia.divine.db.mybatis.pojo;

import cn.idongjia.common.base.Base;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/11/22
 * Time: 下午3:10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ZooMessageDO extends Base {

    /**
     * messageID
     */
    private Long    zmid;
    /**
     * 聊天室id
     */
    private Long    zid;
    /**
     * 用户id
     */
    private Long    uid;
    /**
     * 消息类型：0-用户评论,1-出价,2-开拍,3-结拍,4-结拍倒数,5-管理员评论
     */
    private Integer type;
    /**
     * 消息内容
     */
    private String  content;
    /**
     * 消息状态：0成功,1失败,2无效,-1删除
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Long    createtm;
}
