package cn.idongjia.divine.db.mybatis.query;

import cn.idongjia.common.query.BaseSearch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 描述：匠人类目授权表 Database Object
 *
 * @author lc
 * @date 2018/08/15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CraftsmanCategoryAuthQuery extends BaseSearch {

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 匠人id
     */
    private Integer craftsmanId;

    /**
     * 类目编号
     */
    private Integer categoryId;

    /**
     * 1 可用 0 删除
     */
    private Integer status;

    /**
     * 创建时间
     */
    private java.util.Date createTime;

    /**
     * 更新时间
     */
    private java.util.Date updateTime;

}
