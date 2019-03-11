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

import static cn.idongjia.divine.db.es.entity.LiveEntity.indexName;
import static cn.idongjia.divine.db.es.entity.LiveEntity.typeName;
import static cn.idongjia.se.lib.constant.EsConst.ES_REPLICA;
import static cn.idongjia.se.lib.constant.EsConst.ES_SHARD;

@Getter
@Setter
@EsDocument(indexName = indexName, type = typeName, shards = ES_SHARD, replicas = ES_REPLICA, refreshInterval = "5s")
@Component
public class LiveEntity extends BasicEntity {
    /**
     *
     */
    private static final long   serialVersionUID = 1L;
    public final static  String indexName        = "divine_live";
    public final static  String typeName         = "divine_live";
    private final static String esIdPrefix       = "l_";

    /**
     * 专场id
     */
    @EsField(type = EsFieldType.Long)
    private Long                     sessionId;
    /**
     * 头像
     */
    @EsField(type = EsFieldType.Keyword)
    private String                   avatar;
    /**
     * 名称
     */
    @EsField(type = EsFieldType.Keyword)
    private String                   craftsmanName;
    /**
     * 标题
     */
    @EsField(type = EsFieldType.Keyword)
    private String                   title;
    /**
     * 纯直播状态(-5修改中-4待审核-3审核中-2审核结束-1删除0未上线1已上线)
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  status;
    /**
     * 开始时间
     */
    @EsField(type = EsFieldType.Long)
    private Long                     startTime;
    /**
     * 结束时间
     */
    @EsField(type = EsFieldType.Long)
    private Long                     endTime;
    /**
     * 权重
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  generalWeight;
    /**
     * 封面图地址
     */
    @EsField(type = EsFieldType.Keyword)
    private String                   pic;
    /**
     * 短视频地址
     */
    @EsField(type = EsFieldType.Keyword)
    private String                   videoUrl;
    /**
     * 匠人职称
     */
    @EsField(type = EsFieldType.Keyword)
    private String                   crftsmanTitle;
    /**
     * 匠人所在城市
     */
    @EsField(type = EsFieldType.Keyword)
    private String                   city;
    /**
     * 短视频地址
     */
    @EsField(type = EsFieldType.Keyword)
    private String                   videoPic;
    /**
     * 直播类型
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  liveType;
    /**
     * 直播进程1未开始2已开始3已结束
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  state;
    /**
     * 直播预计开始时间
     */
    @EsField(type = EsFieldType.Long)
    private Long                     preStartTime;
    /**
     * 直播预计结束时间
     */
    @EsField(type = EsFieldType.Long)
    private Long                     preEndTime;
    /**
     * 直播创建时间
     */
    @EsField(type = EsFieldType.Long)
    private Long                     createTime;
    /**
     * 回放
     */
    @EsField(type = EsFieldType.Boolean)
    private Boolean                  hasPlayBack;
    /**
     * 关联商品id
     */
    @EsField(type = EsFieldType.Nested)
    private List<ItemEntity>         items;
    /**
     * 主播id
     */
    @EsField(type = EsFieldType.Long)
    private Long                     userId;
    /**
     * 主播类型 1匠人0 用户
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  userType;
    /**
     * 上线状态
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  online;
    /**
     * 直播时长
     */
    @EsField(type = EsFieldType.Long)
    private Long                     duration;
    /**
     * 直播屏幕方向
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  screenDirection;
    /**
     * 更新时间
     */
    @EsField(type = EsFieldType.Long)
    private Long                     updateTime;
    /**
     * 推荐权重
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  recommendWeight;
    /**
     * uv
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  uv;
    /**
     * 小视频id
     */
    @EsField(type = EsFieldType.Long)
    private Long                     videoId;
    /**
     * 直播间id
     */
    @EsField(type = EsFieldType.Long)
    private Long                     zid;
    /**
     * 聊天室管理员id
     */
    @EsField(type = EsFieldType.Long)
    private Long                     suid;
    /**
     * 直播间随机数
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  zrc;
    /**
     * 预展时间
     */
    @EsField(type = EsFieldType.Long)
    private Long                     preViewTm;
    /**
     * 聊天室id
     */
    @EsField(type = EsFieldType.Long)
    private Long                     roomId;
    /**
     * 订阅人数
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  bookTotal;
    /**
     * 分词字段， 采用自定义算法打分
     */
    @EsField(type = EsFieldType.Keyword, similarity = EsConst.scriptedTfidfSimilarity)
    private List<String>             titleSmart;
    /**
     * 匠人名称分词
     */
    @EsField(type = EsFieldType.Keyword, similarity = EsConst.scriptedTfidfSimilarity)
    private List<String>             craftsmanSmart;
    /**
     * 运营强配置数据， 新用户权重。
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  newUserWeight;
    /**
     *
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  oldUserWeight;
    /**
     * 由匠人的运营类目映射得来的，这个算法组同学@孙逸文 将映射好的数据传给后端。
     */
    @EsField(type = EsFieldType.Nested)
    private List<CategoryEntity>     categoryEntities;
    /**
     * 0 小程序app都不显示 1app显示 2 小程序显示app不显示 3小程序app都显示
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  showLocation;
    /**
     * 直播间真实在线人数
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  realUV;
    /**
     * 五分钟统计一次 累计的发言次数
     */
    @EsField(type = EsFieldType.Integer)
    private Integer                  totalMessageCount;
    /**
     * 扩展属性
     */
    @EsField(type = EsFieldType.Nested)
    private List<ExtAttributeEntity> extAttrs;

}
