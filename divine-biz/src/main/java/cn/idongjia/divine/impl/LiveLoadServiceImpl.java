package cn.idongjia.divine.impl;

import cn.idongjia.divine.biz.LiveLoadBO;
import cn.idongjia.divine.lib.service.LiveLoadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/7/25.
 */
@Service("liveLoadService")
public class LiveLoadServiceImpl implements LiveLoadService {

    @Resource
    private LiveLoadBO liveLoadBO;

    /**
     * 全量更新缓存
     * 通用
     *
     * @return
     */
    @Override
    public Integer load() {
        return liveLoadBO.load();
    }

    /**
     * 按id更新缓存
     * 通用
     *
     * @return
     */
    @Override
    public boolean loadById(Long liveId) {
        return liveLoadBO.loadById(liveId);
    }
}
