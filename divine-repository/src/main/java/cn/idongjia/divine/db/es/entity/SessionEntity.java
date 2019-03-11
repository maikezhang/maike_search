package cn.idongjia.divine.db.es.entity;

import cn.idongjia.se.lib.engine.annotations.EsDocument;
import cn.idongjia.se.lib.engine.annotations.EsField;
import cn.idongjia.se.lib.engine.annotations.EsFieldType;
import cn.idongjia.se.lib.entities.BasicEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import static cn.idongjia.divine.db.es.entity.SessionEntity.indexName;
import static cn.idongjia.divine.db.es.entity.SessionEntity.typeName;
import static cn.idongjia.se.lib.constant.EsConst.ES_REPLICA;
import static cn.idongjia.se.lib.constant.EsConst.ES_SHARD;

@Getter
@Setter
@EsDocument(indexName = indexName, type = typeName, shards = ES_SHARD, replicas = ES_REPLICA, refreshInterval = "5s")
@Component
public class SessionEntity extends BasicEntity {
    /**
     *
     */
    private static final long   serialVersionUID = 1L;
    public final static  String indexName        = "divine_session";
    public final static  String typeName         = "divine_session";
    private final static String esIdPrefix       = "s_";

    /**
     * 专场id
     */
    @EsField(type = EsFieldType.Long)
    private Long    sessionId;
    /**
     * 专场标题
     */
    @EsField(type = EsFieldType.Keyword)
    private String  title;
    /**
     * 匠人头衔
     */
    @EsField(type = EsFieldType.Keyword)
    private String  craftsmanTitle;
    /**
     * 纯直播状态(-5修改中-4待审核-3审核中-2审核结束-1删除0未上线1已上线)
     */
    @EsField(type = EsFieldType.Integer)
    private Integer status;
    /**
     * 直播进程1未开始2已开始3已结束
     */
    @EsField(type = EsFieldType.Integer)
    private Integer state;
    /**
     * 权重
     */
    @EsField(type = EsFieldType.Integer)
    private Integer weight;
    /**
     * 热门权重
     */
    @EsField(type = EsFieldType.Integer)
    private Integer hotWeight;
    /**
     * 专场类型
     */
    @EsField(type = EsFieldType.Integer)
    private Integer sessionType;
    /**
     * 预计开始时间
     */
    @EsField(type = EsFieldType.Long)
    private Long    planStartTime;
    /**
     * 预计结束时间
     */
    @EsField(type = EsFieldType.Long)
    private Long    planEndTime;
    /**
     * 创建人id
     */
    @EsField(type = EsFieldType.Long)
    private Long    creatorId;
    /**
     * 短视频地址
     */
    @EsField(type = EsFieldType.Integer)
    private Integer creatorType;
    /**
     * 直播
     */
    @EsField(type = EsFieldType.Long)
    private Long    liveId;
    /**
     * 直播进程1未开始2已开始3已结束
     */
    @EsField(type = EsFieldType.Integer)
    private Integer liveState;
    /**
     * 直播预计开始时间
     */
    @EsField(type = EsFieldType.Long)
    private Long    livePreStarTime;
    /**
     * 是否预展
     */
    @EsField(type = EsFieldType.Integer)
    private Integer preview;
    /**
     * 新用户专享
     */
    @EsField(type = EsFieldType.Integer)
    private Integer forNewUser;

    /**
     * 出价次数
     */
    @EsField(type = EsFieldType.Integer)
    private Integer offerTotal;
    /**
     * 专场封面
     */
    @EsField(type = EsFieldType.Keyword)
    private String  pic;
    /**
     * 匠人名称
     */
    @EsField(type = EsFieldType.Keyword)
    private String  craftsmanName;
    /**
     * 匠人头像
     */
    @EsField(type = EsFieldType.Keyword)
    private String  craftsmanAvatar;
    /**
     * 默认为0=非激活匠人专场，1=激活匠人专场
     */
    @EsField(type = EsFieldType.Integer)
    private Integer djtFlag;
    /**
     * 关联拍品数量
     */
    @EsField(type = EsFieldType.Integer)
    private Integer relatedCount;
    /**
     * 保证金，分
     */
    @EsField(type = EsFieldType.Long)
    private Long    deposit;
    /**
     * 当专场类型是直播场时，用于记录开播和开拍的时间间隔
     * 分钟
     */
    @EsField(type = EsFieldType.Integer)
    private Integer ainterval;
    /**
     * 创建时间
     */
    @EsField(type = EsFieldType.Long)
    private Long    createTime;
}
