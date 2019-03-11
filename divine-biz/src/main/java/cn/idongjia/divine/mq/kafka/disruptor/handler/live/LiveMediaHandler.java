package cn.idongjia.divine.mq.kafka.disruptor.handler.live;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import cn.idongjia.divine.lib.pojo.Conf;
import org.springframework.stereotype.Component;

import cn.idongjia.divine.biz.LiveLoadBO;
import cn.idongjia.divine.db.mybatis.pojo.MediaDO;
import cn.idongjia.divine.mq.kafka.disruptor.event.MediaEvent;
import cn.idongjia.divine.repository.LiveItemCountRepositoryI;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
@Slf4j
public class LiveMediaHandler extends BaseEventHandler<MediaEvent> {

    @Resource
    private LiveItemCountRepositoryI liveItemCountRepository;
    @Resource
    private LiveLoadBO               liveLoadBO;

    @Override
    public void onEvent(MediaEvent event) throws Exception {
        MysqlMessage<MediaDO> messageBody = event.getValue();
		MediaDO mediaDO = messageBody.getData();
        String                type        = messageBody.getType();
        switch(type) {
            case UPDATE:
                if(mediaDO.getSourceType().equals(Conf.MEAID_SOURCE_ITEM) && mediaDO.getMediaType().equals(Conf.MEAID_TYPE_C0VER)) {
                    List<Long> liveIds = liveItemCountRepository.assembleByItemIds(Arrays.asList(mediaDO.getSourceId()));
                    if(Utils.isNotEmpty(liveIds)) {
                        liveIds.stream().forEach(liveId -> {
                            liveLoadBO.loadById(liveId);
                        });
                    }
                }

                break;
            default:
                break;
        }
    }
}
