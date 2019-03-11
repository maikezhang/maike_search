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
 * 描述：基本直播信息表 Database Object
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
public class LiveShowDO extends Base {

    /**
     * 直播id
     */
    private Long id;

    /**
     * 直播名称
     */
    private String title;

    /**
     * 主播uid
     */
    private Integer uid;

    /**
     * 直播类型1. 纯直播 2. 拍卖直播
     */
    private Integer type;

    /**
     * 直播状态-1删除0正常
     */
    private Integer status;

    /**
     * 直播聊天室id
     */
    private Long zid;

    /**
     * 直播预展时间
     */
    @JsonDeserialize(using = DateDeserializer.class)
    private java.util.Date previewtm;

    /**
     * 直播预计开始时间
     */
    @JsonDeserialize(using = DateDeserializer.class)
    private java.util.Date prestarttm;

    /**
     * 直播预计结束时间
     */
    @JsonDeserialize(using = DateDeserializer.class)
    private java.util.Date preendtm;

    /**
     * 直播开始时间
     */
    @JsonDeserialize(using = DateDeserializer.class)
    private java.util.Date starttm;

    /**
     * 直播结束时间
     */
    @JsonDeserialize(using = DateDeserializer.class)
    private java.util.Date endtm;

    /**
     * 直播间ID
     */
    private Long roomId;

    /**
     * 直播最后修改时间
     */
    @JsonDeserialize(using = DateDeserializer.class)
    private java.util.Date modifiedtm;

    /**
     * 直播创建时间
     */
    @JsonDeserialize(using = DateDeserializer.class)
    private java.util.Date createtm;

    /**
     * 直播进程1未开始2已开始3已结束
     */
    private Integer state;
    /**
     * 小视频id,对应 live_video id
     */
    private Long videoCoverId;

    /**
     * 所有直播的通用权重
     */
    private Integer generalWeight;

    /**
     * 直播的上下线状态，0=下线，1=上线
     */
    private Integer online;

    /**
     * 直播介绍
     */
    private String showDesc;

    /**
     * 屏幕方向0、横屏1、竖屏
     */
    private Integer screenDirection;

    /**
     * 是否自动上线0、否1、是
     */
    private Integer needAutoOnline;

}
