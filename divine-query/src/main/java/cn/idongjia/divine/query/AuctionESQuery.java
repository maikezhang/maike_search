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
public class AuctionESQuery extends LeafQuery {

    /**
     * 主播id
     */
    @QueryField(type = QueryFieldType.IS, fieldName = "craftsmanId")
    private Long             craftsmanId;
    @QueryField(type = QueryFieldType.IN, fieldName = "craftsmanId")
    private List<Long>       craftsmanIds;
    @QueryField(type = QueryFieldType.IS, fieldName = "id")
    private Long             auctionId;
    @QueryField(type = QueryFieldType.IS, fieldName = "itemCategoryId")
    private Long             itemCategoryId;
    @QueryField(type = QueryFieldType.IS, fieldName = "itemId")
    private Long             itemId;
    @QueryField(type = QueryFieldType.IS, fieldName = "zooId")
    private Long             zooId;
    @QueryField(type = QueryFieldType.IN, fieldName = "zooId")
    private List<Long>       zooIds;
    @QueryField(type = QueryFieldType.IN, fieldName = "itemId")
    private List<Long>       itemIds;
    @QueryField(type = QueryFieldType.BOOST_TERMS, fieldName = "itemId")
    private TermsField<Long> itemIdsTerm;
    @QueryField(type = QueryFieldType.IS, fieldName = "sessionId")
    private Long             sessionId;
    @QueryField(type = QueryFieldType.IS, fieldName = "extStatus")
    private Integer          itemExtStatus;
    @QueryField(type = QueryFieldType.IS, fieldName = "itemStatus")
    private Integer          itemStatus;
    @QueryField(type = QueryFieldType.IS, fieldName = "sessionPreviewStatus")
    private Integer          sessionPreviewStatus;
    @QueryField(type = QueryFieldType.IN, fieldName = "sessionId")
    private List<Long>       sessionIds;
    @QueryField(type = QueryFieldType.IN, fieldName = "state")
    private List<Integer>    auctionState;
    @QueryField(type = QueryFieldType.IN, fieldName = "status")
    private List<Integer>    auctionStatus;
    @QueryField(type = QueryFieldType.IN, fieldName = "auctionType")
    private List<Integer>    auctionTypes;
    @QueryField(type = QueryFieldType.IN, fieldName = "status")
    private List<Integer>    auctionType;
    @QueryField(type = QueryFieldType.IN, fieldName = "sessionType")
    private List<Integer>    sessionTypes;
    /**
     * 专场进程
     */
    @QueryField(type = QueryFieldType.IN, fieldName = "sessionState")
    private List<Integer>    sessionStates;
    /**
     * 专场状态
     */
    @QueryField(type = QueryFieldType.IN, fieldName = "sessionStatus")
    private List<Integer>    sessionStatus;
    @QueryField(type = QueryFieldType.IN)
    private List<String>     itemTitleSmart;
    @QueryField(fieldName = "itemTitleSmart")
    private String           itemTitleTerm;
    @QueryField(type = QueryFieldType.IN, fieldName = "craftsmanName")
    private List<String>     craftsmanSmart;
    @QueryField(fieldName = "craftsmanName")
    private String           craftsmanNameTerm;
    @QueryField(type = QueryFieldType.IN, fieldName = "itemCategoryId")
    private List<Long>       categoryIds;
    /**
     * 排除列表
     */
    @QueryField(type = QueryFieldType.NOT_IN, fieldName = "itemCategoryId")
    private List<Long>       noCategoryIds;
    @QueryField(type = QueryFieldType.IN, fieldName = "groundId")
    private List<Long>       groundIds;
    /**
     * 排除列表
     */
    @QueryField(type = QueryFieldType.NOT_IN, fieldName = "groundId")
    private List<Long>       noGroundIds;
    /**
     * 用户id
     */
    @QueryField(type = QueryFieldType.BOOST_TERMS, fieldName = "userId")
    private TermsField       userIds;
    /**
     * 商品类目
     */
    @QueryField(type = QueryFieldType.BOOST_TERMS, fieldName = "itemCategoryId")
    private TermsField       categoryIdsTerms;
    @QueryField(type = QueryFieldType.BOOST_TERMS, fieldName = "status")
    private TermsField       statusTerms;
    /**
     * 额外参数
     */
    @QueryField(type = QueryFieldType.Nested, fieldName = "extAttrs")
    private LiveExtAttrQuery extAttrs;
    /**
     * 匠人状态 0 正常, 1 清退, 2 拉黑
     */
    @QueryField(type = QueryFieldType.IN, fieldName = "craftsmanStatus")
    private List<Integer>    craftsmanStatusList;
    /**
     * 商品标题模糊查询
     */
    @QueryField(type = QueryFieldType.WILDCARD, fieldName = "itemTitle")
    private String           wildItemTitle;
    /**
     * 用于拍品是否关联专场查询
     */
    @QueryField(type = QueryFieldType.RANGE, fieldName = "sessionId")
    private Range            sessionIdRange;
    /**
     * 匠人昵称模糊查询
     */
    @QueryField(type = QueryFieldType.WILDCARD, fieldName = "craftsmanName")
    private String           wildCraftsmanName;
    /**
     * 结拍时间查询，默认包含上下界
     */
    @QueryField(type = QueryFieldType.RANGE, fieldName = "planEndTime")
    private Range<Long>      planEndTimeRange;
    /**
     * 场次id
     */
    @QueryField(type = QueryFieldType.IS, fieldName = "groundId")
    private Long             groundId;
    /**
     * 热门权重
     */
    @QueryField(type = QueryFieldType.RANGE, fieldName = "hot")
    private Range<Integer>   hotRange;
    /**
     *
     */
    @QueryField(type = QueryFieldType.RANGE, fieldName = "createTime")
    private Range<Long>      createTimeRange;
    /**
     * 起拍价
     */
    @QueryField(type = QueryFieldType.RANGE, fieldName = "price")
    private Range<Long>      priceRange;
    @QueryField(type = QueryFieldType.IN, fieldName = "extStatus")
    private List<Integer>    itemExtStatusList;
}

