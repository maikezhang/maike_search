package cn.idongjia.divine.query;

import cn.idongjia.se.lib.engine.annotations.QueryField;
import cn.idongjia.se.lib.engine.annotations.QueryFieldType;
import cn.idongjia.se.lib.engine.query.LeafQuery;
import cn.idongjia.se.lib.engine.query.logical.Range;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/11/23
 * Time: 下午2:31
 */
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ZooMessageESQuery extends LeafQuery {

    /**
     * 聊天室messageId
     */
    @QueryField(type = QueryFieldType.IS,fieldName = "zooMessageId")
    private     Long       zooMessageId;
    /**
     * 批量messageId
     */
    @QueryField(type = QueryFieldType.IN,fieldName = "zooMessageId")
    private     List<Long> zooMessageIds;
    /**
     * 聊天室id
     */
    @QueryField(type = QueryFieldType.IS,fieldName = "zooId")
    private     Long       zooId;
    /**
     * 批量zooIds
     */
    @QueryField(type = QueryFieldType.IN,fieldName = "zooId")
    private List<Long>    zooIds;
    /**
     * 用户id
     */
    @QueryField(type = QueryFieldType.IS,fieldName = "userId")
    private Long          userId;
    /**
     * 批量用户ids
     */
    @QueryField(type = QueryFieldType.IN,fieldName = "userId")
    private List<Long>    userIds;
    /**
     * 评论消息内容
     */
    @QueryField(type = QueryFieldType.WILDCARD,fieldName = "content")
    private String        wildContent;
    /**
     * 最大创建时间
     */
    @QueryField(type = QueryFieldType.RANGE,fieldName = "createTime")
    private Range<Long>   createTime;
    /**
     * 消息内容状态
     */
    @QueryField(type = QueryFieldType.IN,fieldName = "status")
    private List<Integer> status;
    /**
     * 消息类型
     */
    @QueryField(type = QueryFieldType.IN,fieldName = "type")
    private List<Integer> types;
}
