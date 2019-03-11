package cn.idongjia.divine.lib.pojo.response.session;

import cn.idongjia.divine.lib.pojo.response.ClientObject;
import cn.idongjia.se.lib.engine.annotations.EsField;
import cn.idongjia.se.lib.engine.annotations.EsFieldType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yuexiaodong@idongjia.cn
 * @date 2018-11-30
 */
@Getter
@Setter
@ToString
public class SessionLiveCO extends ClientObject {
    /**
     * 专场id
     */
    private Long    sessionId;
    /**
     * 直播id
     */
    private Long    liveId;
    /**
     * 直播进程1未开始2已开始3已结束
     */
    private Integer liveState;
    /**
     * 直播预计开始时间
     */
    private Long    livePreStarTime;
    /**
     * 直播预展时间
     */
    private Long    livePreViewTime;
    /**
     * 直播拍的预计开拍时间
     */
    private Long    planStartTime;
    /**
     * 直播匠人userId
     */
    private Long    liveCraftsmanId;
    /**
     * 直播匠人头像
     */
    private String  liveCraftsmanTitle;
    /**
     * 直播匠人头像
     */
    private String  liveCraftsmanAvatar;
    /**
     * 直播匠人名字
     */
    private String  liveCraftsmanName;
    /**
     * 直播拍预计结拍时间
     */
    private Long    planEndTime;
    /**
     * 专场进程
     */
    private Integer sessionState;
    /**
     * 专场权重
     */
    private Integer sessionWeight;
    /**
     * 专场预展
     */
    private Integer sessionPreview;
    /**
     * 专场状态
     */
    private Integer sessionStatus;
    /**
     * 专场封面
     */
    private String  sessionPic;
    /**
     * 专场标题
     */
    private String  sessionTitle;
}
