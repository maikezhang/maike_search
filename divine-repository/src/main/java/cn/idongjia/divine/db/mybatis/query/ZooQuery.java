package cn.idongjia.divine.db.mybatis.query;

import cn.idongjia.common.pagination.Pagination;
import cn.idongjia.common.query.BaseSearch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.List;

/**
 * 描述：聊天室表 Database Object
 *
 * @author lc
 * @date 2018/07/31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ZooQuery extends BaseSearch {

    /**
     * 聊天室id
     */
    private Long zid;

    /**
     * 聊天室名称
     */
    private String name;

    /**
     * 聊天室管理员(拍卖狮)
     */
    private Long uid;

    /**
     * zoo random count(聊天室随机目标观看数)
     */
    private Integer zrc;

    /**
     * 创建时间
     */
    private Long createtm;

    private List<Long> zooIds;

    @Builder
    public ZooQuery(String logPid,String orderBy, Integer limit, Integer page, Long start, Long end, Timestamp beginTime, Timestamp endTime, String order, Boolean needCount, Integer offset, Pagination pagination
            , Long zid, String name, Long uid, Integer zrc, Long createtm, List<Long> zooIds) {
        super(orderBy, limit, page, start, end, beginTime, endTime, order, needCount,logPid, offset, pagination);
        this.zid = zid;
        this.name = name;
        this.uid = uid;
        this.zrc = zrc;
        this.createtm = createtm;
        this.zooIds = zooIds;
    }
}
