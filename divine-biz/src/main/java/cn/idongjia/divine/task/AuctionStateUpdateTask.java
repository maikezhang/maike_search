package cn.idongjia.divine.task;

import cn.idongjia.common.query.BaseSearch;
import cn.idongjia.divine.assembler.AuctionStateAssembler;
import cn.idongjia.divine.db.es.entity.AuctionStateEntity;
import cn.idongjia.divine.db.mybatis.mapper.kaipao.AuctionMapper;
import cn.idongjia.divine.db.mybatis.pojo.AuctionStateDO;
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
public class AuctionStateUpdateTask implements Callable<Integer> {

    private static final AuctionMapper         auctionMapper         = SpringBeanLoader.getBean("auctionMapper",AuctionMapper.class);
    private static final AuctionStateAssembler auctionStateAssembler = SpringBeanLoader.getBean("auctionStateAssembler",AuctionStateAssembler.class);
    private              ESUpsertManager       esUpsertManager       = SpringBeanLoader.getBean("esUpsertManager",ESUpsertManager.class);

    private BaseSearch baseSearch;

    public AuctionStateUpdateTask(BaseSearch baseSearch) {
        this.baseSearch = baseSearch;
    }

    @Override
    public Integer call() {
        List<AuctionStateDO>     auctionStateDOS      = auctionMapper.groupCraftsmanAuctionBystate(null,baseSearch.getLimit(),baseSearch.getOffset(), null);
        List<AuctionStateEntity> auctionStateEntities = null;
        if(Utils.isNotEmpty(auctionStateDOS)) {
            auctionStateEntities = auctionStateAssembler.assemble(auctionStateDOS);
            if(Utils.isNotEmpty(auctionStateEntities)) {
                esUpsertManager.bulkAuctionStateUpsert(auctionStateEntities);
            }
        }
        return auctionStateDOS.size();
    }
}
