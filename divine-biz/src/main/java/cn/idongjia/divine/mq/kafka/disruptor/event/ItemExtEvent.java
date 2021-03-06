package cn.idongjia.divine.mq.kafka.disruptor.event;

import cn.idongjia.divine.db.mybatis.pojo.ItemExtDO;
import cn.idongjia.divine.db.mybatis.pojo.ZooDO;
import cn.idongjia.divine.utils.disruptor.ValueWrapper;
import cn.idongjia.kafka.message.body.MysqlMessage;

/**
 * @author lc
 * @create at 2018/8/17.
 */
public class ItemExtEvent extends ValueWrapper<MysqlMessage<ItemExtDO>> {
}
