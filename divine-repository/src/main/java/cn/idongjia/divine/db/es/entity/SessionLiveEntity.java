package cn.idongjia.divine.db.es.entity;

import cn.idongjia.se.lib.engine.annotations.EsDocument;
import cn.idongjia.se.lib.engine.annotations.EsField;
import cn.idongjia.se.lib.engine.annotations.EsFieldType;
import cn.idongjia.se.lib.entities.BasicEntity;
import lombok.Getter;
import lombok.Setter;

import static cn.idongjia.divine.db.es.entity.SessionLiveEntity.indexName;
import static cn.idongjia.divine.db.es.entity.SessionLiveEntity.typeName;
import static cn.idongjia.se.lib.constant.EsConst.ES_REPLICA;
import static cn.idongjia.se.lib.constant.EsConst.ES_SHARD;

@Getter
@Setter
@EsDocument(indexName = indexName, type = typeName, shards = ES_SHARD, replicas = ES_REPLICA, refreshInterval = "5s")
public class SessionLiveEntity extends BasicEntity {

    private static final long   serialVersionUID = 1L;
    public final static  String indexName        = "divine_session_live";
    public final static  String typeName         = "divine_session_live";
    private final static String esIdPrefix       = "a_";

    /**
     * 专场id
     */
    @EsField(type = EsFieldType.Long)
    private Long    sessionId;
    /**
     * 直播id
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
     * 直播预展时间
     */
    @EsField(type = EsFieldType.Long)
    private Long    livePreViewTime;
    /**
     * 直播拍的预计开拍时间
     */
    @EsField(type = EsFieldType.Long)
    private Long    planStartTime;
    /**
     * 直播匠人userId
     */
    @EsField(type = EsFieldType.Long)
    private Long    liveCraftsmanId;
    /**
     * 直播匠人头像
     */
    @EsField(type = EsFieldType.Keyword)
    private String  liveCraftsmanTitle;
    /**
     * 直播匠人头像
     */
    @EsField(type = EsFieldType.Keyword)
    private String  liveCraftsmanAvatar;
    /**
     * 直播匠人名字
     */
    @EsField(type = EsFieldType.Keyword)
    private String  liveCraftsmanName;
    /**
     * 直播拍预计结拍时间
     */
    @EsField(type = EsFieldType.Long)
    private Long    planEndTime;
    /**
     * 专场进程
     */
    @EsField(type = EsFieldType.Integer)
    private Integer sessionState;
    /**
     * 专场权重
     */
    @EsField(type = EsFieldType.Integer)
    private Integer sessionWeight;
    /**
     * 专场预展
     */
    @EsField(type = EsFieldType.Integer)
    private Integer sessionPreview;
    /**
     * 专场状态
     */
    @EsField(type = EsFieldType.Integer)
    private Integer sessionStatus;
    /**
     * 专场封面
     */
    @EsField(type = EsFieldType.Keyword)
    private String  sessionPic;
    /**
     * 专场标题
     */
    @EsField(type = EsFieldType.Keyword)
    private String  sessionTitle;
}
