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
 * 描述：直播小视频封面 Database Object
 *
 * @author lc
 * @date 2018/07/31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class LiveVideoCoverQuery extends BaseSearch {

    /**
     *
     */
    private Long id;

    /**
     * 小视频时长，毫秒
     */
    private Integer duration;

    /**
     * 小视频地址
     */
    private String url;

    /**
     * 小视频的默认图片
     */
    private String pic;

    /**
     *
     */
    private Long createTime;

    /**
     *
     */
    private Long updateTime;

    /**
     * 直播id
     */
    private Long liveId;

    /**
     * 直播id
     */
    private List<Long> liveIds;

    @Builder
    public LiveVideoCoverQuery(String logPid,String orderBy, Integer limit, Integer page, Long start, Long end, Timestamp beginTime, Timestamp endTime, String order, Boolean needCount, Integer offset,
            Pagination pagination, Long id, Integer duration, String url, String pic, Long createTime, Long updateTime, Long liveId, List<Long> liveIds) {
        super(orderBy, limit, page, start, end, beginTime, endTime, order, needCount,logPid, offset, pagination);
        this.id = id;
        this.duration = duration;
        this.url = url;
        this.pic = pic;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.liveId = liveId;
        this.liveIds = liveIds;
    }
}
