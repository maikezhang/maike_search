package cn.idongjia.divine.biz;

import cn.idongjia.common.query.BaseSearch;
import cn.idongjia.divine.assembler.AssemblerI;
import cn.idongjia.divine.db.es.entity.AuctionEntity;
import cn.idongjia.divine.db.mybatis.pojo.AuctionDO;
import cn.idongjia.divine.db.mybatis.query.AuctionQuery;
import cn.idongjia.divine.dto.AuctionItemDTO;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.query.AuctionESQuery;
import cn.idongjia.divine.repository.AuctionRepositoryI;
import cn.idongjia.divine.task.AuctionUpdateTask;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.se.lib.service.MappingService;
import lombok.extern.slf4j.Slf4j;
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

/**
 * @author lc
 * @create at 2018/8/16.
 */
@Component
@Slf4j
public class AuctionLoadBO {

    @Resource
    MappingService mappingService;
    private static final int                                                BATCH_SIZE      = 500;
    protected            List<Integer>                                      ALL_LIVE_STATUS = Arrays.asList(0,1,2,3,4,5,6);
    @Resource
    private              ESUpsertManager                                    esUpsertManager;
    @Resource
    private              AuctionRepositoryI                                 auctionRepositoryI;
    @Resource
    private              AssemblerI<AuctionEntity,AuctionDO,AuctionItemDTO> auctionItemAssembler;


    public boolean loadByItemId(Long itemId) {
        AuctionDO auctionDO = auctionRepositoryI.getByItemId(itemId);
        if(auctionDO != null) {
            List<AuctionEntity> auctionItemEntities = auctionItemAssembler.assemble(Arrays.asList(auctionDO));
            return esUpsertManager.bulkAuctionUpsert(AuctionEntity.indexName,AuctionEntity.typeName,auctionItemEntities);
        }
        return false;
    }

    public boolean loadByItemIds(List<Long> itemIds) {
        List<AuctionDO> auctionDOs = auctionRepositoryI.list(itemIds);
        if(Utils.isNotEmpty(auctionDOs)) {
            List<AuctionEntity> auctionItemEntities = auctionItemAssembler.assemble(auctionDOs);
            return esUpsertManager.bulkAuctionUpsert(AuctionEntity.indexName,AuctionEntity.typeName,auctionItemEntities);
        }
        return false;
    }

    public boolean loadAuctionDOs(List<AuctionDO> auctionDOs) {
        if(Utils.isNotEmpty(auctionDOs)) {
            List<AuctionEntity> auctionItemEntities = auctionItemAssembler.assemble(auctionDOs);
            return esUpsertManager.bulkAuctionUpsert(AuctionEntity.indexName,AuctionEntity.typeName,auctionItemEntities);
        }
        return false;
    }

    public int load() {
        boolean success = mappingService.putMapping(AuctionEntity.class);
        if(!success) {
            return 0;
        }
        // 线程池
        ExecutorService exec = new ThreadPoolExecutor(3,50,60 * 5,TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>());
        ((ThreadPoolExecutor)exec).allowCoreThreadTimeOut(true);
        int  total      = auctionRepositoryI.count(AuctionQuery.builder().statusArray(ALL_LIVE_STATUS).build());
        long page       = total / BATCH_SIZE;
        int  remainPage = total % BATCH_SIZE > 0 ? 1 : 0;
        page += remainPage;
        Semaphore             semaphore     = new Semaphore(20);
        List<Future<Integer>> futuresResult = new ArrayList<>();
        for(int i = 0;i < page;i++) {
            try {
                semaphore.acquire();
                final int        pageNow   = i + 1;
                final BaseSearch baseQuery = new BaseSearch();
                baseQuery.setPage(pageNow);
                baseQuery.setLimit(BATCH_SIZE);
                AuctionUpdateTask auctionUpdateTask = new AuctionUpdateTask(ALL_LIVE_STATUS,baseQuery);
                futuresResult.add(exec.submit(auctionUpdateTask));
            } catch(Exception e) {
                log.error("GeneralLiveLoadBO load failed {}",e);
            } finally {
                semaphore.release();
            }
        }
        int loadTotal = 0;
        for(Future<Integer> listFuture: futuresResult) {
            try {
                loadTotal += listFuture.get();
            } catch(Exception e) {
                log.error("获取数据失败{}",e);
            }

        }
        return loadTotal;
    }
}
