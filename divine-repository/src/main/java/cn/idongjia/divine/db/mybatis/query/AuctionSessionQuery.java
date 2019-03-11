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
public class AuctionSessionQuery extends BaseSearch {

    /**
     * 拍卖专场id
     */
    private Integer asid;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片
     */
    private String pic;

    /**
     * 序列化内容字段
     */
    private String data;

    /**
     * -1 已删除 0 待审核 1-修改中 3 审核中 4 已拒绝 5 已审核 2 已上线
     */
    private Integer status;

    /**
     * 1 未开始2 拍卖中 3 已结束 4结拍中
     */
    private Integer state;

    /**
     * 权重
     */
    private Integer weight;

    /**
     * 创建时间
     */
    private Integer timestamp;

    /**
     * 拍卖数量
     */
    private Integer count;

    /**
     * 是否热门及权重，-1非热门，大于-1均为热门，正数为权重
     */
    private Integer hot;

    /**
     * 预计结束时间
     */
    private Integer planetime;

    /**
     * 拍场类型:0-普通拍场,1-0元拍,2-直播拍,3-晚间专场,4-线上线下同步拍
     */
    private Integer type;

    /**
     * 预计开拍时间,定时开拍用
     */
    private Integer planatime;

    /**
     * 定时开拍:0-关闭,1-开启
     */
    private Integer timestart;

    /**
     * 定时结拍:0-关闭,1-开启
     */
    private Integer timeend;

    /**
     * 专场创建人uid
     */
    private Integer uid;

    /**
     * 0-匠人 1-管理员
     */
    private Integer utype;

    /**
     * 备选开拍时间
     */
    private String bakatime;

    /**
     * 备选结拍时间
     */
    private String baketime;

    /**
     * 关联直播场ID
     */
    private Integer lsid;

    /**
     * 顶部描述
     */
    private String topdesc;

    /**
     * 当专场类型是直播场时，用于记录开播和开拍的时间间隔
     */
    private Integer ainterval;

    /**
     * 直播场是否预展
     */
    private Integer preview;

    /**
     * 保证金
     */
    private Integer deposit;

    /**
     * 是否为新用户专享；0=否，1=是
     */
    private Integer forNewUser;

    /**
     * 拍卖场次，对应kp_auction_ground.gid
     */
    private Long gid;

    /**
     * 直播id
     */
    private List<Long> liveIds;
    /**
     * 专场id
     */
    private List<Long> sessionIds;

    private List<Integer> statusArray;


    @Builder
    public AuctionSessionQuery(String logPid,String orderBy,Integer limit,Integer page,Long start,Long end,Timestamp beginTime,Timestamp endTime,String order,Boolean needCount,Integer offset,Pagination pagination,Integer asid,String title,String pic,String data,Integer status,Integer state,Integer weight,Integer timestamp,Integer count,Integer hot,Integer planetime,Integer type,Integer planatime,Integer timestart,Integer timeend,Integer uid,Integer utype,String bakatime,String baketime,Integer lsid,String topdesc,Integer ainterval,Integer preview,Integer deposit,Integer forNewUser,Long gid,List<Long> liveIds,List<Long> sessionIds,List<Integer> statusArray) {
        super(orderBy,limit,page,start,end,beginTime,endTime,order,needCount,logPid,offset,pagination);
        this.asid = asid;
        this.title = title;
        this.pic = pic;
        this.data = data;
        this.status = status;
        this.state = state;
        this.weight = weight;
        this.timestamp = timestamp;
        this.count = count;
        this.hot = hot;
        this.planetime = planetime;
        this.type = type;
        this.planatime = planatime;
        this.timestart = timestart;
        this.timeend = timeend;
        this.uid = uid;
        this.utype = utype;
        this.bakatime = bakatime;
        this.baketime = baketime;
        this.lsid = lsid;
        this.topdesc = topdesc;
        this.ainterval = ainterval;
        this.preview = preview;
        this.deposit = deposit;
        this.forNewUser = forNewUser;
        this.gid = gid;
        this.liveIds = liveIds;
        this.sessionIds = sessionIds;
        this.statusArray=statusArray;
    }

}
