package cn.idongjia.divine.db.mybatis.query;

import cn.idongjia.common.pagination.Pagination;
import cn.idongjia.common.query.BaseSearch;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 描述：商品表 Database Object
 *
 * @author lc
 * @date 2018/08/09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ItemQuery extends BaseSearch {

    /**
     * 商品编号
     */
    private Long iid;

    /**
     * 所属商品分享编号
     */
    private Long pid;

    /**
     * 商品发布人
     */
    private Integer uid;

    /**
     * 发布时间, ms
     */
    private Integer createtm;

    /**
     * 修改时间，包含上下架的时间
     */
    private Integer updatetm;

    /**
     * 商品名称
     */
    private String title;

    /**
     * 所属类目
     */
    private Integer category;

    /**
     * 配图, 序列化字段
     */
    private String pictures;

    /**
     * 描述内容
     */
    private String desc;

    /**
     * 现货or定制 1 现货 2 定制
     */
    private Integer type;

    /**
     * 库存量
     */
    private Integer stock;

    /**
     * 单价
     */
    private java.math.BigDecimal price;

    /**
     * 勾选的服务，如七天无理由退款  关联kp_item_service的id
     */
    private String services;

    /**
     * 评价数
     */
    private Integer evlcnt;

    /**
     * 0 未上架 1 已上架 -1 已删除
     */
    private Integer status;

    /**
     * 商品销售量
     */
    private Integer sellcnt;

    /**
     * 开始时间
     */
    private Integer starttm;

    /**
     * 序列化信息
     */
    private String data;

    /**
     * 出售状态（0：售卖中,1：预售, 2：售卖中不可修改, 3：预售结束, 4：活动结束)
     */
    private Integer salestatus;

    /**
     * 可购买时间
     */
    private Long saletm;

    /**
     * 活动开始时间
     */
    private Long activitystarttm;

    /**
     * 商品禁售时间
     */
    private Long forbidtm;

    /**
     *
     */
    private Integer top;

    /**
     *
     */
    private Long toptm;

    /**
     * 0 正常 1 禁止修改
     */
    private Integer editstatus;

    /**
     * 类目ID
     */
    private Integer categoryId;

    /**
     * 是否特殊属性，1白名单，0普通
     */
    private Integer    special;
    /**
     * 拍品id列表
     */
    private List<Long> itemIds;

    @Builder
    public ItemQuery(String logPid,String orderBy,Integer limit,Integer page,Long start,Long end,Timestamp beginTime,Timestamp endTime,String order,Boolean needCount,Integer offset,Pagination pagination,Long iid,Long pid,Integer uid,Integer createtm,Integer updatetm,String title,Integer category,String pictures,String desc,Integer type,Integer stock,BigDecimal price,String services,Integer evlcnt,Integer status,Integer sellcnt,Integer starttm,String data,Integer salestatus,Long saletm,Long activitystarttm,Long forbidtm,Integer top,Long toptm,Integer editstatus,Integer categoryId,Integer special,List<Long> itemIds) {
        super(orderBy,limit,page,start,end,beginTime,endTime,order,needCount,logPid,offset,pagination);
        this.iid = iid;
        this.pid = pid;
        this.uid = uid;
        this.createtm = createtm;
        this.updatetm = updatetm;
        this.title = title;
        this.category = category;
        this.pictures = pictures;
        this.desc = desc;
        this.type = type;
        this.stock = stock;
        this.price = price;
        this.services = services;
        this.evlcnt = evlcnt;
        this.status = status;
        this.sellcnt = sellcnt;
        this.starttm = starttm;
        this.data = data;
        this.salestatus = salestatus;
        this.saletm = saletm;
        this.activitystarttm = activitystarttm;
        this.forbidtm = forbidtm;
        this.top = top;
        this.toptm = toptm;
        this.editstatus = editstatus;
        this.categoryId = categoryId;
        this.special = special;
        this.itemIds = itemIds;
    }
}
