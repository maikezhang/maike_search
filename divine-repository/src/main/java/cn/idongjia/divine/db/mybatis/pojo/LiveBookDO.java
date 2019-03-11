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
 * 描述： Database Object
 *
 * @author lc
 * @date 2018/08/10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LiveBookDO extends Base {

    /**
     * 唯一ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long uid;

    /**
     * 直播ID
     */
    private Long lid;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    @JsonDeserialize(using = DateDeserializer.class)
    private java.util.Date createtm;

    /**
     * 最后修改时间
     */
    @JsonDeserialize(using = DateDeserializer.class)
    private java.util.Date modifiedtm;

}
