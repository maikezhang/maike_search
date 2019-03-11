package cn.idongjia.divine.lib.pojo.response.auction;

import cn.idongjia.divine.lib.pojo.response.ClientObject;
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
public class AuctionCO extends ClientObject {
    /**
     * 头像
     */
    private String  craftsmaneAvatar;
    /**
     * 匠人名称
     */
    private String  craftsmanName;
    /**
     * 匠人头衔
     */
    private String  craftsmanTitle;
    /**
     * 商品id
     */
    private Long    craftsmanId;
    /**
     * 商品标题
     */
    private String  itemTitle;
    /**
     * 商品id
     */
    private Long    itemId;
    /**
     * 根类目id
     */
    private Long    itemCategoryId;
    /**
     * 商品价格
     */
    private Long    price;
    /**
     * 拍品状态
     */
    private Integer status;
    /**
     * 拍品扩展状态
     */
    private Integer extStatus;
    /**
     * 商品封面图
     */
    private String  cover;
    /**
     * 拍品进程
     */
    private Integer state;
    /**
     * 结拍时间
     */
    private Long    endTime;
    /**
     * 开拍时间
     */
    private Long    startTime;
    /**
     * 专场类型
     */
    private Integer sessionType;
    /**
     * 专场id
     */
    private Long    sessionId;
    /**
     * 专场状态
     */
    private Integer sessionStatus;
    /**
     * 专场进程
     */
    private Integer sessionState;
    /**
     * 专场预展状态
     */
    private Integer sessionPreviewStatus;
    /**
     * 订阅人数
     */
    private Integer bookTotal;
    /**
     * 出价人数
     */
    private Integer offerUserTotal;
    /**
     * 当前价
     */
    private Long    currentPrice;
    /**
     * 最后出价人id
     */
    private Long    offerUserId;
    /**
     * 最后出价人名字
     */
    private String  offerUserName;
    /**
     * 最后出家人头像
     */
    private String  offerUserAvatar;
    /**
     * 场次
     */
    private Integer groundId;
    /**
     * 拍品类型
     */
    private Integer auctionType;
    /**
     * 预计结拍时间
     */
    private Long    planEndTime;
    /**
     * 封顶价
     */
    private Long    cellingPrice;
    /**
     * 最小出价间隔，分
     */
    private Long    minOfferInterval;
    /**
     * 最大出价间隔，分
     */
    private Long    maxOfferInterval;
    /**
     * 拍卖师id
     */
    private Long    muid;
    /**
     * 阶梯出价规则id
     */
    private Integer ladderId;
    /**
     * 聊天室id
     */
    private Long    zooId;
    /**
     * 下一件开拍的拍品id
     */
    private Long    nextItemId;
    /**
     * 创建时间
     */
    private Long    createTime;
    /**
     * 热门权重
     */
    private Integer hot;
    /**
     * 拍品更新时间
     */
    private Long    updateTime;

}
