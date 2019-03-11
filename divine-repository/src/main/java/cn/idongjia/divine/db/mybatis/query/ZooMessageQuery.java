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
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/11/22
 * Time: 下午5:47
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ZooMessageQuery extends BaseSearch {

    /**
     * messageid
     */
    private Long          zmid;
    /**
     *
     */
    private List<Long>    zmids;
    /**
     * zid
     */
    private Long          zid;
    /**
     *
     */
    private List<Long>    zids;
    /**
     * 用户id
     */
    private Long          uid;
    /**
     * 消息类型
     */
    private Integer       type;
    /**
     * 消息类型数组
     */
    private List<Integer> types;
    /**
     * 消息状态数组
     */
    private List<Integer> statusArray;
    /**
     * 消息状态
     */
    private Integer       status;
    /**
     * 创建时间
     */
    private Long          createtm;
    /**
     * 批量查询是使用 代替offset
     */
    private Long          zmidRange;

    @Builder
    public ZooMessageQuery(String orderBy, Integer limit, Integer page, Long start, Long end, Timestamp beginTime, Timestamp endTime, String order, Boolean needCount, Integer offset, Pagination pagination
            , Long zmid, List<Long> zmids, Long zid, List<Long> zids, Long uid, Integer type, Long createtm, List<Integer> types, Integer status, List<Integer> statusArray,Long zmidRange,String logPid) {
        super(orderBy, limit, page, start, end, beginTime, endTime, order, needCount,logPid, offset, pagination);
        this.zmid = zmid;
        this.zmids = zmids;
        this.zid = zid;
        this.zids = zids;
        this.uid = uid;
        this.type = type;
        this.types = types;
        this.status = status;
        this.statusArray = statusArray;
        this.createtm = createtm;
        this.zmidRange=zmidRange;
    }
}
