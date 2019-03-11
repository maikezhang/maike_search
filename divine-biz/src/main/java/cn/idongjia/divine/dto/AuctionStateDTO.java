package cn.idongjia.divine.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lc
 * @create at 2018/9/3.
 */
@Getter
@Setter
public class AuctionStateDTO extends BaseDTO {

    /**
     * 专场封面
     */
    private Long  craftsmanId;
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
