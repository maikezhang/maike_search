package cn.idongjia.divine.mq.kafka.disruptor.event;

import cn.idongjia.divine.db.mybatis.pojo.LivePureDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveShowDO;
import cn.idongjia.divine.utils.disruptor.ValueWrapper;
import cn.idongjia.kafka.message.body.MysqlMessage;

/**
 * @author lc
 * @create at 2018/8/15.
 */
public class LivePureEvent extends ValueWrapper<MysqlMessage<LivePureDO>> {
}
