package cn.idongjia.divine.db.es.entity;

import cn.idongjia.se.lib.engine.annotations.EsDocument;
import cn.idongjia.se.lib.engine.annotations.EsField;
import cn.idongjia.se.lib.engine.annotations.EsFieldType;
import cn.idongjia.se.lib.entities.BasicEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import static cn.idongjia.divine.db.es.entity.AuctionStateEntity.indexName;
import static cn.idongjia.divine.db.es.entity.AuctionStateEntity.typeName;
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
public class AuctionStateEntity extends BasicEntity {
    public final static  String indexName  = "divine_auction_state";
    public final static  String typeName   = "divine_auction_state";
    private final static String esIdPrefix = "a_s";

    @EsField(type = EsFieldType.Long)
    private Long    craftsmanId;
    @EsField(type = EsFieldType.Integer)
    private Integer startedTotal;
    @EsField(type = EsFieldType.Integer)
    private Integer unstartTotal;
    @EsField(type = EsFieldType.Integer)
    private Integer endTotal;
    /**
     * 匠人头衔
     */
    @EsField(type = EsFieldType.Keyword)
    private String  craftsmanTitle;
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
}
