package cn.idongjia.divine.db.es.entity;

import cn.idongjia.se.lib.constant.EsConst;
import cn.idongjia.se.lib.engine.annotations.EsDocument;
import cn.idongjia.se.lib.engine.annotations.EsField;
import cn.idongjia.se.lib.engine.annotations.EsFieldType;
import cn.idongjia.se.lib.entities.BasicEntity;
import cn.idongjia.se.lib.entities.common.ExtAttributeEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

import static cn.idongjia.divine.db.es.entity.AuctionEntity.indexName;
import static cn.idongjia.divine.db.es.entity.AuctionEntity.typeName;
import static cn.idongjia.se.lib.constant.EsConst.ES_REPLICA;
import static cn.idongjia.se.lib.constant.EsConst.ES_SHARD;

/**
 * @author lc
 * @create at 2018/8/16.
 */
@Getter
@Setter
@EsDocument(indexName = indexName, type = typeName, shards = ES_SHARD, replicas = ES_REPLICA, refreshInterval = "5s")
@Component
public class AuctionEntity extends BasicEntity {
    /**
     *
     */
    private static final long   serialVersionUID = 1L;
    public final static  String indexName        = "divine_auction";
    public final static  String typeName         = "divine_auction";
    private final static String esIdPrefix       = "a_";

    /**
     * 头像
     */
    @EsField(type = EsFieldType.Keyword)
    private String                   craftsmaneAvatar;
    /**
     * 匠人名称
     */
    @EsField(type = EsFieldType.Keyword)
    private String                   craftsmanName;
    /**
     * 商品id
     */
    @EsField(type = EsFieldType.Long)
    private Long                     craftsmanId;
    /**
     * 商品标题
     */
    @EsField(type = EsFieldType.Keyword)
    private String                   itemTitle;
    /**
     * 商品id
     */
    @EsField(type = EsFieldType.Long)
    private Long                     itemId;
    /**
     * 根类目id
     */
    @EsField(type = EsFieldType.Long)
    private Long                     itemCategoryId;
    /**
     * 商品价格
     */
    @EsField(type = EsFieldType.Long)
    private Long                     price;
    /**
     * 拍品状态
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  status;
    /**
     * 拍品扩展状态
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  extStatus;
    /**
     * 拍品扩展状态
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  itemStatus;
    /**
     * 商品封面图
     */
    @EsField(type = EsFieldType.Keyword)
    private String                   cover;
    /**
     * 拍品进程
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  state;
    /**
     * 结拍时间
     */
    @EsField(type = EsFieldType.Long)
    private Long                     endTime;
    /**
     * 开拍时间
     */
    @EsField(type = EsFieldType.Long)
    private Long                     startTime;
    /**
     * 预计结拍时间
     */
    @EsField(type = EsFieldType.Long)
    private Long                     planEndTime;
    /**
     * 专场类型
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  sessionType;
    /**
     * 专场id
     */
    @EsField(type = EsFieldType.Long)
    private Long                     sessionId;
    /**
     * 专场状态
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  sessionStatus;
    /**
     * 专场进程
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  sessionState;
    /**
     * 专场预展状态
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  sessionPreviewStatus;
    /**
     * 新用户推荐权重
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  newRecommendWeight;
    /**
     * 老用户推荐权重
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  oldRecommendWeight;
    /**
     * 订阅人数
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  bookTotal;
    /**
     * 出价人数
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  offerTotal;
    /**
     * 出价人数
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  auctionType;
    /**
     * 当前价
     */
    @EsField(type = EsFieldType.Long)
    private Long                     currentPrice;
    /**
     * 最后出价人id
     */
    @EsField(type = EsFieldType.Long)
    private Long                     offerUserId;
    /**
     * 最后出价人名字
     */
    @EsField(type = EsFieldType.Keyword)
    private String                   offerUserName;
    /**
     * 最后出家人头像
     */
    @EsField(type = EsFieldType.Keyword)
    private String                   offerUserAvatar;
    /**
     * 在所关联的专场中的权重
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  weight;
    /**
     * 聊天室id
     */
    @EsField(type = EsFieldType.Long)
    private Long                     zooId;
    /**
     * 场次
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  groundId;
    /**
     * 分词字段， 采用自定义算法打分
     */
    @EsField(type = EsFieldType.Keyword, similarity = EsConst.scriptedTfidfSimilarity)
    private List<String>             itemTitleSmart;
    @EsField(type = EsFieldType.Keyword, similarity = EsConst.scriptedTfidfSimilarity)
    private List<String>             craftsmanNameSmart;
    /**
     * 扩展属性
     */
    @EsField(type = EsFieldType.Nested)
    private List<ExtAttributeEntity> extAttrs;
    /**
     * 封顶价
     */
    @EsField(type = EsFieldType.Long)
    private Long                     cellingPrice;
    /**
     * 拍品关联匠人状态 user.status
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  craftsmanStatus;
    /**
     * 匠人头像
     */
    @EsField(type = EsFieldType.Keyword)
    private String                   craftsmanTitle;
    /**
     * 最小出价间隔，分
     */
    @EsField(type = EsFieldType.Long)
    private Long                     minOfferInterval;
    /**
     * 最大出价间隔，分
     */
    @EsField(type = EsFieldType.Long)
    private Long                     maxOfferInterval;
    /**
     * 拍卖师id
     */
    @EsField(type = EsFieldType.Long)
    private Long                     muid;
    /**
     * 阶梯出价规则id
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  ladderId;
    /**
     * 下一件拍品id
     */
    @EsField(type = EsFieldType.Long)
    private Long                     nextItemId;
    /**
     * 最大连续出价
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  maxContinuousOffer;
    /**
     * 拍品创建时间
     */
    @EsField(type = EsFieldType.Long)
    private Long                     createTime;
    /**
     * 热门权重
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  hot;
    /**
     * 拍品更新时间
     */
    @EsField(type = EsFieldType.Long)
    private Long                     updateTime;
}
