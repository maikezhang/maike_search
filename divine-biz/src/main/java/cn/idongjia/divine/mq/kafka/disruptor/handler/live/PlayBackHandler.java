package cn.idongjia.divine.mq.kafka.disruptor.handler.live;

import cn.idongjia.divine.biz.LiveLoadBO;
import cn.idongjia.divine.db.mybatis.pojo.CountPO;
import cn.idongjia.divine.db.mybatis.pojo.LivePlaybackDO;
import cn.idongjia.divine.dto.LiveDTO;
import cn.idongjia.divine.mq.kafka.disruptor.event.PlayBackEvent;
import cn.idongjia.divine.repository.PlayBackRepositoryI;
import cn.idongjia.kafka.message.body.MysqlMessage;
import cn.idongjia.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
@Slf4j
public class PlayBackHandler extends BaseEventHandler<PlayBackEvent> {

    @Resource
    private PlayBackRepositoryI playBackRepository;
    @Resource
    private LiveLoadBO          liveLoadBO;




    @Override
    public void onEvent(PlayBackEvent event) throws Exception {
        MysqlMessage<LivePlaybackDO> messageBody = event.getValue();
        LivePlaybackDO               playbackDO  = messageBody.getData();
        try {
            Map<Long,CountPO> playBackCountMap = playBackRepository.assemble(Arrays.asList(playbackDO.getLid())).get();
            if(!Utils.isEmpty(playBackCountMap)) {
                List<LiveDTO> liveDTOS = playBackCountMap.values().stream().map(playBackCount -> {
                    LiveDTO liveDTO = new LiveDTO();
                    liveDTO.setHasPlayBack(playBackCount.getCount() > 0);
                    liveDTO.setId(playbackDO.getLid().toString());
                    return liveDTO;
                }).collect(Collectors.toList());
                liveLoadBO.update(liveDTOS);
            }
        } catch(Exception e) {
            log.error("update live playback item failed {}",e);

        }
    }
}
