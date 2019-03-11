package cn.idongjia.divine.dto;

import cn.idongjia.divine.db.mybatis.pojo.CraftsmanCategoryRelDO;
import cn.idongjia.divine.db.mybatis.pojo.ItemRelPO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by zhang on 2017/2/19.
 * 直播关联专场查询结果
 */
@Setter
@Getter
public class LiveDTO extends BaseDTO {

    /**
     * 关联商品id
     */
    private List<ItemRelPO>              itemDOS;
    /**
     * 回复统计
     */
    private Boolean                      hasPlayBack;
    /**
     * 订阅人数
     */
    private Integer                      bookTotal;
    /**
     * 推荐权重
     */
    private Integer                      recommendWeight;
    /**
     * uv
     */
    private Integer                      uv;
    /**
     * 真实uv
     */
    private Integer                      realUV;
    /**
     * 聊天室信息
     */
    private Integer                      zrc;
    /**
     * id
     */
    private String                       id;
    /**
     * 用户类型
     */
    private Integer                      userType;
    /**
     * 匠人所属类目
     */
    private List<CraftsmanCategoryRelDO> categoryDOS;
    /**
     * 新老用户关联推荐权重
     */
    private Integer                      newUserWeight;
    private Integer                      oldUserWeight;
    private String                       pic;
    private String                       title;
    private String                       craftsmanTitle;
    private String                       craftsmanCity;
    private Long                         preEndTime;
    private Long                         preStartTime;
    private Integer                      screenDirection;
    private Long                         startTime;
    private Integer                      state;
    private Integer                      status;
    private Long                         userId;
    private Long                         updateTime;
    private Long                         videoId;
    private Long                         zooId;
    private Long                         previewTime;
    private Long                         createTime;
    private Long                         endTime;
    private Integer                      generalWeight;
    private Integer                      liveType;
    private Integer                      online;
    private Long                         roomId;
    private String                       videoURL;
    private String                       videoPic;
    private Long                         videoDuration;
    private Long                         sessionId;
    private String                       craftsmanName;
    private String                       craftsmanAvatar;
    private Integer                      showLocation;
    public  Long                         suid;
    /**
     * 累计发言次数
     */
    public  Integer                      totalMessageCount;

}
