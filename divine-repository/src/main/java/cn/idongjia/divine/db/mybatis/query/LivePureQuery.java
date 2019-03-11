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
public class LivePureQuery extends BaseSearch {

    /**
     * 纯直播ID 与live_show共享主键
     */
    private Long id;

    /**
     * 纯直播图片
     */
    private String pic;

    /**
     * 纯直播描述
     */
    private String desc;

    /**
     * 纯直播权重
     */
    private Integer weight;

    /**
     * 纯直播状态(-5修改中-4待审核-3审核中-2审核结束-1删除0未上线1已上线)
     */
    private Integer status;

    /**
     * 直播播出时间策略ID
     */
    private Long timeStrategy;

    /**
     * 纯直播是否免审0免审1未免
     */
    private Integer exemption;

    @Builder
    public LivePureQuery(String logPid,String orderBy, Integer limit, Integer page, Long start, Long end, Timestamp beginTime, Timestamp endTime, String order, Boolean needCount, Integer offset,
            Pagination pagination, Long id, String pic, String desc, Integer weight, Integer status, Long timeStrategy, Integer exemption, List<Long> liveIds) {
        super(orderBy, limit, page, start, end, beginTime, endTime, order, needCount,logPid, offset, pagination);
        this.id = id;
        this.pic = pic;
        this.desc = desc;
        this.weight = weight;
        this.status = status;
        this.timeStrategy = timeStrategy;
        this.exemption = exemption;
        this.liveIds = liveIds;
    }

    /**
     * 直播id
     */
    private List<Long> liveIds;


}
