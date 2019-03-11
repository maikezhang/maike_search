package cn.idongjia.divine.lib.pojo.request.auction;

import cn.idongjia.divine.lib.pojo.request.Page;
import cn.idongjia.se.lib.engine.query.logical.Range;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * @author lc
 * @create at 2018/7/25.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class AuctionQry extends Page {

    /**
     * 拍品id
     */
    @QueryParam("itemIds")
    private List<Long> itemIds;
    /**
     * 专场id
     */
    @QueryParam("sessionIds")
    private List<Long> sessionIds;

    /**
     * 拍品标题
     */
    @QueryParam("itemTitle")
    private String itemTitle;

    /**
     * 类目id
     */
    @QueryParam("categoryIds")
    private List<Long> categoryIds;

    /**
     * 匠人id
     */
    @QueryParam("craftsmanIds")
    private List<Long> craftsmanIds;

    /**
     * 排除列表
     */
    @QueryParam("excludeCategoryIds")
    private List<Long> excludeCategoryIds;
    /**
     * 商品状态z
     */
    @QueryParam("itemStatus")
    private Integer    itemStatus;

    /**
     * 商品扩展表状态
     */
    @QueryParam("itemStatus")
    private Integer       itemExtStatus;
    private List<Integer> itemExtStatusList;
    /**
     * 拍卖状态
     */
    @QueryParam("auctionStatus")
    private List<Integer> auctionStatus;

    /**
     * 拍卖进程
     */
    @QueryParam("auctionStates")
    private List<Integer> auctionStates;
    /**
     * 专场状态
     */
    @QueryParam("sessionStatus")
    private List<Integer> sessionStatus;
    /**
     * 专场进程
     */
    @QueryParam("sessionStates")
    private List<Integer> sessionStates;
    /**
     * 专场进程
     */
    @QueryParam("sessionPreview")
    private Integer       sessionPreview;
    /**
     * 场次
     */
    @QueryParam("groundIds")
    private List<Long>    groundIds;
    /**
     * 排除场次
     */
    @QueryParam("excludeGroundIds")
    private List<Long>    excludeGroundIds;
    /**
     * 拍品类型
     */
    @QueryParam("auctionTypes")
    private List<Integer> auctionTypes;

    /**
     * 专场类型
     */
    @QueryParam("sessionTypes")
    private List<Integer>        sessionTypes;
    /**
     * 匠人状态 0 正常, 1 清退, 2 拉黑 -1000匠人不存在
     */
    @QueryParam("craftsmanStatusList")
    private List<Integer>        craftsmanStatusList;
    @QueryParam("auctionPriceGroupQry")
    private AuctionPriceGroupQry auctionPriceGroupQry;
    /**
     *
     */
    @QueryParam("minItemTotal")
    private Integer              minItemTotal;
    /**
     *
     */
    @QueryParam("maxItemTotal")
    private Integer              maxItemTotal;
    /**
     *
     */
    @QueryParam("zooIds")
    private List<Long>           zooIds;
    /**
     * 标题模糊查询
     */
    @QueryParam("wildItemTitle")
    private String               wildItemTitle;
    /**
     * 是否关联专场
     */
    @QueryParam("relatedSession")
    private Boolean              relatedSession;
    /**
     * 匠人昵称模糊查询
     */
    @QueryParam("wildCraftsmanName")
    private String               wildCraftsmanName;
    /**
     * 匠人用户id
     */
    @QueryParam("craftsmanId")
    private Long                 craftsmanId;
    /**
     * 按结拍时间查询，默认包含上下界
     */
    @QueryParam("planEndTimeFrom")
    private Range<Long>          planEndTimeRange;
    /**
     * 热度查询
     */
    private Range<Integer>       hotRange;
    /**
     * 创建时间
     */
    private Range<Long>          createTimeRange;
    /**
     * 起拍价
     */
    private Range<Long>          priceRange;
}
