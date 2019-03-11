package cn.idongjia.divine.lib.pojo.response.live.special;

import cn.idongjia.divine.lib.pojo.response.ClientObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lc
 * @create at 2018/9/3.
 */
@Getter
@Setter
@ToString
public class LiveSpecialCraftsmanCO extends ClientObject {
    /**
     * 匠人id
     */
    private Long    craftsmanUserId;
    /**
     * 匠人名称
     */
    private String  craftsmanName;
    /**
     * 匠人头像
     */
    private String  craftsmanAvatar;
    /**
     * 创建时间
     */
    private Long    createTime;
    /**
     * 显示类型
     */
    private Integer showType;
}
