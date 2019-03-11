package cn.idongjia.divine.lib.pojo.request.live;

import cn.idongjia.divine.lib.pojo.request.Page;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * @author lc
 * @create at 2018/7/25.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class LiveQry extends Page {

    /**
     * 直播id
     */
    @QueryParam("liveIds")
    private List<Long> liveIds;

    /**
     * 聊天室id
     */
    @QueryParam("zooId")
    private Long zooId;

    /**
     * 批量zooId
     */
    @QueryParam("zooIds")
    private List<Long> zooIds;

    /**
     * 直播标题
     */
    @QueryParam("title")
    private String title;

    @QueryParam("wildTitle")
    private String wildTitle;

    /**
     * 是否上线
     */
    @QueryParam("online")

    private Integer online;

    /**
     * 可以接受的直播开播状态列表
     */
    @QueryParam("states")
    private List<Integer> states;

    /**
     * 可以接受的直播上下线状态列表
     */
    @QueryParam("status")
    private List<Integer> status;

    @QueryParam("categoryIds")
    private List<Long> categoryIds;

    /**
     * 排除列表
     */
    @QueryParam("excludeLiveIds")
    private List<Long> excludeLiveIds;

    /**
     * 排除类目id
     */
    @QueryParam("excludeCategoryIds")
    private List<Long>    excludeCategoryIds;
    /**
     * 直播类型
     */
    @QueryParam("types")
    private List<Integer> types;

    /**
     * 主播id
     */
    @QueryParam("uid")
    private Long uid;

    /**
     * 最小创建时间
     */
    @QueryParam("minCreateTime")
    private Long minCreateTime;

    /**
     * 最大创建时间
     */
    @QueryParam("maxCreateTime")
    private Long maxCreateTime;

    /**
     * 最小预计开始时间
     */
    @QueryParam("minPreStartTime")
    private Long minPreStartTime;

    /**
     * 最大预计开始时间
     */
    @QueryParam("maxPreStartTime")
    private Long maxPreStartTime;

    /**
     * 最小预计结束时间
     */
    @QueryParam("minPreEndTime")
    private Long minPreEndTime;

    /**
     * 最大预计结束时间
     */
    @QueryParam("maxPreEndTime")
    private Long maxPreEndTime;

    /**
     * 最小开始时间
     */
    @QueryParam("minStartTime")
    private Long minStartTime;

    /**
     * 最大开始时间
     */
    @QueryParam("maxStartTime")
    private Long maxStartTime;

    /**
     * 最小结束时间
     */
    @QueryParam("minEndTime")
    private Long minEndTime;

    /**
     * 最大结束时间
     */
    @QueryParam("maxEndTime")
    private Long maxEndTime;

    /**
     * 最小权重
     */
    @QueryParam("minWeight")
    private Integer minWeight;

    /**
     * 是否有回放
     */
    @QueryParam("hasPlayback")
    private Integer hasPlayback;

    /**
     * 匠人名称
     */
    @QueryParam("craftsmanName")
    private String craftsmanName;

    /**
     * 显示位置-->0都不显示 1 app显示小程序不显示 2 小程序显示 app不显示 3 小程序和app都显示
     */
    @QueryParam("showLocation")
    private List<Integer> showLocations;

    /**
     * 直播拍的专场id
     */
    @QueryParam("sessionId")
    private Long sessionId;
}
