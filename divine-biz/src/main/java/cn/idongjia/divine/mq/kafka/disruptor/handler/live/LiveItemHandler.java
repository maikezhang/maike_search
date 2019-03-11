package cn.idongjia.divine.mq.kafka.disruptor.handler.live;

import java.util.List;

import javax.annotation.Resource;

import cn.idongjia.divine.lib.pojo.Conf;
import org.springframework.stereotype.Component;

import cn.idongjia.divine.biz.LiveLoadBO;
import cn.idongjia.divine.db.mybatis.mapper.kaipao.LiveResourceMapper;
import cn.idongjia.divine.db.mybatis.pojo.ItemDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveResourceDO;
import cn.idongjia.divine.db.mybatis.query.LiveResourceQuery;
import cn.idongjia.divine.mq.kafka.disruptor.event.ItemEvent;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lc
 * @create at 2018/8/17.
 */
@Component
@Slf4j
public class LiveItemHandler extends BaseEventHandler<ItemEvent> {

	@Resource
	private LiveLoadBO		   liveLoadBO;
	@Resource
	private LiveResourceMapper liveResourceMapper;

	@Override
    public void onEvent(ItemEvent event) throws Exception {
        MysqlMessage<ItemDO> messageBody = event.getValue();
        String               type        = messageBody.getType();
        ItemDO               itemDO      = messageBody.getData();
        switch(type) {
            case UPDATE:
				List<LiveResourceDO> resourceDOS = liveResourceMapper.select(LiveResourceQuery.builder().resId(itemDO.getIid()).resType(Conf.LIVE_RESOURCE_TYPE_ITEM).build());
				if (Utils.isNotEmpty(resourceDOS)) {
					resourceDOS.stream().forEach(resource -> {
						liveLoadBO.loadById(resource.getLid());
					});
				}
                break;
            default: break;

        }
    }
}
