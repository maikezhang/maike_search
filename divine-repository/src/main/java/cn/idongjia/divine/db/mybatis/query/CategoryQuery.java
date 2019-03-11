package cn.idongjia.divine.db.mybatis.query;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import cn.idongjia.common.pagination.Pagination;
import cn.idongjia.common.query.BaseSearch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 描述：后台真实类目表 Database Object
 *
 * @author lc
 * @date 2018/08/15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CategoryQuery extends BaseSearch {

    /**
     * 类目编号
     */
    private Integer id;

    /**
     * 类目名称
     */
    private String name;

    /**
     * 是否为非叶子节点：1为非叶子节点，0：叶子节点
     */
    private Integer node;

    /**
     * 所属层级
     */
    private Integer level;

    /**
     * 类目路径，每一级类目包含上面N级父类目
     */
    private String path;

    /**
     * 父类目ID
     */
    private Integer parentId;

    /**
     * 权重
     */
    private Integer weight;

    /**
     * 是否特殊属性，1白名单，0普通
     */
    private Integer special;

    /**
     * 1 上架 0 下架
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 上架时间
     */
    private java.util.Date shelfTime;

    @Builder
    public CategoryQuery(String logPid,String orderBy,Integer limit,Integer page,Long start,Long end,Timestamp beginTime,Timestamp endTime,String order,Boolean needCount,Integer offset,Pagination pagination,Integer id,String name,Integer node,Integer level,String path,Integer parentId,Integer weight,Integer special,Integer status,Long createTime,Long updateTime,Date shelfTime,List<Long> categoryIds) {
        super(orderBy,limit,page,start,end,beginTime,endTime,order,needCount,logPid,offset,pagination);
        this.id = id;
        this.name = name;
        this.node = node;
        this.level = level;
        this.path = path;
        this.parentId = parentId;
        this.weight = weight;
        this.special = special;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.shelfTime = shelfTime;
        this.categoryIds = categoryIds;
    }

    /**
     * 类目id列表
     */
    private List<Long>     categoryIds;


}
