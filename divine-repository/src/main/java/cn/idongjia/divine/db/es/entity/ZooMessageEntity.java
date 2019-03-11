package cn.idongjia.divine.db.es.entity;

import cn.idongjia.se.lib.engine.annotations.EsDocument;
import cn.idongjia.se.lib.engine.annotations.EsField;
import cn.idongjia.se.lib.engine.annotations.EsFieldType;
import cn.idongjia.se.lib.entities.BasicEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import static cn.idongjia.divine.db.es.entity.ZooMessageEntity.indexName;
import static cn.idongjia.divine.db.es.entity.ZooMessageEntity.typeName;
import static cn.idongjia.se.lib.constant.EsConst.ES_REPLICA;
import static cn.idongjia.se.lib.constant.EsConst.ES_SHARD;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/11/22
 * Time: 下午2:52
 */
@Getter
@Setter
@EsDocument(indexName = indexName, type = typeName, shards = ES_SHARD, replicas = ES_REPLICA, refreshInterval = "5s")
@Component
public class ZooMessageEntity extends BasicEntity {

    private static final long   serialVersionUID = 1L;
    public final static  String indexName        = "divine_zoo";
    public final static  String typeName         = "divine_zoo";
    private final static String esIdPrefix       = "z_";

    /**
     * message id
     */
    @EsField(type = EsFieldType.Long)
    private Long    zooMessageId;
    /**
     * zooId
     */
    @EsField(type = EsFieldType.Long)
    private Long    zooId;
    /**
     * 用户id
     */
    @EsField(type = EsFieldType.Long)
    private Long    userId;
    /**
     * message类型 0-用户评论,1-出价,2-开拍,3-结拍,4-结拍倒数,5-管理员评论
     */
    @EsField(type = EsFieldType.Integer)
    private Integer type;
    /**
     * 消息内容
     */
    @EsField(type = EsFieldType.Keyword)
    private String  content;
    /**
     * 消息状态：0成功,1失败,2无效,-1删除
     */
    @EsField(type = EsFieldType.Integer)
    private Integer status;
    /**
     * 创建时间
     */
    @EsField(type = EsFieldType.Long)
    private Long    createTime;
}
