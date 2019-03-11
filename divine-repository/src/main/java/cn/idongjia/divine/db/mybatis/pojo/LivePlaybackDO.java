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
 * 描述：回放表 Database Object
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
public class LivePlaybackDO extends Base {

    /**
     * 唯一ID
     */
    private Long id;

    /**
     * 对应直播ID
     */
    private Long lid;

    /**
     * 腾讯回放视频地址
     */
    private String url;

    /**
     * 回放时长
     */
    private Long duration;

    /**
     * 回放状态-1删除0正常
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
