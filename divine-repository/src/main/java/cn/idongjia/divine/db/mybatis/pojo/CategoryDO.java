package cn.idongjia.divine.db.mybatis.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import cn.idongjia.common.base.Base;
import cn.idongjia.divine.utils.DateDeserializer;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDO extends Base {

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
     **/
    @JsonDeserialize(using = DateDeserializer.class)
    private java.util.Date shelfTime;

}
