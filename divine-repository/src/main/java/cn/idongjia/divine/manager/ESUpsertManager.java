package cn.idongjia.divine.manager;

import cn.idongjia.desert.dto.WordsDTO;
import cn.idongjia.desert.query.BatchSegQuery;
import cn.idongjia.desert.query.SegQuery;
import cn.idongjia.divine.annotation.Elapsed;
import cn.idongjia.divine.db.es.entity.AuctionEntity;
import cn.idongjia.divine.db.es.entity.AuctionStateEntity;
import cn.idongjia.divine.db.es.entity.LiveEntity;
import cn.idongjia.divine.db.es.entity.LiveSpecialCraftsmanEntity;
import cn.idongjia.divine.db.es.entity.SessionEntity;
import cn.idongjia.divine.db.es.entity.SessionLiveEntity;
import cn.idongjia.divine.db.es.entity.ZooMessageEntity;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.se.lib.engine.index.RetryIndexFunction;
import cn.idongjia.se.lib.engine.query.LeafQuery;
import cn.idongjia.se.lib.entities.BasicEntity;
import cn.idongjia.se.lib.entities.UpdateByQueryResult;
import cn.idongjia.se.lib.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/8/3.
 */
@Component("esUpsertManager")
@Slf4j
public class ESUpsertManager {
    @Resource
    private BaseService esService;

    @Resource
    private SegManager segManager;

    private <T extends BasicEntity> boolean retryUpsert(String index,String type,T t) {
        RetryIndexFunction retryIndexFunction = () -> {
            boolean success = false;
            try {
                success = esService.upsert(index,type,t);
            } catch(Exception e) {
                log.error("尝试保存失败{} {}",t,e);
            }

            return success;
        };
        boolean success = esService.retryIndex(retryIndexFunction);
        if(!success) {
            log.error("数据保存失败{}",t);
        }
        log.info("index {} type {} data {}" ,index,type,t);
        return success;

    }

    private <T extends BasicEntity> boolean retryBulkUpsert(String index,String type,List<T> entities) {
        RetryIndexFunction retryIndexFunction = () -> {
            boolean success = false;
            try {
                success = esService.bulkUpsert(index,type,entities);
            } catch(Exception e) {
                //业务端处理异常信息
                log.error("尝试保存失败{} {}",entities,e);
            }

            return success;
        };
        boolean success = esService.retryIndex(retryIndexFunction);
        if(!success) {
            log.error("数据保存失败{}",entities);
        }
        log.debug("index {} type {} data {}" ,index,type,entities);

        return success;

    }

    @Elapsed
    public boolean upsertLiveEntity(LiveEntity liveEntity) {
        String         title = liveEntity.getTitle();
        SegQuery       query = new SegQuery(title);
        List<WordsDTO> words = segManager.segForIndex(query);
        if(Utils.isNotEmpty(words)) {
            List<String> wordList = words.stream().map(word -> word.getWordStr()).collect(Collectors.toList());
            liveEntity.setTitleSmart(wordList);
        }
        String   craftsmanName       = liveEntity.getCraftsmanName();
        SegQuery craftsmanTitleQuery = new SegQuery(craftsmanName);

        List<WordsDTO> nameWords = segManager.segForIndex(craftsmanTitleQuery);
        if(Utils.isNotEmpty(nameWords)) {
            List<String> wordList = nameWords.stream().map(word -> word.getWordStr()).collect(Collectors.toList());
            liveEntity.setCraftsmanSmart(wordList);
        }

        return retryUpsert(LiveEntity.indexName,LiveEntity.typeName,liveEntity);
    }

    @Elapsed
    public boolean upsertAuctionEntity(String index,String type,AuctionEntity auctionEntity) {
        String         title = auctionEntity.getItemTitle();
        SegQuery       query = new SegQuery(title);
        List<WordsDTO> words = segManager.segForIndex(query);
        if(Utils.isNotEmpty(words)) {
            List<String> wordList = words.stream().map(word -> word.getWordStr()).collect(Collectors.toList());
            auctionEntity.setItemTitleSmart(wordList);
        }
        String   craftsmanName       = auctionEntity.getCraftsmanName();
        SegQuery craftsmanTitleQuery = new SegQuery(craftsmanName);

        List<WordsDTO> nameWords = segManager.segForIndex(craftsmanTitleQuery);
        if(Utils.isNotEmpty(nameWords)) {
            List<String> wordList = nameWords.stream().map(word -> word.getWordStr()).collect(Collectors.toList());
            auctionEntity.setCraftsmanNameSmart(wordList);
        }

        return retryUpsert(index,type,auctionEntity);
    }

