package cn.idongjia.divine.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yuexiaodong@idongjia.cn
 * @date 2018-11-30
 */
@Setter
@Getter
public class SessionLiveDTO extends BaseDTO {

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
     * 直播拍的预计开拍时间
     */
    private Long    planStartTime;
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
}
