package cn.idongjia.divine.db.mybatis.query;

import cn.idongjia.common.query.BaseSearch;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.List;
import javax.ws.rs.QueryParam;

/**
 * 描述：直播主播黑白名单表 Database Object
 *
 * @author lc,longchuan@idongjia.cn
 * @date 2018/09/01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveAnchorBlackWhiteQuery extends BaseSearch {

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 主播id
     */
    private Long anchorId;

    /**
     * 1-黑名单 2-白名单(目前白名单只限制在小程序中)
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


    /**
     * 按照主键批量查询
     */
    private List<Long> idList;

    /**
     * 主播id
     */
    private List<Long> anchorIds;
}
