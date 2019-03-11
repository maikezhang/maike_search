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
 * 描述：拍品出价内容表 Database Object
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
public class AuctionOfferDO extends Base {

    /**
     * 及时会话消息id
     */
    private Long zmid;

    /**
     * 拍品id
     */
    private Long iid;

    /**
     * 产生消息的用户 uid
     */
    private Long uid;

    /**
     * 出价价格
     */
    private java.math.BigDecimal price;

    /**
     * 状态：0成功,1失败,2无效,-1删除
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Long createtm;

    /**
     * 消息来源，0=其他，1=APP，2=H5
     */
    private Integer origin;

}
