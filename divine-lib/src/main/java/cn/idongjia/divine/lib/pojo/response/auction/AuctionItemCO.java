package cn.idongjia.divine.lib.pojo.response.auction;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lc
 * @create at 2018/7/27.
 */
@Getter
@Setter
@ToString
public class AuctionItemCO implements Serializable {

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
     * 进程
     */
    private Integer state;
    /**
     * 关联拍品的权重
     */
    private Integer weight;
    /**
     * 最后出价人
     */
    private Long    lastOfferUserId;
    /**
     * 最后出价人名字
     */
    private String  lastOfferUserName;
    /**
     * 开拍时间
     */
    private Long    startTime;
}
