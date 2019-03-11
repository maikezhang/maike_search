package cn.idongjia.divine.impl;

import cn.idongjia.divine.biz.SessionLiveLoadBO;
import cn.idongjia.divine.lib.service.SessionLiveLoadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yuexiaodong@idongjia.cn
 * @date 2018-11-30
 */
@Service
public class SessionLiveLoadServiceImpl implements SessionLiveLoadService {

    @Resource
    private SessionLiveLoadBO sessionLiveLoadBO;

    @Override
    public Integer load() {
        return sessionLiveLoadBO.load();
    }

    @Override
    public boolean loadById(Long sessionId) {
        return sessionLiveLoadBO.loadById(sessionId);
    }

}
