package cn.idongjia.divine.mq.kafka.disruptor.handler.live;

import cn.idongjia.divine.biz.LiveLoadBO;
import cn.idongjia.divine.db.mybatis.pojo.LiveShowDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveVideoCoverDO;
import cn.idongjia.divine.db.mybatis.query.LiveShowQuery;
import cn.idongjia.divine.dto.LiveDTO;
import cn.idongjia.divine.mq.kafka.disruptor.event.LiveVideCoverEvent;
import cn.idongjia.divine.repository.LiveShowRepositoryI;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
@Slf4j
public class LiveVideoCoverHandler extends BaseEventHandler<LiveVideCoverEvent> {

    @Resource
    private LiveLoadBO liveLoadBO;

    @Resource
    private LiveShowRepositoryI liveShowRepositoryI;

    @Override
    public void onEvent(LiveVideCoverEvent event) throws Exception {
        MysqlMessage<LiveVideoCoverDO> messageBody  = event.getValue();
        LiveVideoCoverDO               videoCoverDO = messageBody.getData();
        List<LiveShowDO>               liveShowDOS  = liveShowRepositoryI.select(LiveShowQuery.builder().status(0).videoCoverId(videoCoverDO.getId()).build());
        if(Utils.isNotEmpty(liveShowDOS)) {
            liveShowDOS.stream().forEach(liveShowDO -> {
                LiveDTO liveDTO = new LiveDTO();
                liveDTO.setVideoDuration(Utils.getDefault(videoCoverDO.getDuration()).longValue());
                liveDTO.setVideoId(Utils.getDefaultId(videoCoverDO.getId()));
                liveDTO.setVideoPic(Utils.getDefaultString(videoCoverDO.getPic()));
                liveDTO.setVideoURL(Utils.getDefaultString(videoCoverDO.getUrl()));
                liveDTO.setId(liveShowDO.getId().toString());
                liveLoadBO.update(liveDTO);
            });
        }

    }

}
