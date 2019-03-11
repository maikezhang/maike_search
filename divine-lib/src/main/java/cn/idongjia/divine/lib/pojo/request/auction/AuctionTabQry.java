package cn.idongjia.divine.lib.pojo.request.auction;

import cn.idongjia.divine.lib.pojo.request.Page;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * @author lc
 * @create at 2018/7/25.
 */
@Getter
@Setter
public class AuctionTabQry extends Page {

    @QueryParam("categoryIds")
    private List<Long> categoryIds;
    @QueryParam("noCategoryIds")
    private List<Long> noCategoryIds;

}
