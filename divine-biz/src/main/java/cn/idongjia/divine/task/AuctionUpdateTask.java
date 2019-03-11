package cn.idongjia.divine.task;

import cn.idongjia.common.query.BaseSearch;
import cn.idongjia.divine.assembler.AuctionItemAssembler;
import cn.idongjia.divine.db.es.entity.AuctionEntity;
import cn.idongjia.divine.db.mybatis.mapper.kaipao.AuctionMapper;
import cn.idongjia.divine.db.mybatis.pojo.AuctionDO;
import cn.idongjia.divine.db.mybatis.query.AuctionQuery;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.utils.SpringBeanLoader;
import cn.idongjia.util.Utils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author lc
 */
@Slf4j
public class AuctionUpdateTask implements Callable<Integer> {

    private AuctionMapper        auctionMapper        = SpringBeanLoader.getBean("auctionMapper",AuctionMapper.class);
    private AuctionItemAssembler auctionItemAssembler = SpringBeanLoader.getBean("auctionItemAssembler",AuctionItemAssembler.class);
    private ESUpsertManager      esUpsertManager      = SpringBeanLoader.getBean("esUpsertManager",ESUpsertManager.class);
    private List<Integer>        statusArray;

    private BaseSearch baseSearch;

    public AuctionUpdateTask(List<Integer> statusArray,BaseSearch baseSearch) {
        this.baseSearch = baseSearch;
        this.statusArray = statusArray;
    }

    @Override
    public Integer call() {
        AuctionQuery        liveShowQuery       = AuctionQuery.builder().statusArray(statusArray).page(baseSearch.getPage()).limit(baseSearch.getLimit()).build();
        List<AuctionDO>     auctionDOS          = auctionMapper.select(liveShowQuery);
        List<AuctionEntity> auctionItemEntities = auctionItemAssembler.assemble(auctionDOS);
        if(!Utils.isEmpty(auctionItemEntities)) {
            esUpsertManager.bulkAuctionUpsert(AuctionEntity.indexName,AuctionEntity.typeName,auctionItemEntities);
        }
        return auctionItemEntities.size();

    }
}
