package cn.idongjia.divine.impl;

import cn.idongjia.divine.biz.AuctionLoadBO;
import cn.idongjia.divine.lib.service.AuctionLoadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/17.
 */
@Service("auctionLoadService")
public class AuctionLoadServiceImpl implements AuctionLoadService {

    @Resource
    private AuctionLoadBO auctionItemLoadBO;


    /**
     * 全量更新缓存
     * 通用
     *
     * @return
     */
    @Override
    public Integer load() {
        return auctionItemLoadBO.load();
    }

    /**
     * 按id更新缓存
     * 通用
     *
     * @param itemId
     * @return
     */
    @Override
    public boolean loadByItemId(Long itemId) {
        return auctionItemLoadBO.loadByItemId(itemId);
    }
}
