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
 * 描述：匠人类目授权表 Database Object
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
public class CraftsmanCategoryAuthDO extends Base {

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
	@JsonDeserialize(using = DateDeserializer.class)
    private java.util.Date createTime;

    /**
     * 更新时间
     */
	@JsonDeserialize(using = DateDeserializer.class)
    private java.util.Date updateTime;

}
