package cn.idongjia.divine.impl;

import cn.idongjia.divine.biz.AuctionStateQueryBO;
import cn.idongjia.divine.lib.pojo.request.auction.AuctionStateQry;
import cn.idongjia.divine.lib.pojo.response.MultiResponse;
import cn.idongjia.divine.lib.pojo.response.session.AuctionStateCO;
import cn.idongjia.divine.lib.service.AuctionStateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/9/4.
 */
@Service("auctionStateService")
public class AuctionStateServiceImpl implements AuctionStateService {
    @Resource
    private AuctionStateQueryBO auctionStateQueryBO;
    @Override
    public MultiResponse<AuctionStateCO> search(AuctionStateQry auctionStateQry) {
        return auctionStateQueryBO.query(auctionStateQry);
    }
}
