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
 * 描述：客户用户关系表 Database Object
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
public class CustomerUserRelDO extends Base {

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 状态: 1 正常 0 删除
     */
    private Integer status;

    /**
     * 创建时间
     */
    @JsonDeserialize(using = DateDeserializer.class)
    private java.util.Date createTime;

    /**
     * 修改时间
     */
    @JsonDeserialize(using = DateDeserializer.class)
    private java.util.Date updateTime;

}
