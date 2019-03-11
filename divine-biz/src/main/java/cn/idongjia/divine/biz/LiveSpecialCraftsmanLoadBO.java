package cn.idongjia.divine.biz;

import cn.idongjia.common.query.BaseSearch;
import cn.idongjia.divine.assembler.AssemblerI;
import cn.idongjia.divine.assembler.LiveSpecialCraftsmanAssembler;
import cn.idongjia.divine.db.es.entity.LiveEntity;
import cn.idongjia.divine.db.es.entity.LiveSpecialCraftsmanEntity;
import cn.idongjia.divine.db.mybatis.pojo.LiveAnchorBlackWhiteDO;
import cn.idongjia.divine.db.mybatis.query.LiveAnchorBlackWhiteQuery;
import cn.idongjia.divine.dto.LiveSpecialCraftsmanDTO;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.repository.LiveSpecialCraftsmanRepositoryI;
import cn.idongjia.divine.task.LiveSpecialCraftsmanUpdateTask;
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
 * @create at 2018/7/26.
 */
@Component
public class LiveSpecialCraftsmanLoadBO {
    @Resource
    MappingService mappingService;
    private static final Log logger = LogFactory.getLog(LiveSpecialCraftsmanLoadBO.class);

    private static final int                                                                                   BATCH_SIZE      = 1500;
    public static final  List<Integer>                                                                         ALL_LIVE_STATUS = Arrays.asList(0,1,2,3);
    @Resource
    private              LiveSpecialCraftsmanRepositoryI                                                       liveSpecialCraftsmanRepositoryI;
    @Resource
    private              ESUpsertManager                                                                       esUpsertManager;
    @Resource
    private              AssemblerI<LiveSpecialCraftsmanEntity,LiveAnchorBlackWhiteDO,LiveSpecialCraftsmanDTO> liveSpecialCraftsmanAssembler;

    public boolean loadById(Long craftsmanId) {
        LiveAnchorBlackWhiteDO           liveAnchorBlackWhiteDO       = liveSpecialCraftsmanRepositoryI.getByCraftsmanId(craftsmanId);
        List<LiveSpecialCraftsmanEntity> liveSpecialCraftsmanEntities = liveSpecialCraftsmanAssembler.assemble(Arrays.asList(liveAnchorBlackWhiteDO));
        return esUpsertManager.buldLiveCraftsmanUpsert(liveSpecialCraftsmanEntities);
    }

    public boolean update(List<LiveSpecialCraftsmanDTO> liveSpecialCraftsmanDTOS) {
        if(Utils.isEmpty(liveSpecialCraftsmanDTOS)) {
            return false;
        }
        List<LiveSpecialCraftsmanEntity> liveSpecialCraftsmanEntities = liveSpecialCraftsmanDTOS.stream().map(liveSpecialCraftsmanDTO -> {
            return liveSpecialCraftsmanAssembler.assemble(liveSpecialCraftsmanDTO);
        }).collect(Collectors.toList());
        return esUpsertManager.buldLiveCraftsmanUpsert(liveSpecialCraftsmanEntities);
    }

    public Integer load() {
        boolean success = mappingService.putMapping(LiveEntity.class);
        if(!success) {
            return 0;
        }
        // 线程池
        ExecutorService exec = new ThreadPoolExecutor(3,10,60 * 5,TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>());
        ((ThreadPoolExecutor)exec).allowCoreThreadTimeOut(true);
        int  total      = liveSpecialCraftsmanRepositoryI.count(LiveAnchorBlackWhiteQuery.builder().build());
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
                LiveSpecialCraftsmanUpdateTask liveSpecialCraftsmanUpdateTask = new LiveSpecialCraftsmanUpdateTask(baseQuery);
                futuresResult.add(exec.submit(liveSpecialCraftsmanUpdateTask));
            } catch(Exception e) {
                logger.error("LiveSpecialCraftsmanLoadBO load failed {}",e);
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

    public boolean update(LiveSpecialCraftsmanDTO liveSpecialCraftsmanDTO) {
        if(liveSpecialCraftsmanDTO == null) {
            return false;
        }
        LiveSpecialCraftsmanEntity liveSpecialCraftsmanEntity = liveSpecialCraftsmanAssembler.assemble(liveSpecialCraftsmanDTO);
        return esUpsertManager.liveCraftsmanUpsert(liveSpecialCraftsmanEntity);
    }
}
