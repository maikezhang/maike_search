package cn.idongjia.divine.biz;

import cn.idongjia.common.query.BaseSearch;
import cn.idongjia.divine.assembler.AssemblerI;
import cn.idongjia.divine.db.es.entity.AuctionStateEntity;
import cn.idongjia.divine.db.mybatis.pojo.AuctionStateDO;
import cn.idongjia.divine.dto.AuctionStateDTO;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.repository.AuctionRepositoryI;
import cn.idongjia.divine.task.AuctionStateUpdateTask;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.log.Log;
import cn.idongjia.log.LogFactory;
import cn.idongjia.se.lib.service.MappingService;
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
 * @author lc
 * @create at 2018/9/3.
 */
@Component
public class AuctionStateLoadBO {
    @Resource
    MappingService mappingService;
    private static final Log logger = LogFactory.getLog(LiveLoadBO.class);

    private static final int BATCH_SIZE = 1500;

    @Resource
    private AuctionRepositoryI                                              auctionRepositoryI;
    @Resource
    private ESUpsertManager                                                 esUpsertManager;
    @Resource
    private AssemblerI<AuctionStateEntity, AuctionStateDO, AuctionStateDTO> auctionStateAssembler;

    public boolean loadById(Long craftsmanId) {
        return loadById(craftsmanId, null);
    }

    public boolean loadById(Long craftsmanId, Long itemId) {
        if (craftsmanId == null && itemId == null) {
            return false;
        }
        final List<Long> craftsmanIds = craftsmanId == null ? null : Arrays.asList(craftsmanId);
        List<AuctionStateDO> auctionStateDOS = auctionRepositoryI.groupCraftsmanAuctionBystate(craftsmanIds, itemId);

        if (Utils.isNotEmpty(auctionStateDOS)) {
            List<AuctionStateEntity> auctionStateEntities = auctionStateAssembler.assemble(auctionStateDOS);
            return esUpsertManager.bulkAuctionStateUpsert(auctionStateEntities);
        }
        return false;

    }

    public Integer load() {
        boolean success = mappingService.putMapping(AuctionStateEntity.class);
        if (!success) {
            return 0;
        }
        // 线程池
        ExecutorService exec = new ThreadPoolExecutor(3, 10, 60 * 5, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
        ((ThreadPoolExecutor) exec).allowCoreThreadTimeOut(true);
        long total      = auctionRepositoryI.countCraftsmanAuctionByState();
        long page       = total / BATCH_SIZE;
        int  remainPage = total % BATCH_SIZE > 0 ? 1 : 0;
        page += remainPage;
        Semaphore             semp          = new Semaphore(10);
        List<Future<Integer>> futuresResult = new ArrayList<>();
        for (int i = 0; i < page; i++) {
            try {
                semp.acquire();
                final int        pageNow   = i + 1;
                final BaseSearch baseQuery = new BaseSearch();
                baseQuery.setPage(pageNow);
                baseQuery.setLimit(BATCH_SIZE);
                AuctionStateUpdateTask auctionStateUpdateTask = new AuctionStateUpdateTask(baseQuery);
                futuresResult.add(exec.submit(auctionStateUpdateTask));
            } catch (Exception e) {
                logger.error("AuctionStateLoadBO load failed {}", e);
            } finally {
                semp.release();
            }
        }
        int loadTotal = 0;
        for (Future<Integer> listFuture : futuresResult) {
            try {
                loadTotal += listFuture.get();
            } catch (Exception e) {
                logger.error("获取数据失败{}", e);
            }

        }
        return loadTotal;
    }
}
