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
public class CraftsmanQuery extends BaseSearch {

    /**
     * 主键(原cid)
     */
    private Long id;

    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 匠人状态 0 正常, 1 清退, 2 拉黑
     */
    private Integer status;

    /**
     * 匠人认证头衔
     */
    private String title;

    /**
     * 匠人地区(所发商品地)
     */
    private String city;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 用户ids
     */
    private List<Long> customerIds;


    @Builder
    public CraftsmanQuery(String logPid,String orderBy, Integer limit, Integer page, Long start, Long end, Timestamp beginTime, Timestamp endTime, String order, Boolean needCount, Integer offset,
            Pagination pagination, Long id, Long customerId, Integer status, String title, String city, Long createTime, Long updateTime, List<Long> customerIds) {
        super(orderBy, limit, page, start, end, beginTime, endTime, order, needCount,logPid, offset, pagination);
        this.id = id;
        this.customerId = customerId;
        this.status = status;
        this.title = title;
        this.city = city;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.customerIds = customerIds;
    }
}
