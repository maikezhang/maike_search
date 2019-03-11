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
 * 描述：回放表 Database Object
 *
 * @author lc
 * @date 2018/07/31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class LivePlaybackQuery extends BaseSearch {

    /**
     * 唯一ID
     */
    private Long id;

    /**
     * 对应直播ID
     */
    private Long lid;

    /**
     * 腾讯回放视频地址
     */
    private String url;

    /**
     * 回放时长
     */
    private Long duration;

    /**
     * 回放状态-1删除0正常
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

    /**
     * 最小直播时长
     */
    private Long minDuration;

    @Builder
    public LivePlaybackQuery(String logPid,String orderBy, Integer limit, Integer page, Long start, Long end, Timestamp beginTime, Timestamp endTime, String order, Boolean needCount, Integer offset,
            Pagination pagination, Long id, Long lid, String url, Long duration, Integer status, Date createtm, Date modifiedtm, List<Long> liveIds, Long minDuration) {
        super(orderBy, limit, page, start, end, beginTime, endTime, order, needCount,logPid, offset, pagination);
        this.id = id;
        this.lid = lid;
        this.url = url;
        this.duration = duration;
        this.status = status;
        this.createtm = createtm;
        this.modifiedtm = modifiedtm;
        this.liveIds = liveIds;
        this.minDuration = minDuration;
    }
}
