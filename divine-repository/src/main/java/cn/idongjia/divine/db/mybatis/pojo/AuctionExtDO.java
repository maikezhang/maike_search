package cn.idongjia.divine.db.mybatis.pojo;

import cn.idongjia.common.base.Base;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 描述： Database Object
 *
 * @author ,yuexiaodong@idongjia.cn
 * @date 2018/12/18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuctionExtDO extends Base {

    /**
     * 拍卖ID
     */
    private Long itemId;

    /**
     * 绝杀拍循环结束时间
     */
    private Long circleEndTime;

    /**
     * 绝杀拍持续时间
     */
    private Long auctionDuration;

    /**
     * 绝杀拍是否循环0、否1、是
     */
    private Integer circulation;

    /**
     * 断崖拍封底价格
     */
    private Long floorPrice;

    /**
     * 断崖拍每次降价幅度
     */
    private Long decadePrice;

    /**
     * 断崖拍降价间隔
     */
    private Long decadeDuration;

    /**
     * 是否推送 0：不推（默认）1：推
     */
    private Integer notify;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 最后更新时间
     */
    private Long updateTime;

    /**
     * 
     */
    private Long id;

    /**
     * 结拍是否推送tip，1=推送，0=不推送
     */
    private Integer endTip;

}