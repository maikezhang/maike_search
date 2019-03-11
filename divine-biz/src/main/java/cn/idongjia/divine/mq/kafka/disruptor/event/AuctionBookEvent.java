package cn.idongjia.divine.mq.kafka.disruptor.event;

import cn.idongjia.divine.db.mybatis.pojo.AuctionBookDO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionRelDO;
import cn.idongjia.divine.utils.disruptor.ValueWrapper;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Setter
@Getter
public class AuctionBookEvent extends ValueWrapper<MysqlMessage<AuctionBookDO>> {

}
