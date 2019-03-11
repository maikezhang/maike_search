package cn.idongjia.divine.mq.kafka.disruptor.handler.session;

import cn.idongjia.divine.assembler.AssemblerI;
import cn.idongjia.divine.assembler.SessionAssembler;
import cn.idongjia.divine.db.es.entity.SessionEntity;
import cn.idongjia.divine.db.es.entity.SessionLiveEntity;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveShowDO;
import cn.idongjia.divine.dto.SessionDTO;
import cn.idongjia.divine.dto.SessionLiveDTO;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.mq.kafka.disruptor.event.LiveShowEvent;
import cn.idongjia.divine.mq.kafka.disruptor.handler.live.BaseEventHandler;
import cn.idongjia.divine.repository.AuctionSessionRepositoryI;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.kafka.message.body.MysqlMessage;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
@Slf4j
public class SessionLiveHandler extends BaseEventHandler<LiveShowEvent> {
    @Resource
    private AuctionSessionRepositoryI auctionSessionRepositoryI;
    @Resource
    private SessionAssembler          sessionAssembler;
    @Resource
    private ESUpsertManager           esUpsertManager;

    @Resource
    private AssemblerI<SessionLiveEntity, AuctionSessionDO, SessionLiveDTO> sessionLiveAssembler;

    @Override
    public void onEvent(LiveShowEvent event) throws Exception {
        MysqlMessage<LiveShowDO> messageBody = event.getValue();
        String                   type        = messageBody.getType();
        LiveShowDO               liveShowDO  = messageBody.getData();
        switch (type) {
            case INSERT:
            case UPDATE:
                Map<Long, AuctionSessionDO> sessionDOMap = auctionSessionRepositoryI.mapByLiveId(Arrays.asList(liveShowDO.getId()));
                if (Utils.isNotEmpty(sessionDOMap)) {
                    AuctionSessionDO auctionSessionDO = sessionDOMap.get(liveShowDO.getId());
                    if (auctionSessionDO != null) {
                        SessionDTO sessionDTO = new SessionDTO();
                        sessionDTO.setLiveId(liveShowDO.getId());
                        sessionDTO.setLivePreStartTime(Utils.getDefaultTime(liveShowDO.getPrestarttm()));
                        sessionDTO.setLiveState(Utils.getDefaultEnum(liveShowDO.getState()));
                        sessionDTO.setSessionId(auctionSessionDO.getAsid().longValue());
                        SessionEntity sessionEntity = sessionAssembler.assemble(sessionDTO);
                        esUpsertManager.sessionUpsert(sessionEntity);
                    }
                    //直播拍索引更新
                    final List<SessionLiveEntity> entities = sessionLiveAssembler.assemble(Lists.newArrayList(auctionSessionDO));
                    esUpsertManager.bulkSessionLiveUpsert(entities);
                }
                break;
            default:
                break;
        }
    }
}
