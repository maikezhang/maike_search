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
 * 描述：视频图片表 Database Object
 *
 * @author lc
 * @date 2018/08/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MediaDO extends Base {

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

}
