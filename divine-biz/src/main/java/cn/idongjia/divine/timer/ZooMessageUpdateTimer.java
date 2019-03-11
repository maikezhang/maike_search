package cn.idongjia.divine.timer;

import cn.idongjia.divine.assembler.ZooMessageAssambler;
import cn.idongjia.divine.cache.ZooMessageUpdateOffsetCache;
import cn.idongjia.divine.db.es.entity.ZooMessageEntity;
import cn.idongjia.divine.db.mybatis.mapper.kaipao.LiveShowMapper;
import cn.idongjia.divine.db.mybatis.mapper.zoo.base.ZooMessageBaseMapper;
import cn.idongjia.divine.db.mybatis.pojo.ZooMessageDO;
import cn.idongjia.divine.db.mybatis.query.ZooMessageQuery;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.schedule.AbstractScheduleTask;
import cn.idongjia.zoo.api.ZooCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author maike_zhang
 * @create at 2018/12/17.
 */
@Component
@Slf4j
public class ZooMessageUpdateTimer {
//    private static final String UV_UPDATE_TASK = "ZOO_MESSAGE_UPDATE_TASK";

    @Resource
    private ZooMessageUpdateOffsetCache cache;

    @Resource
    private ZooMessageBaseMapper zooMessageBaseMapper;

    @Resource
    private ZooMessageAssambler zooMessageAssambler;

    @Resource
    private ESUpsertManager esUpsertManager;

    public void update() {
        log.info("开始更新聊天消息。。。。。。");
        List<Long> zmids = cache.lpopAllZid();
        log.info("开始更新聊天消息zmids:{}", zmids);

        if (!Utils.isEmpty(zmids)) {

            ZooMessageQuery    query         = ZooMessageQuery.builder().zmids(zmids).build();
            List<ZooMessageDO> zooMessageDOS = zooMessageBaseMapper.select(query);

            if (!Utils.isEmpty(zooMessageDOS)) {
                List<ZooMessageEntity> zooMessageEntityList = zooMessageAssambler.assemble(zooMessageDOS);

                esUpsertManager.bulkZooMessageUpsert(zooMessageEntityList);

            }
        }

    }

}
