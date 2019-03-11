package cn.idongjia.divine.db.mybatis.query;

import cn.idongjia.common.pagination.Pagination;
import cn.idongjia.common.query.BaseSearch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 描述： Database Object
 *
 * @author lc
 * @date 2018/07/31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class LiveResourceQuery extends BaseSearch {

    /**
     * 唯一ID
     */
    private Long id;

    /**
     * 资源ID
     */
    private Long resId;

    /**
     * 资源类型:0=超级模版 1=商品
     */
    private Integer resType;

    /**
     * 关联的直播
     */
    private Long lid;

    /**
     * 权重
     */
    private Integer weight;

    /**
     * 资源状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createtm;

    /**
     * 最后修改时间
     */
    private Date modifiedtm;

    /**
     * 直播id
     */
    private List<Long> liveIds;

    private List<Long> resourceIds;

    @Builder
    public LiveResourceQuery(String logPid,String orderBy, Integer limit, Integer page, Long start, Long end, Timestamp beginTime, Timestamp endTime, String order, Boolean needCount, Integer offset,
            Pagination pagination, Long id, Long resId, Integer resType, Long lid, Integer weight, Integer status, Date createtm, Date modifiedtm, List<Long> liveIds,List<Long> resourceIds) {
        super(orderBy, limit, page, start, end, beginTime, endTime, order, needCount,logPid, offset, pagination);
        this.id = id;
        this.resId = resId;
        this.resType = resType;
        this.lid = lid;
        this.weight = weight;
        this.status = status;
        this.createtm = createtm;
        this.modifiedtm = modifiedtm;
        this.liveIds = liveIds;
        this.resourceIds=resourceIds;
    }
}
