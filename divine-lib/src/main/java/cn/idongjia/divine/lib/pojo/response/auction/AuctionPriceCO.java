package cn.idongjia.divine.lib.pojo.response.auction;

import cn.idongjia.divine.lib.pojo.response.ClientObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author lc
 * @create at 2018/9/4.
 */
@Getter
@Setter
@ToString
public class AuctionPriceCO  extends ClientObject {
    private Long            id;
    private List<AuctionCO> auctionCOS;
}
