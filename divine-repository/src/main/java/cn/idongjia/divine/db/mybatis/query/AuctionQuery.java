package cn.idongjia.divine.db.mybatis.query;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import cn.idongjia.common.pagination.Pagination;
import cn.idongjia.common.query.BaseSearch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述： Database Object
 *
 * @author lc
 * @date 2018/08/16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class AuctionQuery extends BaseSearch {

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 拍品id 与item表iid相关
     */
    private Long iid;

    /**
     * 最后出价人
     */
    private Long uid;

    /**
     *
     */
    private Long rid;

    /**
     * 拍卖最终出价
     */
    private java.math.BigDecimal price;

    /**
     * 开始时间
     */
    private Long starttm;

    /**
     * 结束时间
     */
    private Long endtm;

    /**
     * 状态：-1删除，0正常,1流拍,2待支付
     */
    private Integer status;

    /**
     * 状态：0未开始,1已经开始,2结束
     */
    private Integer state;

    /**
     * 拍卖狮uid
     */
    private Long muid;

    /**
     * 拍品参考价
     */
    private java.math.BigDecimal refprice;

    /**
     * 最低加价范围
     */
    private java.math.BigDecimal interval;

    /**
     * 下一个拍品
     */
    private Long nextiid;

    /**
     * 分享描述
     */
    private String sharedesc;

    /**
     * 阶梯规则id
     */
    private Integer lid;

    /**
     * 佣金比例
     */
    private java.math.BigDecimal commission;

    /**
     * 佣金金额
     */
    private java.math.BigDecimal cmsprice;

    /**
     * 保证金
     */
    private java.math.BigDecimal deposit;

    /**
     * 是否开启保证金验证 0-不开启,1-开启
     */
    private Integer vdeposit;

    /**
     * 是否热门及权重，-1非热门，大于-1均为热门，正数为权重
     */
    private Integer hot;

    /**
     * 预计结拍时间
     */
    private Integer planetime;

    /**
     * kp_auction_ground场次id
     */
    private Integer ground;

    /**
     * 是否添加结拍置顶 0-否,1-是
     */
    private Integer ending;

    /**
     * 聊天室id
     */
    private Long zooId;

    /**
     * 最大加价价格
     */
    private java.math.BigDecimal maxinterval;

    /**
     * 拍卖类型1、绝杀拍2、断崖拍
     */
    private Integer type;

    /**
     * 订单关闭时间间隔
     */
    private Long orderCancelTime;

    /**
     * 拍卖封顶价
     */
    private java.math.BigDecimal cellingPrice;

    /**
     * 保证金额度
     */
    private java.math.BigDecimal depositNum;

    /**
     * 能否关联（0：可关联 1：不可（默认） )
     */
    private Integer related;

    /**
     * 最大连续出价
     */
    private Integer maxContinuousOffer;


    /**
     * 状态列表
     */
    private List<Integer> statusArray;


	/**
	 * 状态列表
	 */
	private List<Long>			 itemIds;
	
    @Builder
    public AuctionQuery(String orderBy,Integer limit,Integer page,
            Long start,Long end,Timestamp beginTime,Timestamp endTime,
            String order,Boolean needCount,Integer offset,Pagination pagination,
            Long id,Long iid,Long uid,Long rid,BigDecimal price,Long starttm,Long endtm,
            Integer status,Integer state,Long muid,BigDecimal refprice,BigDecimal interval,
            Long nextiid,String sharedesc,Integer lid,BigDecimal commission,BigDecimal cmsprice,
            BigDecimal deposit,Integer vdeposit,Integer hot,Integer planetime,Integer ground,
            Integer ending,Long zooId,BigDecimal maxinterval,Integer type,Long orderCancelTime,
            BigDecimal cellingPrice,BigDecimal depositNum,Integer related,Integer maxContinuousOffer,
	    List<Integer> statusArray, List<Long> itemIds,String logPid) {
        super(orderBy,limit,page,start,end,beginTime,endTime,order,needCount,logPid,offset,pagination);
        this.id = id;
        this.iid = iid;
        this.uid = uid;
        this.rid = rid;
        this.price = price;
        this.starttm = starttm;
        this.endtm = endtm;
        this.status = status;
        this.state = state;
        this.muid = muid;
        this.refprice = refprice;
        this.interval = interval;
        this.nextiid = nextiid;
        this.sharedesc = sharedesc;
        this.lid = lid;
        this.commission = commission;
        this.cmsprice = cmsprice;
        this.deposit = deposit;
        this.vdeposit = vdeposit;
        this.hot = hot;
        this.planetime = planetime;
        this.ground = ground;
        this.ending = ending;
        this.zooId = zooId;
        this.maxinterval = maxinterval;
        this.type = type;
        this.orderCancelTime = orderCancelTime;
        this.cellingPrice = cellingPrice;
        this.depositNum = depositNum;
        this.related = related;
        this.maxContinuousOffer = maxContinuousOffer;
        this.statusArray = statusArray;
		this.itemIds = itemIds;
    }
}
