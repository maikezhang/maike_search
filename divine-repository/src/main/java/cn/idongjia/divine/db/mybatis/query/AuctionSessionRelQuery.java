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
 * 描述： Database Object
 *
 * @author lc
 * @date 2018/07/31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class AuctionSessionRelQuery extends BaseSearch {

    /**
     * 拍卖专场关系表id
     */
    private Integer asrid;

    /**
     * 拍卖专场id
     */
    private Integer asid;

    /**
     * 权重
     */
    private Integer weight;

    /**
     * 与kp_auction iid对应
     */
    private Long aid;

    private List<Long> sessionIds;


    @Builder
    public AuctionSessionRelQuery(String logPid,String orderBy, Integer limit, Integer page, Long start, Long end, Timestamp beginTime, Timestamp endTime, String order, Boolean needCount, Integer offset,
            Pagination pagination, Integer asrid, Integer asid, Integer weight, Long aid, List<Long> sessionIds) {
        super(orderBy, limit, page, start, end, beginTime, endTime, order, needCount,logPid, offset, pagination);
        this.asrid = asrid;
        this.asid = asid;
        this.weight = weight;
        this.aid = aid;
        this.sessionIds = sessionIds;
    }


}
