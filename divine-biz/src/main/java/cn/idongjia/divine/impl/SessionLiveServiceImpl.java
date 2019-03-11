package cn.idongjia.divine.impl;

import cn.idongjia.divine.biz.SessionLiveBO;
import cn.idongjia.divine.lib.pojo.request.session.SessionLiveQry;
import cn.idongjia.divine.lib.pojo.response.MultiResponse;
import cn.idongjia.divine.lib.pojo.response.session.SessionLiveCO;
import cn.idongjia.divine.lib.service.SessionLiveService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yuexiaodong@idongjia.cn
 * @date 2018-12-03
 */
@Service
public class SessionLiveServiceImpl implements SessionLiveService {

    @Resource
    private SessionLiveBO sessionLiveBO;

    @Override
    public MultiResponse<SessionLiveCO> search(SessionLiveQry qry) {
        return sessionLiveBO.query(qry);
    }
}
