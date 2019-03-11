package cn.idongjia.divine.task;

import cn.idongjia.divine.assembler.SessionLiveAssembler;
import cn.idongjia.divine.db.es.entity.SessionLiveEntity;
import cn.idongjia.divine.db.mybatis.mapper.kaipao.AuctionSessionMapper;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionDO;
import cn.idongjia.divine.db.mybatis.query.AuctionSessionQuery;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.utils.SpringBeanLoader;
import cn.idongjia.divine.utils.Utils;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author yuexiaodong@idongjia.cn
 * @date 2018-11-30
 */
public class SessionLiveUpdateTask implements Callable<Integer> {

    private static final AuctionSessionMapper auctionSessionMapper = SpringBeanLoader.
            getBean("auctionSessionMapper", AuctionSessionMapper.class);
    private static final SessionLiveAssembler sessionLiveAssembler = SpringBeanLoader.
            getBean("sessionLiveAssembler", SessionLiveAssembler.class);
    private              ESUpsertManager      esUpsertManager      = SpringBeanLoader.
            getBean("esUpsertManager", ESUpsertManager.class);

    private List<Integer> statusList;
    private int           page;
    private int           limit;
    private int           type;

    public SessionLiveUpdateTask(List<Integer> statusList, int type, int page, int limit) {
        this.statusList = statusList;
        this.page = page;
        this.limit = limit;
        this.type = type;
    }

    @Override
    public Integer call() throws Exception {
        AuctionSessionQuery query = AuctionSessionQuery.builder()
                .statusArray(this.statusList)
                .page(page)
                .limit(limit)
                .type(type)
                .build();
        final List<AuctionSessionDO> sessionDOS = auctionSessionMapper.select(query);
        final List<SessionLiveEntity> sessionLiveEntities = sessionLiveAssembler.assemble(sessionDOS);
        if (Utils.isEmpty(sessionLiveEntities)) {
            return 0;
        }
        final boolean result = esUpsertManager.bulkSessionLiveUpsert(sessionLiveEntities);
        if (result) {
            return sessionLiveEntities.size();
        } else {
            return 0;
        }
    }
}
