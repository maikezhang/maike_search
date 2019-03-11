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
 * 描述：基本直播信息表 Database Object
 *
 * @author lc
 * @date 2018/07/31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class LiveShowQuery extends BaseSearch {

    /**
     * 直播id
     */
    private Long id;

    /**
     * 直播名称
     */
    private String title;

    /**
     * 主播uid
     */
    private Integer uid;

    /**
     * 直播类型1. 纯直播 2. 拍卖直播
     */
    private Integer type;

    /**
     * 直播状态-1删除0正常
     */
    private Integer status;

    /**
     * 直播聊天室id
     */
    private Long zid;

    /**
     * 直播预展时间
     */
    private Date previewtm;

    /**
     * 直播预计开始时间
     */
    private Date prestarttm;

    /**
     * 直播预计结束时间
     */
    private Date preendtm;

    /**
     * 直播开始时间
     */
    private Date starttm;

    /**
     * 直播结束时间
     */
    private Date endtm;

    /**
     * 直播间ID
     */
    private Long roomId;

    /**
     * 直播最后修改时间
     */
    private Date modifiedtm;

    /**
     * 直播创建时间
     */
    private Date createtm;

    /**
     * 直播进程1未开始2已开始3已结束
     */
    private Integer state;

    /**
     * 小视频id,对应 live_video id
     */
    private Long videoCoverId;

    /**
     * 所有直播的通用权重
     */
    private Integer generalWeight;

    /**
     * 直播的上下线状态，0=下线，1=上线
     */
    private Integer online;

    /**
     * 直播介绍
     */
    private String showDesc;

    /**
     * 屏幕方向0、横屏1、竖屏
     */
    private Integer screenDirection;

    /**
     * 是否自动上线0、否1、是
     */
    private Integer needAutoOnline;

    /**
     * 状态列表
     */
    private List<Integer> statusArray;
    /**
     * 进程列表
     */
    private List<Integer> states;

    private Long       minUpdateTime;
    /**
     * 直播id
     */
    private List<Long> liveIds;

    @Builder
    public LiveShowQuery(String logPid,String orderBy,Integer limit,Integer page,Long start,Long end,Timestamp beginTime,Timestamp endTime,String order,Boolean needCount,Integer offset,Pagination pagination,Long id,String title,Integer uid,Integer type,Integer status,Long zid,Date previewtm,Date prestarttm,Date preendtm,Date starttm,Date endtm,Long roomId,Date modifiedtm,Date createtm,Integer state,Long videoCoverId,Integer generalWeight,Integer online,String showDesc,Integer screenDirection,Integer needAutoOnline,List<Integer> statusArray,Long minUpdateTime,List<Integer> states,List<Long> liveIds) {
        super(orderBy,limit,page,start,end,beginTime,endTime,order,needCount,logPid,offset,pagination);
        this.id = id;
        this.title = title;
        this.uid = uid;
        this.type = type;
        this.status = status;
        this.zid = zid;
        this.previewtm = previewtm;
        this.prestarttm = prestarttm;
        this.preendtm = preendtm;
        this.starttm = starttm;
        this.endtm = endtm;
        this.roomId = roomId;
        this.modifiedtm = modifiedtm;
        this.createtm = createtm;
        this.state = state;
        this.videoCoverId = videoCoverId;
        this.generalWeight = generalWeight;
        this.online = online;
        this.showDesc = showDesc;
        this.screenDirection = screenDirection;
        this.needAutoOnline = needAutoOnline;
        this.statusArray = statusArray;
        this.states = states;
        this.minUpdateTime = minUpdateTime;
        this.liveIds = liveIds;
    }
}
