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
 * 描述：视频图片表 Database Object
 *
 * @author lc
 * @date 2018/08/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaQuery extends BaseSearch {

    /**
     * 主键
     */
    private Long id;

    /**
     * 来源id
     */
    private Long sourceId;

    /**
     * 类型：1-封面图， 2-详情图 3-视频 4-视频封面
     */
    private Integer mediaType;

    /**
     * 来源：1-商品 2-评价
     */
    private Integer sourceType;

    /**
     * 图片/视频地址
     */
    private String mediaUrl;

    /**
     * 权重
     */
    private Integer weight;

    /**
     * 0-删除 1-正常
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     *
     */
    private Integer height;

    /**
     *
     */
    private Integer width;


    /**
     * 按照主键批量查询
     */
    private List<Long> idList;

    private List<Long> resourceIds;
}
