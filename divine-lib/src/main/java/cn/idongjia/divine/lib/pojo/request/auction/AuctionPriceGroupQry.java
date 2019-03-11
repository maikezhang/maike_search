package cn.idongjia.divine.lib.pojo.request.auction;

import cn.idongjia.common.base.Base;
import cn.idongjia.divine.lib.pojo.response.ClientObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.ws.rs.QueryParam;

/**
 * @author lc
 * @create at 2018/9/4.
 */
@Getter
@Setter
@ToString
public class AuctionPriceGroupQry extends ClientObject {

    @QueryParam("subLimit")
    private Integer subLimit;
    @QueryParam("firstAgg")
    private String firstAgg;
    @QueryParam("secondAgg")
    private String secondAgg;
}
