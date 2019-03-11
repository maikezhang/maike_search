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
 * 描述： Database Object
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
public class LivePureDO extends Base {

    /**
     * 纯直播ID 与live_show共享主键
     */
    private Long id;

    /**
     * 纯直播图片
     */
    private String pic;

    /**
     * 纯直播描述
     */
    private String desc;

    /**
     * 纯直播权重
     */
    private Integer weight;

    /**
     * 纯直播状态(-5修改中-4待审核-3审核中-2审核结束-1删除0未上线1已上线)
     */
    private Integer status;

    /**
     * 直播播出时间策略ID
     */
    private Long timeStrategy;

    /**
     * 纯直播是否免审0免审1未免
     */
    private Integer exemption;

}
