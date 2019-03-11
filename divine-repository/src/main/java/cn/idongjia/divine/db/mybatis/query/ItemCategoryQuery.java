package cn.idongjia.divine.db.mybatis.query;

import cn.idongjia.common.query.BaseSearch;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import javax.ws.rs.QueryParam;

/**
 * 描述：类目表 Database Object
 *
 * @author lc
 * @date 2018/08/16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ItemCategoryQuery extends BaseSearch {

    /**
     * 商品类目编号
     */
    private Integer icid;

    /**
     * 类目名称
     */
    private String name;

    /**
     * 创建时间
     */
    private Integer createtm;

    /**
     * 父类目编号
     */
    private Integer parentid;

    /**
     * 权重
     */
    private Integer weight;

    /**
     * 0 新建 1 发布 -1 已删除
     */
    private Integer status;
    /**
     * 拍品id
     */

}
