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
public class AuctionStateCO extends ClientObject {
    /**
     * 专场封面
     */
    private Long    craftsmanId;
    /**
     * 匠人名称
     */
    private String  craftsmanName;
    /**
     * 匠人头像
     */
    private String  craftsmanAvatar;
    /**
     * 匠人头衔
     */
    private String  craftsmanTitle;
    /**
     * 已开始数量
     */
    private Integer started;

    /**
     * 未开始数量
     */
    private Integer unstart;

    /**
     * 已结束数量
     */
    private Integer ended;
}
