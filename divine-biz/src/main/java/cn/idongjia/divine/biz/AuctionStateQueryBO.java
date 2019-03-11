package cn.idongjia.divine.biz;

import cn.idongjia.divine.convertor.ConvertorI;
import cn.idongjia.divine.db.es.entity.AuctionStateEntity;
import cn.idongjia.divine.lib.pojo.request.auction.AuctionStateQry;
import cn.idongjia.divine.lib.pojo.request.sort.BizType;
import cn.idongjia.divine.lib.pojo.response.MultiResponse;
import cn.idongjia.divine.lib.pojo.response.session.AuctionStateCO;
import cn.idongjia.divine.manager.EsSearchManager;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.se.lib.entities.EsSearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/8/7.
 */
@Component
@Slf4j
public class AuctionStateQueryBO extends BaseQueryBO<AuctionStateQry,AuctionStateEntity> {

    @Resource
    private ConvertorI<AuctionStateCO,AuctionStateEntity> auctionStateEntityConvertor;

    public MultiResponse<AuctionStateCO> query(AuctionStateQry auctionStateQry) {
        EsSearchResult           esSearchResult       = query(BizType.ModuleType.AUCTION_STATE,auctionStateQry,AuctionStateEntity.class);
        List<AuctionStateEntity> auctionStateEntities = esSearchResult.getData();
        List<AuctionStateCO>     auctionStateCOS      = new ArrayList<>();
        if(!Utils.isEmpty(auctionStateEntities)) {
            auctionStateCOS = auctionStateEntities.stream().map(auctionStateEntity -> {
                return auctionStateEntityConvertor.dataToClient(auctionStateEntity);
            }).collect(Collectors.toList());
        }
        return MultiResponse.of(auctionStateCOS,esSearchResult.getTotal().intValue());
    }
}
