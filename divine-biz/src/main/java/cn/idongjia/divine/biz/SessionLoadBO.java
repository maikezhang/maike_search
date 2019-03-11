package cn.idongjia.divine.biz;

import cn.idongjia.common.query.BaseSearch;
import cn.idongjia.divine.assembler.AssemblerI;
import cn.idongjia.divine.db.es.entity.SessionEntity;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionDO;
import cn.idongjia.divine.db.mybatis.query.AuctionSessionQuery;
import cn.idongjia.divine.dto.SessionDTO;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.repository.AuctionSessionRepositoryI;
import cn.idongjia.divine.task.SessionUpdateTask;
import cn.idongjia.log.Log;
import cn.idongjia.log.LogFactory;
import cn.idongjia.se.lib.service.MappingService;
import cn.idongjia.util.Utils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/9/3.
 */
@Component
public class SessionLoadBO {
    @Resource
    MappingService mappingService;
    private static final Log logger = LogFactory.getLog(LiveLoadBO.class);

    private static final int                                                   BATCH_SIZE         = 1500;
    private static final  List<Integer>                                         ALL_SESSION_STATUS =
            Arrays.asList(-1,0,1,2,3,4,5,6);
    @Resource
    private              AuctionSessionRepositoryI                             auctionSessionRepositoryI;
    @Resource
    private              ESUpsertManager                                       esUpsertManager;
    @Resource
    private              AssemblerI<SessionEntity,AuctionSessionDO,SessionDTO> sessionAssembler;

    public boolean loadById(Long sessionId) {
        AuctionSessionDO    auctionSessionDO = auctionSessionRepositoryI.getByPrimaryKey(sessionId.intValue());
        List<SessionEntity> sessionEntities  = sessionAssembler.assemble(Arrays.asList(auctionSessionDO));
        return esUpsertManager.bulkSessionUpsert(sessionEntities);
    }

    public boolean update(List<SessionDTO> sessionDTOS) {
        if(Utils.isEmpty(sessionDTOS)) {
            return false;
        }
        List<SessionEntity> sessionEntities = sessionDTOS.stream().map(sessionDTO -> {
            return sessionAssembler.assemble(sessionDTO);
        }).collect(Collectors.toList());
        return esUpsertManager.bulkSessionUpsert(sessionEntities);
    }

    public Integer load() {
        boolean success = mappingService.putMapping(SessionEntity.class);
        if(!success) {
            return 0;
        }
        // 线程池
        ExecutorService exec = new ThreadPoolExecutor(3,10,60 * 5,TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>());
        ((ThreadPoolExecutor)exec).allowCoreThreadTimeOut(true);
        int  total      = auctionSessionRepositoryI.count(AuctionSessionQuery.builder().statusArray(ALL_SESSION_STATUS).build());
        long page       = total / BATCH_SIZE;
        int  remainPage = total % BATCH_SIZE > 0 ? 1 : 0;
        page += remainPage;
        Semaphore             semp          = new Semaphore(10);
        List<Future<Integer>> futuresResult = new ArrayList<>();
        for(int i = 0;i < page;i++) {
            try {
                semp.acquire();
                final int        pageNow   = i + 1;
                final BaseSearch baseQuery = new BaseSearch();
                baseQuery.setPage(pageNow);
                baseQuery.setLimit(BATCH_SIZE);
                SessionUpdateTask sessionUpdateTask = new SessionUpdateTask(ALL_SESSION_STATUS,baseQuery);
                futuresResult.add(exec.submit(sessionUpdateTask));
            } catch(Exception e) {
                logger.error("GeneralLiveLoadBO load failed {}",e);
            } finally {
                semp.release();
            }
        }
        int loadTotal = 0;
        for(Future<Integer> listFuture: futuresResult) {
            try {
                loadTotal += listFuture.get();
            } catch(Exception e) {
                logger.error("获取数据失败{}",e);
            }

        }
        return loadTotal;
    }

    public boolean update(SessionDTO sessionDTO) {
        if(sessionDTO == null) {
            return false;
        }
        SessionEntity sessionEntity = sessionAssembler.assemble(sessionDTO);
        return esUpsertManager.sessionUpsert(sessionEntity);
    }
}
