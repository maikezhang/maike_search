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
 * 描述：客户用户关系表 Database Object
 *
 * @author lc
 * @date 2018/07/31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class CustomerUserRelQuery extends BaseSearch {

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 状态: 1 正常 0 删除
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    @Builder
    public CustomerUserRelQuery(String logPid,String orderBy, Integer limit, Integer page, Long start, Long end, Timestamp beginTime, Timestamp endTime, String order, Boolean needCount, Integer offset,
            Pagination pagination, Integer userId, Integer customerId, Integer status, Date createTime, Date updateTime, List<Long> userIds) {
        super(orderBy, limit, page, start, end, beginTime, endTime, order, needCount,logPid, offset, pagination);
        this.userId = userId;
        this.customerId = customerId;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.userIds = userIds;
    }

    /**
     * 用户id
     */
    private List<Long> userIds;
}
