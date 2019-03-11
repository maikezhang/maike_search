package cn.idongjia.divine.mq.kafka.disruptor.handler.live;

import cn.idongjia.divine.assembler.AssemblerI;
import cn.idongjia.divine.biz.LiveLoadBO;
import cn.idongjia.divine.db.es.entity.LiveEntity;
import cn.idongjia.divine.db.mybatis.pojo.LiveBookDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveShowDO;
import cn.idongjia.divine.dto.LiveDTO;
import cn.idongjia.divine.lib.pojo.Conf;
import cn.idongjia.divine.mq.kafka.disruptor.event.LiveBookEvent;
import cn.idongjia.divine.repository.LiveBookRepositoryI;
import cn.idongjia.divine.repository.LiveShowRepositoryI;
import cn.idongjia.kafka.message.body.MysqlMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Component
@Slf4j
public class LiveBookHandler extends BaseEventHandler<LiveBookEvent> {
    @Resource
    private AssemblerI<LiveEntity, LiveShowDO, LiveDTO> liveAssembler;
    @Resource
    private LiveLoadBO                                  liveLoadBO;

    @Resource
    private LiveShowRepositoryI liveShowRepository;
    @Resource
    private LiveBookRepositoryI liveBookRepository;


    @Override
    public void onEvent(LiveBookEvent event) throws Exception {
        MysqlMessage<LiveBookDO> messageBody = event.getValue();
        LiveBookDO               liveBookDO  = messageBody.getData();
        LiveShowDO               liveShowDO  = liveShowRepository.getByPrimaryKey(liveBookDO.getLid());
//        if(liveShowDO.getType().intValue() != Conf.LIVE_TYPE_SESSION.intValue()) {
        Long    liveId    = liveShowDO.getId();
        int     bookTotal = liveBookRepository.countByLiveId(liveId);
        LiveDTO liveDTO   = new LiveDTO();
        liveDTO.setBookTotal(bookTotal);
        liveDTO.setId(liveId.toString());
        LiveEntity liveEntity = liveAssembler.assemble(liveDTO);
        liveLoadBO.update(liveEntity);
//        }
    }
}
