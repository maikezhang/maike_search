package cn.idongjia.divine.convertor;

import cn.idongjia.divine.db.es.entity.ZooMessageEntity;
import cn.idongjia.divine.lib.pojo.response.zoo.ZooMessageCO;
import org.springframework.stereotype.Component;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/11/23
 * Time: 下午3:08
 */
@Component
public class ZooMessageConvertor implements ConvertorI<ZooMessageCO,ZooMessageEntity> {

    @Override
    public ZooMessageCO dataToClient(ZooMessageEntity entity) {
        ZooMessageCO zooMessageCO=new ZooMessageCO();

        zooMessageCO.setContent(entity.getContent());
        zooMessageCO.setCreateTime(entity.getCreateTime());
        zooMessageCO.setStatus(entity.getStatus());
        zooMessageCO.setType(entity.getType());
        zooMessageCO.setUserId(entity.getUserId());
        zooMessageCO.setZooId(entity.getZooId());
        zooMessageCO.setZooMessageId(entity.getZooMessageId());

        return zooMessageCO;
    }
}
