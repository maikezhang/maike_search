package cn.idongjia.divine.mq.kafka.disruptor.event;

import cn.idongjia.divine.db.mybatis.pojo.AuctionBookDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveUserStageRelDO;
import cn.idongjia.divine.utils.disruptor.ValueWrapper;
import cn.idongjia.kafka.message.body.MysqlMessage;

/**
 * @author lc
 * @create at 2018/8/16.
 */
public class UserStageRelEvent extends ValueWrapper<MysqlMessage<LiveUserStageRelDO>> {
}
