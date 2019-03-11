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
 * 描述： Database Object
 *
 * @author lc
 * @date 2018/07/31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuctionSessionDO extends Base {

    /**
     * 拍卖专场id
     */
    private Integer asid;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片
     */
    private String pic;

    /**
     * 序列化内容字段
     */
    private String data;

    /**
     * -1 已删除 0 待审核 1-修改中 3 审核中 4 已拒绝 5 已审核 2 已上线
     */
    private Integer status;

    /**
     * 1 未开始2 拍卖中 3 已结束 4结拍中
     */
    private Integer state;

    /**
     * 权重
     */
    private Integer weight;

    /**
     * 创建时间
     */
    private Integer timestamp;

    /**
     * 拍卖数量
     */
    private Integer count;

    /**
     * 是否热门及权重，-1非热门，大于-1均为热门，正数为权重
     */
    private Integer hot;

    /**
     * 预计结束时间
     */
    private Integer planetime;

    /**
     * 拍场类型:0-普通拍场,1-0元拍,2-直播拍,3-晚间专场,4-线上线下同步拍
     */
    private Integer type;

    /**
     * 预计开拍时间,定时开拍用
     */
    private Integer planatime;

    /**
     * 定时开拍:0-关闭,1-开启
     */
    private Integer timestart;

    /**
     * 定时结拍:0-关闭,1-开启
     */
    private Integer timeend;

    /**
     * 专场创建人uid
     */
    private Integer uid;

    /**
     * 0-匠人 1-管理员
     */
    private Integer utype;

    /**
     * 备选开拍时间
     */
//    private String bakatime;

    /**
     * 备选结拍时间
     */
//    private String baketime;

    /**
     * 关联直播场ID
     */
    private Integer lsid;

    /**
     * 顶部描述
     */
    private String topdesc;

    /**
     * 当专场类型是直播场时，用于记录开播和开拍的时间间隔
     * 分钟
     */
    private Integer ainterval;

    /**
     * 直播场是否预展
     */
    private Integer preview;

    /**
     * 保证金
     */
    private Long deposit;

    /**
     * 是否为新用户专享；0=否，1=是
     */
    private Integer forNewUser;

    /**
     * 拍卖场次，对应kp_auction_ground.gid
     */
    private Long gid;

}