    public boolean buldLiveCraftsmanUpsert(List<LiveSpecialCraftsmanEntity> liveSpecialCraftsmanEntities) {
        Map<String,LiveSpecialCraftsmanEntity> liveSpecialCraftsmanEntityMap = liveSpecialCraftsmanEntities.stream().collect(Collectors.toMap(v1 -> v1.getId(),v1 -> v1,(v1,v2) -> v1));
        Map<String,String>                     wordMap                       = liveSpecialCraftsmanEntities.stream().filter(entity -> !Utils.isEmpty(entity.getCraftsmanName())).collect(Collectors.toMap(v1 -> v1.getId(),v1 -> v1.getCraftsmanName(),(v1,v2) -> v1));
        if(!Utils.isEmpty(wordMap)) {
            List<String>         names    = wordMap.values().stream().collect(Collectors.toList());
            List<List<WordsDTO>> wordList = segManager.batchSegForIndex(new BatchSegQuery(names));
            if(!Utils.isEmpty(wordList)) {
                int i = 0;
                for(Map.Entry<String,String> entity: wordMap.entrySet()) {
                    String         id    = entity.getKey();
                    List<WordsDTO> words = wordList.get(i);
                    if(!Utils.isEmpty(words)) {
                        List<String>               segs                       = words.stream().map(word -> word.getWordStr()).collect(Collectors.toList());
                        LiveSpecialCraftsmanEntity liveSpecialCraftsmanEntity = liveSpecialCraftsmanEntityMap.get(id);
                        liveSpecialCraftsmanEntity.setCraftsmanSmart(segs);
                    }
                    i++;
                }

            }
        }
        return retryBulkUpsert(LiveSpecialCraftsmanEntity.indexName,LiveSpecialCraftsmanEntity.typeName,liveSpecialCraftsmanEntities);
    }

    public boolean liveCraftsmanUpsert(LiveSpecialCraftsmanEntity liveSpecialCraftsmanEntity) {
        List<WordsDTO> wordList = segManager.segForIndex(new SegQuery(liveSpecialCraftsmanEntity.getCraftsmanName()));
        if(Utils.isNotEmpty(wordList)) {
            List<String> words = wordList.stream().map(WordsDTO::getWordStr).collect(Collectors.toList());
            liveSpecialCraftsmanEntity.setCraftsmanSmart(words);
        }
        return retryUpsert(LiveSpecialCraftsmanEntity.indexName,LiveSpecialCraftsmanEntity.typeName,liveSpecialCraftsmanEntity);
    }

    /**
     * 二期接口，给kafka那边用的，后期自己也可用
     *
     * @param entities
     * @return
     */
    @Elapsed
    public boolean bulkLiveUpsert(List<LiveEntity> entities) {
        Map<String,LiveEntity> liveEntityMap = entities.stream().collect(Collectors.toMap(v1 -> v1.getId(),v1 -> v1,(v1,v2) -> v1));
        Map<String,String>     wordMap       = entities.stream().filter(entity -> !Utils.isEmpty(entity.getTitle())).collect(Collectors.toMap(v1 -> v1.getId(),v1 -> v1.getTitle(),(v1,v2) -> v1));
        if(!Utils.isEmpty(wordMap)) {
            List<String>         titles   = wordMap.values().stream().collect(Collectors.toList());
            List<List<WordsDTO>> wordList = segManager.batchSegForIndex(new BatchSegQuery(titles));
            if(!Utils.isEmpty(wordList)) {
                int i = 0;
                for(Map.Entry<String,String> entity: wordMap.entrySet()) {
                    String         id    = entity.getKey();
                    List<WordsDTO> words = wordList.get(i);
                    if(!Utils.isEmpty(words)) {
                        List<String> segs       = words.stream().map(word -> word.getWordStr()).collect(Collectors.toList());
                        LiveEntity   liveEntity = liveEntityMap.get(id);
                        liveEntity.setTitleSmart(segs);
                    }
                    i++;
                }

            }
        }
        Map<String,String> craftsmanNameWord = entities.stream().filter(entity -> !Utils.isEmpty(entity.getCraftsmanName())).collect(Collectors.toMap(v1 -> v1.getId(),v1 -> v1.getCraftsmanName(),(v1,v2) -> v1));
        if(!Utils.isEmpty(craftsmanNameWord)) {
            List<String>         titles   = craftsmanNameWord.values().stream().collect(Collectors.toList());
            List<List<WordsDTO>> wordList = segManager.batchSegForIndex(new BatchSegQuery(titles));
            if(!Utils.isEmpty(wordList)) {
                int i = 0;
                for(Map.Entry<String,String> entity: craftsmanNameWord.entrySet()) {
                    String         id    = entity.getKey();
                    List<WordsDTO> words = wordList.get(i);
                    if(!Utils.isEmpty(words)) {
                        List<String> segs       = words.stream().map(word -> word.getWordStr()).collect(Collectors.toList());
                        LiveEntity   liveEntity = liveEntityMap.get(id);
                        liveEntity.setCraftsmanSmart(segs);
                    }
                    i++;

                }

            }
        }

        return retryBulkUpsert(LiveEntity.indexName,LiveEntity.typeName,entities);
    }

