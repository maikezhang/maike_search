package cn.idongjia.divine.db.mybatis.query;

import cn.idongjia.common.query.BaseSearch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 描述： Database Object
 *
 * @author , yuexiaodong@idongjia.cn
 * @date 2018/12/18
 */
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AuctionExtQuery extends BaseSearch {

    /**
     * 拍卖ID
     */
    private Long       itemId;
    private List<Long> itemIds;

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