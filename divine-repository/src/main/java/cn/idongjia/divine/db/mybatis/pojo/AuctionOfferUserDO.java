package cn.idongjia.divine.db.mybatis.pojo;

import cn.idongjia.common.base.Base;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lc
 * @create at 2018/8/16.
 */
@Getter
@Setter
public class AuctionOfferUserDO extends Base {
    private Long    userId;
    private Long    price;
    private Long    itemId;
    private Integer offerTimes;
}
