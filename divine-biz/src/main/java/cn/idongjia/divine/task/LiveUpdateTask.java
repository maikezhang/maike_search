package cn.idongjia.divine.task;

import cn.idongjia.common.query.BaseSearch;
import cn.idongjia.divine.assembler.LiveAssembler;
import cn.idongjia.divine.db.es.entity.LiveEntity;
import cn.idongjia.divine.db.mybatis.mapper.kaipao.base.LiveShowBaseMapper;
import cn.idongjia.divine.db.mybatis.pojo.LiveShowDO;
import cn.idongjia.divine.db.mybatis.query.LiveShowQuery;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.utils.SpringBeanLoader;
import cn.idongjia.util.Utils;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author lc
 */
@Slf4j
public class LiveUpdateTask implements Callable<Integer> {


    private static final LiveShowBaseMapper liveShowBaseMapper   = SpringBeanLoader.getBean("liveShowBaseMapper", LiveShowBaseMapper.class);
    private static final LiveAssembler      liveAssembler = SpringBeanLoader.getBean("liveAssembler",LiveAssembler.class);
    private              ESUpsertManager    esUpsertManager      = SpringBeanLoader.getBean("esUpsertManager",ESUpsertManager.class);
    private              List<Integer>      statusArray;

    private Long updateTime;

    private BaseSearch baseSearch;

    public LiveUpdateTask(List<Integer> statusArray, BaseSearch baseSearch, Long updateTime) {
        this.baseSearch = baseSearch;
        this.statusArray = statusArray;
        this.updateTime = updateTime;
    }


    @Override
    public Integer call() {
        LiveShowQuery    liveShowQuery       = LiveShowQuery.builder().statusArray(statusArray).page(baseSearch.getPage()).limit(baseSearch.getLimit()).modifiedtm(updateTime==null?null:new Date(updateTime)).build();
        List<LiveShowDO> liveShowDOS         = liveShowBaseMapper.select(liveShowQuery);
        List<LiveEntity> liveEntities = liveAssembler.assemble(liveShowDOS);
        if(!Utils.isEmpty(liveEntities)) {
            esUpsertManager.bulkLiveUpsert(liveEntities);

        }
        return liveEntities.size();

    }
}
