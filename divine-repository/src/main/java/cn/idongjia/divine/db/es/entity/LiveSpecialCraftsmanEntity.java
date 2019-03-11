package cn.idongjia.divine.db.es.entity;

import cn.idongjia.se.lib.constant.EsConst;
import cn.idongjia.se.lib.engine.annotations.EsDocument;
import cn.idongjia.se.lib.engine.annotations.EsField;
import cn.idongjia.se.lib.engine.annotations.EsFieldType;
import cn.idongjia.se.lib.entities.BasicEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

import static cn.idongjia.divine.db.es.entity.LiveSpecialCraftsmanEntity.indexName;
import static cn.idongjia.divine.db.es.entity.LiveSpecialCraftsmanEntity.typeName;
import static cn.idongjia.se.lib.constant.EsConst.ES_REPLICA;
import static cn.idongjia.se.lib.constant.EsConst.ES_SHARD;

@Getter
@Setter
@EsDocument(indexName = indexName, type = typeName, shards = ES_SHARD, replicas = ES_REPLICA, refreshInterval = "5s")
@Component
public class LiveSpecialCraftsmanEntity extends BasicEntity {
    /**
     *
     */
    private static final long   serialVersionUID = 1L;
    public final static  String indexName        = "divine_live_craftsman_special";
    public final static  String typeName         = "divine_live_craftsman_special";
    private final static String esIdPrefix       = "l_s";

    /**
     * 专场id
     */
    @EsField(type = EsFieldType.Long)
    private Long   craftsmanUserId;
    /**
     * 头像
     */
    @EsField(type = EsFieldType.Keyword)
    private String craftsmanAvatar;
    /**
     * 名称
     */
    @EsField(type = EsFieldType.Keyword)
    private String craftsmanName;

    @EsField(type = EsFieldType.Long)
    private Long         createTime;
    @EsField(type = EsFieldType.Integer)
    private Integer      showType;
    @EsField(type = EsFieldType.Keyword, similarity = EsConst.scriptedTfidfSimilarity)
    private List<String> craftsmanSmart;
}
