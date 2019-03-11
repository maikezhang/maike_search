package cn.idongjia.divine.task;

import cn.idongjia.common.query.BaseSearch;
import cn.idongjia.divine.assembler.LiveSpecialCraftsmanAssembler;
import cn.idongjia.divine.db.es.entity.LiveEntity;
import cn.idongjia.divine.db.es.entity.LiveSpecialCraftsmanEntity;
import cn.idongjia.divine.db.mybatis.mapper.kaipao.LiveAnchorBlackWhiteMapper;
import cn.idongjia.divine.db.mybatis.pojo.LiveAnchorBlackWhiteDO;
import cn.idongjia.divine.db.mybatis.query.LiveAnchorBlackWhiteQuery;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.utils.SpringBeanLoader;
import cn.idongjia.util.Utils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author lc
 */
@Slf4j
public class LiveSpecialCraftsmanUpdateTask implements Callable<Integer> {

    private static final LiveAnchorBlackWhiteMapper    liveAnchorBlackWhiteMapper    = SpringBeanLoader.getBean("liveAnchorBlackWhiteMapper",LiveAnchorBlackWhiteMapper.class);
    private static final LiveSpecialCraftsmanAssembler liveSpecialCraftsmanAssembler = SpringBeanLoader.getBean("liveSpecialCraftsmanAssembler",LiveSpecialCraftsmanAssembler.class);
    private              ESUpsertManager               esUpsertManager               = SpringBeanLoader.getBean("esUpsertManager",ESUpsertManager.class);


    private BaseSearch baseSearch;

    public LiveSpecialCraftsmanUpdateTask(BaseSearch baseSearch) {
        this.baseSearch = baseSearch;
    }

    @Override
    public Integer call() {
        LiveAnchorBlackWhiteQuery liveAnchorBlackWhiteQuery = new LiveAnchorBlackWhiteQuery();
        liveAnchorBlackWhiteQuery.setLimit(baseSearch.getLimit());
        liveAnchorBlackWhiteQuery.setPage(baseSearch.getPage());
        List<LiveAnchorBlackWhiteDO>     liveAnchorBlackWhiteDOS      = liveAnchorBlackWhiteMapper.select(liveAnchorBlackWhiteQuery);
        List<LiveSpecialCraftsmanEntity> liveSpecialCraftsmanEntities = liveSpecialCraftsmanAssembler.assemble(liveAnchorBlackWhiteDOS);
        if(!Utils.isEmpty(liveSpecialCraftsmanEntities)) {
            esUpsertManager.buldLiveCraftsmanUpsert(liveSpecialCraftsmanEntities);
        }
        return liveSpecialCraftsmanEntities.size();

    }
}
