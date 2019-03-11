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
 * 描述：只包含加精数据，未加精的会被删除 Database Object
 *
 * @author lc
 * @date 2018/07/31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class TabRecommendQuery extends BaseSearch {

    /**
     * 
     */
    private Long rid;

    /**
     * tab id
     */
    private Long tid;

    /**
     * 类型：1分享、2商品、3运营管理、4商品模板
     */
    private Integer type;

    /**
     * 针对不同类型唯一id
     */
    private Long id;

    /**
     * 权重：0-5000，5001用于置顶
     */
    private Integer weight;

    /**
     * 创建时间
     */
    private Long createtm;

    /**
     * 对应商品或分享的发布人uid，如果是运营块，可以为空
     */
    private Long uid;

    /**
     * 是否定时加精操作
     */
    private Integer timer;

    /**
     * 
     */
    private Integer clear;

    private List<Long> ids;

    @Builder
    public TabRecommendQuery(String logPid,String orderBy, Integer limit, Integer page, Long start, Long end, Timestamp beginTime, Timestamp endTime, String order, Boolean needCount, Integer offset,
            Pagination pagination, Long rid, Long tid, Integer type, Long id, Integer weight, Long createtm, Long uid, Integer timer, Integer clear, List<Long> ids) {
        super(orderBy, limit, page, start, end, beginTime, endTime, order, needCount,logPid, offset, pagination);
        this.rid = rid;
        this.tid = tid;
        this.type = type;
        this.id = id;
        this.weight = weight;
        this.createtm = createtm;
        this.uid = uid;
        this.timer = timer;
        this.clear = clear;
        this.ids = ids;
    }
}
