package cn.idongjia.divine.lib.pojo.response.auction;

import java.util.List;

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
public class SessionAuctionRel extends ClientObject {

    /**
     * 商品id
     */
    private Long                sessionId;
    /**
     * 拍品
     */
    private List<AuctionItemCO> auctionItemCOS;
}
