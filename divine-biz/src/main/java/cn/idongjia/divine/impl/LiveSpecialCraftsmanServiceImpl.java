package cn.idongjia.divine.impl;

import cn.idongjia.divine.biz.LiveSpecialCraftsmanQueryBO;
import cn.idongjia.divine.lib.pojo.request.live.CraftsmanQry;
import cn.idongjia.divine.lib.pojo.response.MultiResponse;
import cn.idongjia.divine.lib.pojo.response.live.special.LiveSpecialCraftsmanCO;
import cn.idongjia.divine.lib.service.LiveSpecialCraftsmanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/9/3.
 */
@Service("liveSpecialCraftsmanService")
public class LiveSpecialCraftsmanServiceImpl implements LiveSpecialCraftsmanService {
    @Resource
    private LiveSpecialCraftsmanQueryBO liveSpecialCraftsmanQueryBO;

    @Override
    public MultiResponse<LiveSpecialCraftsmanCO> list(CraftsmanQry craftsmanQry) {
        return liveSpecialCraftsmanQueryBO.query(craftsmanQry);
    }
}
