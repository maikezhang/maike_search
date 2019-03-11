package cn.idongjia.divine.db.mybatis.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import cn.idongjia.common.base.Base;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 描述：只包含加精数据，未加精的会被删除 Database Object
 *
 * @author lc
 * @date 2018/07/31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TabRecommendDO extends Base {

    /**
     * 
     */
    private Long rid;

    /**
     * tab id
     */
    private Long tid;

    /**
     * 类型：1分享、2商品、3运营管理、4商品模板
     */
    private Integer type;

    /**
     * 针对不同类型唯一id
     */
    private Long id;

    /**
     * 权重：0-5000，5001用于置顶
     */
    private Integer weight;

    /**
     * 创建时间
     */
    private Long createtm;

    /**
     * 对应商品或分享的发布人uid，如果是运营块，可以为空
     */
    private Long uid;

    /**
     * 是否定时加精操作
     */
    private Integer timer;

    /**
     * 
     */
    private Integer clear;

}
