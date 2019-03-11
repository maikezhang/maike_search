package cn.idongjia.divine.impl;

import cn.idongjia.divine.biz.ZooMessageLoadBO;
import cn.idongjia.divine.lib.service.ZooLoadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/11/22
 * Time: 下午2:47
 */
@Service("zooLoadService")
public class ZooLoadServiceImpl implements ZooLoadService {

    @Resource
    private ZooMessageLoadBO zooMessageBO;




    @Override
    public Integer loadZooMessage() {
        return zooMessageBO.loadZooMessage();
    }

    @Override
    public boolean loadByZooMessageId(Long zooMessageId) {
        return zooMessageBO.loadByZmid(zooMessageId);
    }
}
