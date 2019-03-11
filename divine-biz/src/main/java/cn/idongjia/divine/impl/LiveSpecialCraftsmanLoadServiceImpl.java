package cn.idongjia.divine.impl;

import cn.idongjia.divine.biz.LiveSpecialCraftsmanLoadBO;
import cn.idongjia.divine.lib.service.LiveSpecialCraftsmanLoadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/9/3.
 */
@Service("liveSpecialCraftsmanLoadService")
public class LiveSpecialCraftsmanLoadServiceImpl implements LiveSpecialCraftsmanLoadService {

    @Resource
    private LiveSpecialCraftsmanLoadBO liveSpecialCraftsmanLoadBO;
    /**
     * 全量更新缓存
     * 通用
     *
     * @return
     */
    @Override
    public Integer load() {
        return liveSpecialCraftsmanLoadBO.load();
    }

    /**
     * 按id更新缓存
     * 通用
     *
     * @param craftsmanId
     * @return
     */
    @Override
    public boolean loadByCraftsmanId(Long craftsmanId) {
        return liveSpecialCraftsmanLoadBO.loadById(craftsmanId);
    }
}
