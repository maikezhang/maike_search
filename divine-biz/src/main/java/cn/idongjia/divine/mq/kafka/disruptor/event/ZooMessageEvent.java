package cn.idongjia.divine.mq.kafka.disruptor.event;

import cn.idongjia.divine.db.mybatis.pojo.ZooMessageDO;
import cn.idongjia.divine.utils.disruptor.ValueWrapper;
import cn.idongjia.kafka.message.body.MysqlMessage;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/11/22
 * Time: 下午3:16
 */
public class ZooMessageEvent extends ValueWrapper<MysqlMessage<ZooMessageDO>> {
}
