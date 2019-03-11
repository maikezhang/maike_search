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
 * 描述：拍卖订阅表 Database Object
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
public class AuctionBookDO extends Base {

    /**
     * 唯一ID
     */
    private Long id;

    /**
     * 订阅用户ID
     */
    private Long userId;

    /**
     * 订阅内容ID
     */
    private Long contentId;

    /**
     * 订阅内容类型0、普通拍卖1、直播拍卖
     */
    private Integer contentType;

    /**
     * 订阅状态-1、删除0、正常
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 最后修改时间
     */
    private Long updateTime;

}
