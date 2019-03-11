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
 * @author lc
 * @create at 2018/8/7.
 */
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionESQuery extends LeafQuery {
    /**
     * 纯直播状态(-5修改中-4待审核-3审核中-2审核结束-1删除0未上线1已上线)
     */
    @QueryField(type = QueryFieldType.IN, fieldName = "status")
    private List<Integer> status;
    /**
     * 直播进程1未开始2已开始3已结束
     */
    @QueryField(type = QueryFieldType.IN, fieldName = "state")
    private List<Integer> state;
    /**
     * 创建人类型
     */
    @QueryField(type = QueryFieldType.IS, fieldName = "creatorType")
    private Integer       creatorType;
    /**
     * 创建人id
     */
    @QueryField(type = QueryFieldType.IS, fieldName = "creatorId")
    private Long          creatorId;
    /**
     * 专场类型
     */
    @QueryField(type = QueryFieldType.IN, fieldName = "sessionType")
    private List<Integer> sessionTypes;
    /**
     * 是否预展
     */
    @QueryField(type = QueryFieldType.IS, fieldName = "preview")
    private Integer       preview;
    /**
     * flag
     */
    @QueryField(type = QueryFieldType.IS, fieldName = "djtFlag")
    public  Integer       djtFlag;
    @QueryField(type = QueryFieldType.IN, fieldName = "sessionId")
    private List<Long>    sessionIds;
    /**
     * 结拍时间
     */
    @QueryField(type = QueryFieldType.RANGE, fieldName = "planEndTime")
    private Range<Long>   planEndTime;
    /**
     * 专场标题模糊查询
     */
    @QueryField(type = QueryFieldType.WILDCARD, fieldName = "title")
    private String        wildTitle;
}
