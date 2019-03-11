package cn.idongjia.divine.biz;

import cn.idongjia.divine.assembler.AssemblerI;
import cn.idongjia.divine.db.es.entity.SessionLiveEntity;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionDO;
import cn.idongjia.divine.db.mybatis.query.AuctionSessionQuery;
import cn.idongjia.divine.dto.SessionLiveDTO;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.repository.AuctionSessionRepositoryI;
import cn.idongjia.divine.task.SessionLiveUpdateTask;
import cn.idongjia.log.Log;
import cn.idongjia.log.LogFactory;
import cn.idongjia.se.lib.service.MappingService;
import io.netty.util.concurrent.DefaultThreadFactory;
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

/**
 * @author yuexiaodong@idongjia.cn
 * @date 2018-11-30
 */
@Component
public class SessionLiveLoadBO {

    private static final Log LOGGER = LogFactory.getLog(SessionLiveLoadBO.class);

    /**
     * 直播拍类型
     */
    private static final int           LIVE_SESSION_TYPE = 2;
    private static final int           BATCH_SIZE        = 1500;
    private static final List<Integer> SESSION_STATUS    = Arrays.asList(5, 2);

    @Resource
    private AssemblerI<SessionLiveEntity, AuctionSessionDO, SessionLiveDTO> sessionLiveAssembler;

    @Resource
    private ESUpsertManager           esUpsertManager;
    @Resource
    private AuctionSessionRepositoryI auctionSessionRepositoryI;
    @Resource
    private MappingService            mappingService;

    public boolean loadById(Long sessionId) {
        final AuctionSessionDO sessionDO = auctionSessionRepositoryI.getByPrimaryKey(sessionId.intValue());
        if (sessionDO.getType() == null || sessionDO.getType() != LIVE_SESSION_TYPE) {
            return false;
        }
        final List<SessionLiveEntity> sessionLiveEntityList = sessionLiveAssembler.assemble(Arrays.asList(sessionDO));
        return esUpsertManager.bulkSessionLiveUpsert(sessionLiveEntityList);
    }

    public Integer load() {
        boolean success = mappingService.putMapping(SessionLiveEntity.class);
        if (!success) {
            return 0;
        }
        ExecutorService pool = new ThreadPoolExecutor(
                4, 16,
                5L, TimeUnit.MINUTES,
                new LinkedBlockingDeque<Runnable>(),
                new DefaultThreadFactory("SessionLivePool"));
        final int total = auctionSessionRepositoryI.count(AuctionSessionQuery.builder()
                .type(LIVE_SESSION_TYPE)
                .statusArray(SESSION_STATUS)
                .build());
        long page      = total / BATCH_SIZE;
        int  extraPage = total % BATCH_SIZE > 0 ? 1 : 0;
        page += extraPage;
        Semaphore             flag          = new Semaphore(10);
        List<Future<Integer>> executeResult = new ArrayList<>();
        for (int i = 1; i <= page; i++) {
            try {
                flag.acquire();
                SessionLiveUpdateTask task = new SessionLiveUpdateTask(
                        SESSION_STATUS, LIVE_SESSION_TYPE, i, BATCH_SIZE);
                executeResult.add(pool.submit(task));
            } catch (Exception e) {
                LOGGER.error("SessionLive update error", e);
            } finally {
                flag.release();
            }
        }
        int updateTotal = 0;
        for (Future<Integer> taskCount : executeResult) {
            try {
                updateTotal += taskCount.get();
            } catch (Exception e) {
                LOGGER.error("SessionLive update count error", e);
            }
        }
        return updateTotal;
    }

}
