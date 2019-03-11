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
 * 描述：拍卖人工推荐数据 Database Object
 *
 * @author lc
 * @date 2018/08/16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuctionManualRecommendDO extends Base {

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

}
