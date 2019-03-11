package cn.idongjia.divine.lib.pojo.request.auction;

import cn.idongjia.divine.lib.pojo.request.Page;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author lc
 * @create at 2018/7/25.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class AuctionStateQry extends Page {
    /**
     * 纯直播状态(-5修改中-4待审核-3审核中-2审核结束-1删除0未上线1已上线)
     */
    private List<Long> craftsmanIds;

}
