package cn.idongjia.divine.db.mybatis.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import cn.idongjia.common.base.Base;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 描述：商品表 Database Object
 *
 * @author lc
 * @date 2018/08/09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemDO extends Base {

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
    private Integer special;

}
