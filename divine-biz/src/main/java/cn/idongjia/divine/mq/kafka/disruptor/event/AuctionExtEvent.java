package cn.idongjia.divine.mq.kafka.disruptor.event;

import cn.idongjia.divine.db.mybatis.pojo.AuctionExtDO;
import cn.idongjia.divine.utils.disruptor.ValueWrapper;
import cn.idongjia.kafka.message.body.MysqlMessage;

/**
 * @author yuexiaodong@idongjia.cn
 * @create at 2018/12/18.
 */
public class AuctionExtEvent extends ValueWrapper<MysqlMessage<AuctionExtDO>> {
}
