package cn.idongjia.divine.impl;

import cn.idongjia.divine.biz.SessionQueryBO;
import cn.idongjia.divine.lib.pojo.request.session.SessionQry;
import cn.idongjia.divine.lib.pojo.response.MultiResponse;
import cn.idongjia.divine.lib.pojo.response.session.SessionCO;
import cn.idongjia.divine.lib.service.SessionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/9/3.
 */
@Service("sessionService")
public class SessionServiceImpl implements SessionService {

    @Resource
    private SessionQueryBO sessionQueryBO;

    /**
     * 直播列表查询
     * 通用
     *
     * @param sessionQry
     * @return
     */
    @Override
    public MultiResponse<SessionCO> search(SessionQry sessionQry) {
        return sessionQueryBO.query(sessionQry);
    }
}
