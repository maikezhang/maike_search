package cn.idongjia.divine.db.mybatis.query;

import cn.idongjia.common.pagination.Pagination;
import cn.idongjia.common.query.BaseSearch;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import javax.ws.rs.QueryParam;
import java.sql.Timestamp;
import java.util.List;

/**
 * 描述：拍卖人工推荐数据 Database Object
 *
 * @author lc
 * @date 2018/08/16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class AuctionManualRecommendQuery extends BaseSearch {

    /**
     *
     */
    private Long id;

    /**
     * 拍品id
     */
    private Long itemId;

    /**
     * 权重
     */
    private Integer weight;

    /**
     * 0=否，1=是
     */
    private Integer isDelete;

    /**
     * 推荐用户类别；1=新用户，2=老用户
     */
    private Integer userType;

    /**
     *
     */
    private Long createTime;

    /**
     *
     */
    private Long updateTime;
    /**
     * 拍品id列表
     */
    private List<Long> itemIds;

    @Builder
    public AuctionManualRecommendQuery(String logPid,String orderBy,Integer limit,Integer page,Long start,Long end,Timestamp beginTime,Timestamp endTime,String order,Boolean needCount,Integer offset,Pagination pagination,Long id,Long itemId,Integer weight,Integer isDelete,Integer userType,Long createTime,Long updateTime,List<Long> itemIds) {
        super(orderBy,limit,page,start,end,beginTime,endTime,order,needCount,logPid,offset,pagination);
        this.id = id;
        this.itemId = itemId;
        this.weight = weight;
        this.isDelete = isDelete;
        this.userType = userType;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.itemIds = itemIds;
    }
}
