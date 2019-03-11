package cn.idongjia.divine.mq.kafka.disruptor.handler.auction.statistics;

import cn.idongjia.divine.biz.AuctionStateLoadBO;
import cn.idongjia.divine.db.mybatis.pojo.ItemDO;
import cn.idongjia.divine.db.mybatis.pojo.ItemExtDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.ItemEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.divine.repository.ItemExtRepositoryI;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * @author lc
 * @create at 2018/8/17.
 */
@Component
@Slf4j
public class AuctionStateItemHandler extends BaseEventHandler<ItemEvent> {

    @Resource
    private AuctionStateLoadBO auctionStateLoadBO;
    @Resource
    private ItemExtRepositoryI itemExtRepositoryI;

    @Override
    public void onEvent(ItemEvent event) throws Exception {
        MysqlMessage<ItemDO> messageBody = event.getValue();
        String               type        = messageBody.getType();
        ItemDO               itemDO      = messageBody.getData();
        switch(type) {
            case UPDATE:
                Map<Long,ItemExtDO> itemExtDOMap = itemExtRepositoryI.mapByItemIds(Arrays.asList(itemDO.getIid()));
                if(Utils.isNotEmpty(itemExtDOMap)) {
                    itemExtDOMap.values().stream()
                            .forEach(itemExtDO -> auctionStateLoadBO.loadById(
                                    itemExtDO.getCuid(), itemExtDO.getIid()));
                }

                break;
            default: break;

        }
    }
}
