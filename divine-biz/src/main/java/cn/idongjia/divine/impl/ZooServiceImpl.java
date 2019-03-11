package cn.idongjia.divine.impl;

import cn.idongjia.divine.biz.ZooMessageQueryBO;
import cn.idongjia.divine.lib.pojo.request.zoo.ZooMessageQry;
import cn.idongjia.divine.lib.pojo.response.MultiResponse;
import cn.idongjia.divine.lib.pojo.response.zoo.ZooMessageCO;
import cn.idongjia.divine.lib.service.ZooService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/11/22
 * Time: 下午2:45
 */
@Service("zooService")
public class ZooServiceImpl implements ZooService {

    @Resource
    private ZooMessageQueryBO zooMessageQueryBO;

    @Override
    public MultiResponse<ZooMessageCO> search(ZooMessageQry zooMessageQry) {
        return zooMessageQueryBO.query(zooMessageQry);
    }
}
