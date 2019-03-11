package cn.idongjia.divine.lib.pojo.response.live.general;

import cn.idongjia.divine.lib.pojo.response.ClientObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author lc
 * @create at 2018/7/27.
 */
@Getter
@Setter
@ToString
public class GeneralLiveCO extends ClientObject {
    /**
     * id
     */
    private Long         id;
    /**
     * 专场id
     */
    private Long         sessionId;
    /**
     * 头像
     */
    private String       avatar;
    /**
     * 名称
     */
    private String       name;
    /**
     * 标题
     */
    private String       title;
    /**
     * 纯直播状态(-5修改中-4待审核-3审核中-2审核结束-1删除0未上线1已上线)
     */
    private Integer      status;
    /**
     * 开始时间
     */
    private Long         startTime;
    /**
     * 结束时间
     */
    private Long         endTime;
    /**
     * uv数据
     */
    private Integer      uv;
    /**
     * 权重
     */
    private Integer      generalWeight;
    /**
     * 封面图地址
     */
    private String       pic;
    /**
     * 短视频id
     */
    private Long         videoCoverId;
    /**
     * 短视频地址
     */
    private String       videoCoverUrl;
    /**
     * 短视频时长
     */
    private Integer      videoCoverDuration;
    /**
     * 短视频默认图片
     */
    private String       videoCoverPic;
    /**
     * 匠人城市
     */
    private String       craftsmanCity;
    /**
     * e
     * 匠人名称
     */
    private String       craftsmanName;
    /**
     * 匠人头衔
     */
    private String       craftsmanTitle;
    /**
     * 直播类型
     */
    /**
     * 类型
     */
    private Integer      type;
    /**
     * 更新时间
     */
    private Long         updateTime;
    /**
     * 直播进程1未开始2已开始3已结束
     */
    private Integer      state;
    /**
     * 主播id
     */
    private Long         uid;
    /**
     * 直播预计开始时间
     */
    private Long         preStartTime;
    /**
     * 直播预计结束时间
     */
    private Long         preEndTime;
    /**
     * 直播创建时间
     */
    private Long         createTime;
    /**
     * 预展时间
     */
    private Long         previewTime;
    /**
     * 上下线
     */
    private Integer      online;
    /**
     * 首页热门权重
     */
    private Integer      recommendWeight;
    /**
     * 聊天室基准随机数
     */
    private Integer      zrc;
    /**
     * 关联商品
     */
    private List<ItemCO> items;
    /**
     * 关联资源数量
     */
    private Integer      resourceCount;
    /**
     * 聊天室id
     */
    private Long         zid;
    private Long         suid;

    /**
     * 聊天室id
     */
    private Long         roomId;
    /**
     * 屏幕方向
     */
    private Integer      screenDirection;
}
