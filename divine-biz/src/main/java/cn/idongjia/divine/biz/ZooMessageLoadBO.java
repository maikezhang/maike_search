package cn.idongjia.divine.biz;

import cn.idongjia.common.query.BaseSearch;
import cn.idongjia.divine.assembler.ZooMessageAssambler;
import cn.idongjia.divine.db.es.entity.ZooMessageEntity;
import cn.idongjia.divine.db.mybatis.pojo.ZooMessageDO;
import cn.idongjia.divine.db.mybatis.query.ZooMessageQuery;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.repository.ZooMessageRepositoryI;
import cn.idongjia.divine.task.ZooMessageUpdateTask;
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
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/11/22
 * Time: 下午3:39
 */
@Component
public class ZooMessageLoadBO {

    @Resource
    MappingService mappingService;
    private static final Log logger = LogFactory.getLog(LiveLoadBO.class);

    private static final int           BATCH_SIZE           = 1500;
    public static final  List<Integer> ALL_LIVE_STATUS      = Arrays.asList(-1, 0, 1, 2);
    public static final  List<Integer> ALL_ZOO_MESSAGE_TYPE = Arrays.asList(0, 1, 2, 3, 4, 5);
    @Resource
    private ESUpsertManager       esUpsertManager;
    @Resource
    private ZooMessageAssambler   zooMessageAssambler;
    @Resource
    private ZooMessageRepositoryI zooMessageRepositoryI;


    public boolean loadByZmid(Long zmid) {
        ZooMessageDO           zooMessageDO         = zooMessageRepositoryI.getByPrimaryKey(zmid);
        List<ZooMessageEntity> zooMessageEntityList = zooMessageAssambler.assemble(Arrays.asList(zooMessageDO));
        return esUpsertManager.bulkZooMessageUpsert(zooMessageEntityList);
    }

    public Integer loadZooMessage() {
        boolean success = mappingService.putMapping(ZooMessageEntity.class);
        if (!success) {
            return 0;
        }

        ExecutorService executor = new ThreadPoolExecutor(3, 10, 60 * 5, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

        ((ThreadPoolExecutor) executor).allowCoreThreadTimeOut(true);

//        int count = zooMessageRepositoryI.count(ZooMessageQuery.builder().build());
        long count=zooMessageRepositoryI.maxZmid();

        long page       = count / BATCH_SIZE;
        int  remainPage = count % BATCH_SIZE > 0 ? 1 : 0;
        page += remainPage;

        Semaphore             semp          = new Semaphore(10);
        List<Future<Integer>> futuresResult = new ArrayList<>();
        for (int i = 0; i < page; i++) {
            try {
                semp.acquire();
                final int        pageNow   = i + 1;
                final long       minZmid   = i * BATCH_SIZE;
                final BaseSearch baseQuery = new BaseSearch();
                baseQuery.setPage(pageNow);
                baseQuery.setLimit(BATCH_SIZE);
                ZooMessageUpdateTask zooMessageUpdateTask = new ZooMessageUpdateTask(baseQuery, null, minZmid);
                futuresResult.add(executor.submit(zooMessageUpdateTask));
            } catch (Exception e) {
                logger.error("ZooMessageLoadBO load failed {}", e);
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
