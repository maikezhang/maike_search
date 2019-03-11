package cn.idongjia.divine.impl;

import cn.idongjia.divine.biz.AuctionStateLoadBO;
import cn.idongjia.divine.lib.service.AuctionStateLoadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/9/4.
 */
@Service("auctionStateLoadService")
public class AuctionStateLoadServiceImpl implements AuctionStateLoadService {
    @Resource
    private AuctionStateLoadBO auctionStateLoadBO;

    /**
     * 全量更新缓存
     * 通用
     *
     * @return
     */
    @Override
    public Integer load() {
        return auctionStateLoadBO.load();
    }

    /**
     * 按id更新缓存
     * 通用
     *
     * @param customerId
     * @return
     */
    @Override
    public boolean loadByCustomerId(Long customerId) {
        return auctionStateLoadBO.loadById(customerId);
    }
}
