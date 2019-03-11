package cn.idongjia.divine.mq.kafka.disruptor.handler.live;

import cn.idongjia.divine.assembler.AssemblerI;
import cn.idongjia.divine.assembler.LiveAssembler;
import cn.idongjia.divine.biz.LiveLoadBO;
import cn.idongjia.divine.db.es.entity.LiveEntity;
import cn.idongjia.divine.db.mybatis.pojo.LivePureDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveShowDO;
import cn.idongjia.divine.dto.LiveDTO;
import cn.idongjia.divine.lib.pojo.Conf;
import cn.idongjia.divine.mq.kafka.disruptor.event.LivePureEvent;
import cn.idongjia.kafka.message.body.MysqlMessage;
import cn.idongjia.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
@Slf4j
public class LivePureHandler extends BaseEventHandler<LivePureEvent> {

    @Resource
    private AssemblerI<LiveEntity,LiveShowDO,LiveDTO> liveAssembler;
    @Resource
    private LiveLoadBO                                liveLoadBO;




    @Override
    public void onEvent(LivePureEvent event) throws Exception {
        MysqlMessage<LivePureDO> messageBody = event.getValue();
        long                     now         = Utils.getCurrentMillis();
        String                   type        = messageBody.getType();
        LivePureDO               livePureDO  = messageBody.getData();
        log.info("update takes  old:{} new :{} {}ms",messageBody.getOld(),livePureDO,Utils.getCurrentMillis() - now);
        switch(type) {
            case UPDATE:
                LiveDTO liveDTO = new LiveDTO();
                liveDTO.setId(livePureDO.getId().toString());
                liveDTO.setPic(livePureDO.getPic() == null ? Conf.defaultString : livePureDO.getPic());
                LiveEntity liveEntity = liveAssembler.assemble(liveDTO);
                liveLoadBO.update(liveEntity);
                break;
            default:
                break;
        }
    }
}
