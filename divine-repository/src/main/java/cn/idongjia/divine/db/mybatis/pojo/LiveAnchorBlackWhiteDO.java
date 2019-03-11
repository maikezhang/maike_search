package cn.idongjia.divine.db.mybatis.pojo;

import cn.idongjia.common.base.Base;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * 描述：直播主播黑白名单表 Database Object
 *
 * @author lc,longchuan@idongjia.cn
 * @date 2018/09/01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LiveAnchorBlackWhiteDO extends Base {

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 主播id
     */
    private Long anchorId;

    /**
     * 0都不显示 1 app显示小程序不显示 2 小程序显示 app不显示 3 小程序和app都显示
     * kaipao.live_anchor_black_white 表的 type
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;

}
