package cn.idongjia.divine.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lc
 * @create at 2018/8/16.
 */
@Getter
@Setter
@ToString
public class AuctionItemDTO extends BaseDTO {
    private Long    zooId;
    private Integer bookTotal;
    private Integer newRecommendWeight;
    private Integer oldRecommendWeight;
    private Integer sessionPreview;
    private Integer sessionState;
    private Integer sessionStatus;
    private Integer sessionType;
    private Long    sessionId;
    private Integer sessionWeight;
    private String  customerAvatar;
    private String  customerName;
    private Long    itemCategoryId;
    private Long    auctionEndTime;
    private Integer auctionGroundId;
    private Long    auctionItemId;
    private Integer auctionState;
    private Long    auctionPlanEndTime;
    private Long    auctionStartTime;
    private Integer auctionStatus;
    private Integer auctionType;
    private Long    auctionId;
    private String  itemPicture;
    private String  itemTitle;
    private Long    itemPrice;
    private Integer itemExtStatus;
    private Integer itemStatus;
    private Long    craftsmanId;
    private Long    currentPrice;
    private Integer offerUserTotal;
    private Long    lastOfferUseId;
    private Integer craftsmanStatus;
    private Long    cellingPrice;
    private String  lastOfferUserName;
    private String  lastOfferUserAvatar;
    private String  craftsmanTitle;
    private Long    minOfferInterval;
    private Long    maxOfferInterval;
    private Long    muid;
    private Integer ladderId;
    private Long    nextItemId;
    private Integer maxContinuousOffer;
    private Long    createTime;
    private Integer hot;
    private Long    updateTime;
}
