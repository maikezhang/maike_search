package cn.idongjia.divine.db.es.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lc
 * @create at 2018/8/21.
 */
@Getter
@Setter
public class AuctionDTO {
    /**
     * 商品id
     */
    private Long    itemId;
    /**
     * 商品价格
     */
    private Long    price;
    /**
     * 当前价
     */
    private Long    currentPrice;
    /**
     * 商品标题
     */
    private String  title;
    /**
     * 图片
     */
    private String  cover;
    /**
     * 专场id
     */
    private Long    sessionId;
    /**
     * 状态
     */
    private Integer state;
    /**
     * 权重
     */
    private Integer weight;
    /**
     * 最后出价人
     */
    private Long    lastOfferUserId;
    /**
     * 开始时间
     */
    private Long    startTime;
    /**
     * 拍品更新时间
     */
    private Long    updateTime;
}
