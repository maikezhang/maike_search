package cn.idongjia.divine.mq.kafka.disruptor.handler.live;

import cn.idongjia.divine.biz.LiveLoadBO;
import cn.idongjia.divine.db.mybatis.pojo.ItemRelPO;
import cn.idongjia.divine.db.mybatis.pojo.LiveResourceDO;
import cn.idongjia.divine.db.mybatis.pojo.MediaDO;
import cn.idongjia.divine.dto.LiveDTO;
import cn.idongjia.divine.mq.kafka.disruptor.event.LiveResourceEvent;
import cn.idongjia.divine.repository.LiveItemCountRepositoryI;
import cn.idongjia.divine.repository.MediaRepositoryI;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
@Slf4j
public class LiveResourceHandler extends BaseEventHandler<LiveResourceEvent> {

    @Resource
    private LiveItemCountRepositoryI liveItemCountRepository;
    @Resource
    private LiveLoadBO               liveLoadBO;

    @Resource
    private MediaRepositoryI mediaRepository;

    @Override
    public void onEvent(LiveResourceEvent event) throws Exception {
        MysqlMessage<LiveResourceDO> messageBody    = event.getValue();
        LiveResourceDO               liveResourceDO = messageBody.getData();
        try {
            Long                      liveId         = liveResourceDO.getLid();
            Map<Long,List<ItemRelPO>> liveItemRelMap = liveItemCountRepository.assembleItems(Arrays.asList(liveId)).get();
            if(!Utils.isEmpty(liveItemRelMap)) {
                List<ItemRelPO> itemRelPOS = liveItemRelMap.get(liveId);
                LiveDTO         liveDTO;
                if(Utils.isNotEmpty(itemRelPOS)) {
                    liveDTO = assembleDTO(liveId,itemRelPOS);
                } else {
                    liveDTO = new LiveDTO();
                    liveDTO.setItemDOS(new ArrayList<>());
                    liveDTO.setId(liveId.toString());
                }
                liveLoadBO.update(liveDTO);

            }
        } catch(Exception e) {
            log.error("update live resource item failed {}",e);
        }
    }

    private LiveDTO assembleDTO(Long liveId,List<ItemRelPO> itemRelPOS) {
        List<Long> itemIds = new ArrayList<>();
        itemRelPOS.stream().forEach(itemRelPO -> {
            itemIds.add(itemRelPO.getItemId());
        });
        Map<Long,MediaDO> mediaMap = mediaRepository.assembleByItemId(itemIds);
        LiveDTO           liveDTO  = new LiveDTO();
        liveDTO.setId(liveId.toString());
        itemRelPOS.stream().forEach(itemRelPO -> {
            MediaDO mediaDO = mediaMap.get(itemRelPO.getItemId());
            itemRelPO.setPicture(mediaDO.getMediaUrl());
        });
        liveDTO.setItemDOS(itemRelPOS);
        return liveDTO;
    }
}
