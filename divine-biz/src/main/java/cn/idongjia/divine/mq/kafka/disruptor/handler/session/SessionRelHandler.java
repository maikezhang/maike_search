package cn.idongjia.divine.mq.kafka.disruptor.handler.session;

import cn.idongjia.divine.biz.SessionLoadBO;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionRelDO;
import cn.idongjia.divine.dto.SessionDTO;
import cn.idongjia.divine.mq.kafka.disruptor.event.AuctionSessionRelEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.divine.repository.SessionItemCountRepositoryI;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 拍品关联事件处理
 *
 * @author yuexiaodong@idongjia.cn
 * @date 2018/11/21
 */
@Component
@Slf4j
public class SessionRelHandler extends BaseEventHandler<AuctionSessionRelEvent> {

    @Resource
    private SessionItemCountRepositoryI sessionItemCountRepository;
    @Resource
    private SessionLoadBO               sessionLoadBO;

    @Override
    public void onEvent(AuctionSessionRelEvent event) throws Exception {
        MysqlMessage<AuctionSessionRelDO> message             = event.getValue();
        AuctionSessionRelDO               auctionSessionRelDO = message.getData();
        if (auctionSessionRelDO.getAsid() == null || auctionSessionRelDO.getAsid() < 1) {
            return;
        }
        final long sessionId = auctionSessionRelDO.getAsid().longValue();
        //关联拍品变化，只需要更新专场关联的拍品数量
        final Map<Long, Integer> sessionRelCountMap = sessionItemCountRepository.
                assembleRelCount(Lists.newArrayList(sessionId));
        final Integer relCount   = sessionRelCountMap.getOrDefault(sessionId, 0);
        SessionDTO    sessionDTO = new SessionDTO();
        sessionDTO.setRelatedCount(relCount);
        sessionDTO.setSessionId(sessionId);
        sessionLoadBO.update(sessionDTO);
    }
}
