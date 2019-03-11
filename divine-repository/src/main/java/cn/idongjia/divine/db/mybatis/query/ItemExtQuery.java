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
 * 描述：拍品扩展表 Database Object
 *
 * @author lc
 * @date 2018/08/16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ItemExtQuery extends BaseSearch {

    /**
     * 商品编号
     */
    private Long iid;

    /**
     * 匠人uid
     */
    private Long cuid;

    /**
     * 尺寸
     */
    private String size;

    /**
     * 规格
     */
    private String standard;

    /**
     * 材质
     */
    private String material;

    /**
     * 年代
     */
    private String age;

    /**
     * 作者
     */
    private String author;

    /**
     * 工艺
     */
    private String craft;

    /**
     * 瑕疵
     */
    private String flaw;

    /**
     * 状态:0-已作废,1-待审核,2-修改中,3-已回退,4-审核中,5-已通过审核,6-已可用
     */
    private Integer status;

    /**
     * 16:9封面图
     */
    private String coverrect;

    /**
     * 商品来源:0-管理员,1-匠人
     */
    private Integer source;


    /**
     * 产地
     */
    private String nativee;

    /**
     * 0-普通方式,1-拍卖,2-搭伙,3-众筹
     */
    private Integer saletype;
    /**
     * 商品id列表
     */
    private List<Long> itemIds;


    @Builder
    public ItemExtQuery(String logPid,String orderBy,Integer limit,Integer page,Long start,Long end,Timestamp beginTime,Timestamp endTime,String order,Boolean needCount,Integer offset,Pagination pagination,Long iid,Long cuid,String size,String standard,String material,String age,String author,String craft,String flaw,Integer status,String coverrect,Integer source,String nativee,Integer saletype,List<Long> itemIds) {
        super(orderBy,limit,page,start,end,beginTime,endTime,order,needCount,logPid,offset,pagination);
        this.iid = iid;
        this.cuid = cuid;
        this.size = size;
        this.standard = standard;
        this.material = material;
        this.age = age;
        this.author = author;
        this.craft = craft;
        this.flaw = flaw;
        this.status = status;
        this.coverrect = coverrect;
        this.source = source;
        this.nativee = nativee;
        this.saletype = saletype;
        this.itemIds = itemIds;
    }
}
