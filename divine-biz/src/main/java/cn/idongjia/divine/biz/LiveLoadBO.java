package cn.idongjia.divine.biz;

import cn.idongjia.common.query.BaseSearch;
import cn.idongjia.divine.assembler.LiveAssembler;
import cn.idongjia.divine.db.es.entity.LiveEntity;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveShowDO;
import cn.idongjia.divine.db.mybatis.query.LiveShowQuery;
import cn.idongjia.divine.dto.LiveDTO;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.query.LiveESQuery;
import cn.idongjia.divine.repository.AuctionSessionRepositoryI;
import cn.idongjia.divine.repository.LiveShowRepositoryI;
import cn.idongjia.divine.task.LiveUpdateTask;
import cn.idongjia.log.Log;
import cn.idongjia.log.LogFactory;
import cn.idongjia.se.lib.service.MappingService;
import cn.idongjia.util.Utils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
public class LiveLoadBO {
    @Resource
    MappingService mappingService;
    private static final Log logger = LogFactory.getLog(LiveLoadBO.class);

    private static final int                       BATCH_SIZE      = 1500;
    public static final  List<Integer>             ALL_LIVE_STATUS = Arrays.asList(0,1,2,3);
    @Resource
    private              LiveShowRepositoryI       liveShowRepositoryI;
    @Resource
    private              AuctionSessionRepositoryI auctionSessionRepositoryI;
    @Resource
    private              ESUpsertManager           esUpsertManager;
    @Resource
    private              LiveAssembler             liveAssembler;

    public boolean loadById(Long liveId) {
        LiveShowDO       liveShowDO   = liveShowRepositoryI.getByPrimaryKey(liveId);
        List<LiveEntity> liveEntities = liveAssembler.assemble(Arrays.asList(liveShowDO));
        return esUpsertManager.bulkLiveUpsert(liveEntities);
    }

    public Long getLiveBySessionId(Long sessionId) {
        AuctionSessionDO auctionSessionDO = auctionSessionRepositoryI.getByPrimaryKey(sessionId.intValue());
        if(auctionSessionDO.getType().equals(2)) {
            return auctionSessionDO.getLsid() != null ? auctionSessionDO.getLsid().longValue() : null;
        }
        return null;
    }

    public boolean update(LiveEntity liveEntity) {
        if(liveEntity == null) {
            return false;
        }
        return esUpsertManager.upsertLiveEntity(liveEntity);
    }

    public boolean updateByQuery(Map<String,Object> map,LiveESQuery liveESQuery) {
        if(map == null) {
            return false;
        }
        return esUpsertManager.updateByQuery(LiveEntity.indexName,LiveEntity.typeName,liveESQuery,map);
    }

    public boolean update(List<LiveDTO> liveDTOS) {
        if(Utils.isEmpty(liveDTOS)) {
            return false;
        }
        List<LiveEntity> liveEntities = liveDTOS.stream().map(liveDTO -> {
            return liveAssembler.assemble(liveDTO);
        }).collect(Collectors.toList());
        return esUpsertManager.bulkLiveUpsert(liveEntities);
    }

    public Integer load() {
        boolean success = mappingService.putMapping(LiveEntity.class);
        if(!success) {
            return 0;
        }
        // 线程池
        ExecutorService exec = new ThreadPoolExecutor(3,10,60 * 5,TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>());
        ((ThreadPoolExecutor)exec).allowCoreThreadTimeOut(true);
        int  total      = liveShowRepositoryI.count(LiveShowQuery.builder().statusArray(ALL_LIVE_STATUS).build());
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
                LiveUpdateTask liveUpdateTask = new LiveUpdateTask(ALL_LIVE_STATUS,baseQuery,null);
                futuresResult.add(exec.submit(liveUpdateTask));
            } catch(Exception e) {
                logger.error("LiveLoadBO load failed {}",e);
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

    public boolean update(LiveDTO liveDTO) {
        if(liveDTO == null) {
            return false;
        }
        LiveEntity liveEntity = liveAssembler.assemble(liveDTO);
        return esUpsertManager.upsertLiveEntity(liveEntity);
    }
}
