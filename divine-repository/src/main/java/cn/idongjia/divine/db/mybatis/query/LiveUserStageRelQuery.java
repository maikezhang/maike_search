package cn.idongjia.divine.db.mybatis.query;

import cn.idongjia.common.query.BaseSearch;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import javax.ws.rs.QueryParam;

/**
 * 描述： Database Object
 *
 * @author lc
 * @date 2018/08/15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveUserStageRelQuery extends BaseSearch {

    /**
     * 主键
     */
    private Long id;

    /**
     * stage类型 0新用户 1老用户

     */
    private Integer stage;

    /**
     * 直播id
     */
    private Long liveId;

    /**
     * 0 不显示 1显示

     */
    private Integer showStatus;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 0已失效(直播已结束) 1生效中
     */
    private Integer status;

    /**
     * 权重
     */
    private Integer weight;

}
