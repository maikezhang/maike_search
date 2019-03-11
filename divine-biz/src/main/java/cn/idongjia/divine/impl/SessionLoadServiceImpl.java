package cn.idongjia.divine.impl;

import cn.idongjia.divine.biz.SessionLoadBO;
import cn.idongjia.divine.lib.service.SessionLoadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/9/3.
 */
@Service("sessionLoadService")
public class SessionLoadServiceImpl implements SessionLoadService {
    @Resource
    private SessionLoadBO sessionLoadBO;

    /**
     * 全量更新缓存
     * 通用
     *
     * @return
     */
    @Override
    public Integer load() {
        return sessionLoadBO.load();
    }

    /**
     * 按id更新缓存
     * 通用
     *
     * @param sessionId
     * @return
     */
    @Override
    public boolean loadById(Long sessionId) {
        return sessionLoadBO.loadById(sessionId);
    }
}