    public boolean bulkSessionUpsert(List<SessionEntity> sessionEntities) {
        log.debug("sessionEntities==>{}",sessionEntities);
        return retryBulkUpsert(SessionEntity.indexName,SessionEntity.typeName,sessionEntities);
    }

    @Elapsed
    public boolean sessionUpsert(SessionEntity sessionEntity) {
        log.info("sessionEntity==>{}",sessionEntity);
        return retryUpsert(SessionEntity.indexName,SessionEntity.typeName,sessionEntity);
    }

    @Elapsed
    public boolean bulkAuctionUpsert(String index,String type,List<AuctionEntity> entities) {
        Map<String,AuctionEntity> auctionItemEntityMap = entities.stream().collect(Collectors.toMap(v1 -> v1.getId(),v1 -> v1,(v1,v2) -> v1));
        Map<String,String>        wordMap              = entities.stream().filter(entity -> !Utils.isEmpty(entity.getItemTitle())).collect(Collectors.toMap(v1 -> v1.getId(),v1 -> v1.getItemTitle(),(v1,v2) -> v1));
        if(!Utils.isEmpty(wordMap)) {
            List<String>         titles   = wordMap.values().stream().collect(Collectors.toList());
            List<List<WordsDTO>> wordList = segManager.batchSegForIndex(new BatchSegQuery(titles));
            if(!Utils.isEmpty(wordList)) {
                int i = 0;
                for(Map.Entry<String,String> entity: wordMap.entrySet()) {
                    String         id    = entity.getKey();
                    List<WordsDTO> words = wordList.get(i);
                    if(!Utils.isEmpty(words)) {
                        List<String>  segs              = words.stream().map(word -> word.getWordStr()).collect(Collectors.toList());
                        AuctionEntity auctionItemEntity = auctionItemEntityMap.get(id);
                        auctionItemEntity.setItemTitleSmart(segs);
                    }
                    i++;

                }

            }
        }
        Map<String,String> craftsmanNameWord = entities.stream().filter(entity -> !Utils.isEmpty(entity.getItemTitle())).collect(Collectors.toMap(v1 -> v1.getId(),v1 -> v1.getCraftsmanName(),(v1,v2) -> v1));
        if(!Utils.isEmpty(craftsmanNameWord)) {
            List<String>         titles   = craftsmanNameWord.values().stream().collect(Collectors.toList());
            List<List<WordsDTO>> wordList = segManager.batchSegForIndex(new BatchSegQuery(titles));
            if(!Utils.isEmpty(wordList)) {
                int i = 0;
                for(Map.Entry<String,String> entity: craftsmanNameWord.entrySet()) {
                    String         id    = entity.getKey();
                    List<WordsDTO> words = wordList.get(i);
                    if(!Utils.isEmpty(words)) {
                        List<String>  segs              = words.stream().map(word -> word.getWordStr()).collect(Collectors.toList());
                        AuctionEntity auctionItemEntity = auctionItemEntityMap.get(id);
                        auctionItemEntity.setCraftsmanNameSmart(segs);
                    }
                    i++;

                }

            }
        }
        log.debug("auctionEntities==>{}",entities);
        return retryBulkUpsert(index,type,entities);
    }

    @Elapsed
    public boolean updateByQuery(String index,String type,LeafQuery query,Map<String,Object> updateValues) {
        UpdateByQueryResult updateByQueryResult = esService.updateByQuery(index,type,query,updateValues);
        if(updateByQueryResult.getTotal() > updateByQueryResult.getUpdated()) {
            updateByQueryResult = esService.updateByQuery(index,type,query,updateValues);
        }
        return updateByQueryResult.getTotal().equals(updateByQueryResult.getUpdated());
    }

    /**
     * 二期接口，给kafka那边用的，后期自己也可用
     *
     * @param entities
     * @return
     */
    @Elapsed
    public boolean bulkAuctionStateUpsert(List<AuctionStateEntity> entities) {

        log.info("auctionStateEntities==>{}",entities);

        return retryBulkUpsert(AuctionStateEntity.indexName,AuctionStateEntity.typeName,entities);
    }

    @Elapsed
    public boolean auctionStateUpsert(AuctionStateEntity entity) {
        return retryUpsert(AuctionStateEntity.indexName,AuctionStateEntity.typeName,entity);
    }

    @Elapsed
    public boolean bulkSessionLiveUpsert(List<SessionLiveEntity> entities) {
        if (Utils.isEmpty(entities)) {
            return false;
        }
        return retryBulkUpsert(SessionLiveEntity.indexName, SessionLiveEntity.typeName,entities);
    }

    @Elapsed
    public boolean bulkZooMessageUpsert(List<ZooMessageEntity> zooMessageEntities){
        log.debug("zooMessageEntities==>>{}",zooMessageEntities);
        return retryBulkUpsert(ZooMessageEntity.indexName,ZooMessageEntity.typeName,zooMessageEntities);

    }

}
