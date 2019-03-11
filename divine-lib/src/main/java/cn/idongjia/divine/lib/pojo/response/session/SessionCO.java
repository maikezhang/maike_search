package cn.idongjia.divine.lib.pojo.response.session;

import cn.idongjia.divine.lib.pojo.response.ClientObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lc
 * @create at 2018/7/27.
 */
@Getter
@Setter
@ToString
public class SessionCO extends ClientObject {
    /**
     * 专场id
     */
    private Long    sessionId;
    /**
     * 专场标题
     */
    private String  title;
    /**
     * 纯直播状态(-5修改中-4待审核-3审核中-2审核结束-1删除0未上线1已上线)
     */
    private Integer status;
    /**
     * 直播进程1未开始2已开始3已结束
     */
    private Integer state;
    /**
     * 权重
     */
    private Integer weight;
    /**
     * 热门权重
     */
    private Integer hotWeight;
    /**
     * 专场类型
     */
    private Integer sessionType;
    /**
     * 预计开始时间
     */
    private Long    planStartTime;
    /**
     * 预计结束时间
     */
    private Long    planEndTime;
    /**
     * 创建人id
     */
    private Long    creatorId;
    /**
     * 短视频地址
     */
    private Integer creatorType;
    /**
     * 直播id
     */
    private Long    liveId;
    /**
     * 是否预展
     */
    private Integer preview;
    /**
     * 新用户专享
     */
    private Integer forNewUser;
    /**
     * 专场封面
     */
    private String  pic;
    /**
     * 匠人名称
     */
    private String  craftsmanName;
    /**
     * 匠人头像
     */
    private String  craftsmanAvatar;
    /**
     * 默认为0=非激活匠人专场，1=激活匠人专场
     */
    public  Integer djtFlag;
    /**
     * 匠人头衔
     */
    private String  craftsmanTitle;
    /**
     * 出价次数
     */
    private Integer offerTotal;
    /**
     * 关联拍品数量
     */
    private Integer relatedCount;
    /**
     * 保证金，分
     */
    private Long    deposit;
    /**
     * 当专场类型是直播场时，用于记录开播和开拍的时间间隔
     * 分钟
     */
    private Integer ainterval;
    /**
     * 创建时间
     */
    private Long    createTime;
}
