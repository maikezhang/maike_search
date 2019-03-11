package cn.idongjia.divine.task;

import cn.idongjia.common.query.BaseSearch;
import cn.idongjia.divine.assembler.SessionAssembler;
import cn.idongjia.divine.db.es.entity.SessionEntity;
import cn.idongjia.divine.db.mybatis.mapper.kaipao.AuctionSessionMapper;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionDO;
import cn.idongjia.divine.db.mybatis.query.AuctionSessionQuery;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.utils.SpringBeanLoader;
import cn.idongjia.divine.utils.Utils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author lc
 */
@Slf4j
public class SessionUpdateTask implements Callable<Integer> {

    private static final AuctionSessionMapper auctionSessionMapper = SpringBeanLoader.getBean("auctionSessionMapper",AuctionSessionMapper.class);
    private static final SessionAssembler     sessionAssembler     = SpringBeanLoader.getBean("sessionAssembler",SessionAssembler.class);
    private              ESUpsertManager      esUpsertManager      = SpringBeanLoader.getBean("esUpsertManager",ESUpsertManager.class);
    private              List<Integer>        statusArray;

    private BaseSearch baseSearch;

    public SessionUpdateTask(List<Integer> statusArray,BaseSearch baseSearch) {
        this.baseSearch = baseSearch;
        this.statusArray = statusArray;
    }

    @Override
    public Integer call() {
        AuctionSessionQuery auctionSessionQuery = AuctionSessionQuery.builder().statusArray(statusArray).page(baseSearch.getPage()).limit(baseSearch.getLimit()).build();
        List<AuctionSessionDO> auctionSessionDOS = auctionSessionMapper.select(auctionSessionQuery);
        List<SessionEntity>    sessionEntities   = sessionAssembler.assemble(auctionSessionDOS);
        if(Utils.isNotEmpty(sessionEntities)) {
            esUpsertManager.bulkSessionUpsert(sessionEntities);
        }
        return sessionEntities.size();

    }
}
