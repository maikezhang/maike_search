package cn.idongjia.divine.query;

import cn.idongjia.se.lib.engine.annotations.QueryField;
import cn.idongjia.se.lib.engine.annotations.QueryFieldType;
import cn.idongjia.se.lib.engine.query.LeafQuery;
import cn.idongjia.se.lib.engine.query.boost.TermsField;
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
public class LiveESQuery extends LeafQuery {
    /**
     * 直播id
     */
    @QueryField(type = QueryFieldType.IN, fieldName = "id")
    private List<Long>       ids;
    @QueryField(type = QueryFieldType.IN, fieldName = "craftsmanId")
    private List<Long>       craftsmanIds;
    @QueryField(type = QueryFieldType.IN, fieldName = "sessionId")
    private List<Long>       sessionIds;
    @QueryField(type = QueryFieldType.BOOST_TERMS, fieldName = "id")
    private TermsField<Long> liveIds;
    /**
     * 直播标题
     */
    @QueryField(type = QueryFieldType.IN)
    private List<String>     titleSmart;

    @QueryField(fieldName = "titleSmart")
    private String titleTerm;

    @QueryField(fieldName = "title", type = QueryFieldType.WILDCARD)
    private String wildTitle;

    @QueryField(fieldName = "craftsmanSmart")
    private String              craftsmanTerm;
    @QueryField(type = QueryFieldType.IN)
    private List<String>        craftsmanSmart;
    /**
     * 是否上线
     */
    @QueryField
    private Integer             online;
    /**
     * 可以接受的直播开播状态列表
     */
    @QueryField(type = QueryFieldType.IN, fieldName = "state")
    private List<Integer>       stateList;
    /**
     * 可以接受的直播上下线状态列表
     */
    @QueryField(type = QueryFieldType.IN, fieldName = "status")
    private List<Integer>       statusList;
    /**
     * 排除列表
     */
    @QueryField(type = QueryFieldType.NOT_IN, fieldName = "id")
    private List<Long>          notContainIds;
    /**
     * 直播类型
     */
    @QueryField(type = QueryFieldType.IN, fieldName = "liveType")
    private List<Integer>       typeList;
    /**
     * 主播id
     */
    @QueryField(type = QueryFieldType.IS, fieldName = "userId")
    private Long                uid;
    /**
     * 批量用户id
     */
    @QueryField(type = QueryFieldType.BOOST_TERMS, fieldName = "userId")
    private TermsField          userIds;
    @QueryField(type = QueryFieldType.IS, fieldName = "sessionId")
    private Long                sessionId;
    @QueryField(type = QueryFieldType.IS, fieldName = "zid")
    private Long                zooId;
    /**
     * 批量zooId
     */
    @QueryField(type = QueryFieldType.IN,fieldName = "zid")
    private List<Long>          zooIds;
    //  0都不显示 1 app 2小程序线上app不显示  3 小程序和app都显示
    @QueryField(type = QueryFieldType.IN, fieldName = "showLocation")
    private List<Integer>       showLocation;
    /**
     * 创建时间
     */
    @QueryField(type = QueryFieldType.RANGE, fieldName = "createTime")
    private Range<Long>         createTime;
    /**
     * 预计开始时间
     */
    @QueryField(type = QueryFieldType.RANGE, fieldName = "preStartTime")
    private Range<Long>         preStartTime;
    /**
     * 预计结束时间
     */
    @QueryField(type = QueryFieldType.RANGE, fieldName = "preEndTime")
    private Range<Long>         preEndTime;
    /**
     * 开始时间
     */
    @QueryField(type = QueryFieldType.RANGE, fieldName = "startTime")
    private Range<Long>         startTime;
    /**
     * 最小结束时间
     */
    @QueryField(type = QueryFieldType.RANGE, fieldName = "endTime")
    private Range<Long>         endTime;
    /**
     * 最小权重
     */
    @QueryField(type = QueryFieldType.RANGE, fieldName = "generalWeight")
    private Range<Integer>      minWeight;
    /**
     * 是否有回放
     */
    @QueryField(type = QueryFieldType.IS, fieldName = "hasPlayBack")
    private Boolean             hasPlayBack;
    /**
     * 状态
     */
    @QueryField(type = QueryFieldType.BOOST_TERMS, fieldName = "status")
    private TermsField<Integer> statusTerms;
    @QueryField(type = QueryFieldType.BOOST_TERMS, fieldName = "state")
    private TermsField<Integer> stateTerms;
    /**
     * 类目
     */
    @QueryField(type = QueryFieldType.Nested, fieldName = "categoryEntities")
    private CategoryESQuery     categoryESQuery;
    /**
     * 额外参数
     */
    @QueryField(type = QueryFieldType.Nested, fieldName = "extAttrs")
    private LiveExtAttrQuery    extAttrs;

    @Override
    public String indexName() {
        return "divine_live";
    }

    @Override
    public String typeName() {
        return "divine_live";
    }
}
