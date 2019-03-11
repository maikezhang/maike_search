package cn.idongjia.divine.biz;

import cn.idongjia.divine.aggregation.AggregationEnums;
import cn.idongjia.divine.aggregation.AggregationFactory;
import cn.idongjia.divine.aggregation.factory.AbstractAggregationFactory;
import cn.idongjia.divine.convertor.ConvertorI;
import cn.idongjia.divine.db.es.entity.AuctionEntity;
import cn.idongjia.divine.db.es.entity.AuctionPriceDTO;
import cn.idongjia.divine.db.es.entity.SessionAuctionDTO;
import cn.idongjia.divine.lib.pojo.request.auction.AuctionPriceGroupQry;
import cn.idongjia.divine.lib.pojo.request.auction.AuctionQry;
import cn.idongjia.divine.lib.pojo.request.sort.BizType;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.lib.pojo.response.MultiResponse;
import cn.idongjia.divine.lib.pojo.response.auction.AuctionCO;
import cn.idongjia.divine.lib.pojo.response.auction.AuctionItemCO;
import cn.idongjia.divine.lib.pojo.response.auction.AuctionPriceCO;
import cn.idongjia.divine.lib.pojo.response.auction.SessionAuctionCO;
import cn.idongjia.divine.lib.pojo.response.auction.SessionAuctionRel;
import cn.idongjia.divine.manager.EsSearchManager;
import cn.idongjia.divine.query.factory.AbstractQueryFactory;
import cn.idongjia.divine.query.factory.QueryFactory;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.divine.utils.exception.DivineException;
import cn.idongjia.se.lib.engine.query.LeafQuery;
import cn.idongjia.se.lib.engine.query.QueryArgs;
import cn.idongjia.se.lib.entities.EsSearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author lc
 * @create at 2018/8/7.
 */
@Component
@Slf4j
public class AuctionQueryBO {

    @Resource
    private EsSearchManager                     esSearchManager;
    @Resource
    private ConvertorI<AuctionCO,AuctionEntity> auctionItemEntityConvertor;

    public MultiResponse<AuctionCO> query(AuctionQry auctionQry) {
        AbstractQueryFactory       abstractQueryFactory = QueryFactory.getFactory(BizType.ModuleType.AUCTION,auctionQry);
        LeafQuery                  query                = abstractQueryFactory.getQuery();
        QueryArgs<AuctionEntity,?> queryArgs            = new QueryArgs();
        queryArgs.setType(AuctionEntity.typeName);
        queryArgs.setIndex(AuctionEntity.indexName);
        queryArgs.setClazz(AuctionEntity.class);
        ESSort esSort = abstractQueryFactory.getSort();
        query.setSortList(esSort.getSorts());
        queryArgs.setQuery(query);
        EsSearchResult      esSearchResult      = esSearchManager.search(queryArgs);
        List<AuctionEntity> auctionItemEntities = esSearchResult.getData();
        List<AuctionCO>     auctionCOS          = new ArrayList<>();
        if(Utils.isNotEmpty(auctionItemEntities)) {
            auctionCOS = auctionItemEntities.stream().map(auctionItemEntity -> {
                return auctionItemEntityConvertor.dataToClient(auctionItemEntity);
            }).collect(Collectors.toList());
        }
        return MultiResponse.of(auctionCOS,esSearchResult.getTotal().intValue());
    }

    public MultiResponse<AuctionPriceCO> priceAggSearch(AuctionQry auctionQry) {
        AbstractQueryFactory                     abstractQueryFactory = QueryFactory.getFactory(BizType.ModuleType.AUCTION,auctionQry);
        LeafQuery                                query                = abstractQueryFactory.getQuery();
        QueryArgs<AuctionEntity,AuctionPriceDTO> queryArgs            = new QueryArgs();
        queryArgs.setType(AuctionEntity.typeName);
        queryArgs.setIndex(AuctionEntity.indexName);
        queryArgs.setClazz(AuctionEntity.class);
        ESSort esSort = abstractQueryFactory.getSort();
        query.setSortList(esSort.getSorts());
        query.setNum(0);
        query.setStart(0);
        queryArgs.setQuery(query);
        //对聚合价格进行排序
        AuctionPriceGroupQry auctionPriceGroupQry = auctionQry.getAuctionPriceGroupQry();
        if(auctionPriceGroupQry != null) {
            AbstractAggregationFactory abstractAggregationFactory = AggregationFactory.get(AggregationEnums.AggregationType.AUCTION_PRICE,queryArgs,query,auctionQry);
            if(abstractAggregationFactory != null) {
                queryArgs.setAggregationBuilders(abstractAggregationFactory.getAggregationBuilder());
                queryArgs.setParseAggFunction(abstractAggregationFactory.getAggretionFunction());

            }
        }

        EsSearchResult        esSearchResult   = esSearchManager.search(queryArgs);
        List<AuctionPriceDTO> auctionPriceDTOS = esSearchResult.getAggs();
        List<AuctionPriceCO>  auctionPriceCOS  = new ArrayList<>();
        if(Utils.isNotEmpty(auctionPriceDTOS)) {
            auctionPriceCOS = auctionPriceDTOS.stream().map(auctionPriceDTO -> {
                AuctionPriceCO auctionPriceCO = new AuctionPriceCO();
                auctionPriceCO.setId(auctionPriceDTO.getId());
                List<AuctionEntity> auctionEntities = auctionPriceDTO.getEntities();
                if(Utils.isNotEmpty(auctionEntities)) {
                    List<AuctionCO> auctionCOS = auctionEntities.stream().map(auctionEntity -> {
                        return auctionItemEntityConvertor.dataToClient(auctionEntity);
                    }).collect(toList());
                    auctionPriceCO.setAuctionCOS(auctionCOS);
                }

                return auctionPriceCO;
            }).collect(Collectors.toList());
        }
        return MultiResponse.ofWithoutTotal(auctionPriceCOS);
    }

    public MultiResponse<SessionAuctionRel> auctionGroup(AuctionQry auctionQry) {
        if(Utils.isEmpty(auctionQry.getSessionIds())) {
            throw DivineException.failure("请传入专场id");
        }
        QueryArgs<AuctionEntity,?> queryArgs            = new QueryArgs();
        AbstractQueryFactory       abstractQueryFactory = QueryFactory.getFactory(BizType.ModuleType.AUCTION,auctionQry);
        ESSort                     esSort               = abstractQueryFactory.getSort();
        LeafQuery                  leafQuery            = abstractQueryFactory.getQuery();
        leafQuery.setSortList(esSort.getSorts());
        queryArgs.setQuery(leafQuery);
        queryArgs.setType(AuctionEntity.typeName);
        queryArgs.setIndex(AuctionEntity.indexName);
        queryArgs.setClazz(AuctionEntity.class);
        EsSearchResult          esSearchResult     = esSearchManager.search(queryArgs);
        List<AuctionEntity>     auctionEntities    = esSearchResult.getData();
        List<SessionAuctionRel> sessionAuctionRels = new ArrayList<>();
        if(Utils.isNotEmpty(auctionEntities)) {
            sessionAuctionRels = auctionEntities.stream().collect(Collectors.groupingBy(AuctionEntity::getSessionId)).entrySet().stream().map(entry -> {
                SessionAuctionDTO sessionAuctionDTO = new SessionAuctionDTO();
                Long              key               = entry.getKey();
                sessionAuctionDTO.setSessionId(key);
                List<AuctionEntity> entities          = entry.getValue();
                SessionAuctionRel   sessionAuctionRel = null;
                int                 minTotal          = auctionQry.getMinItemTotal() == null ? 0 : auctionQry.getMinItemTotal();
                if(Utils.isNotEmpty(entities) && entities.size() >= minTotal) {
                    sessionAuctionRel = new SessionAuctionRel();
                    sessionAuctionRel.setSessionId(sessionAuctionDTO.getSessionId());
                    int                 maxTotal = auctionQry.getMaxItemTotal() == null ? entities.size() : auctionQry.getMaxItemTotal();
                    List<AuctionEntity> subAuctionEntities;
                    if(maxTotal < entities.size()) {
                        subAuctionEntities = entities.subList(0,maxTotal);
                    } else {
                        subAuctionEntities = entities;
                    }
                    List<AuctionItemCO> auctionItemCOS = subAuctionEntities.stream().map(auctionEntity -> {
                        AuctionItemCO auctionItemCO = new AuctionItemCO();
                        auctionItemCO.setWeight(auctionEntity.getWeight());
                        auctionItemCO.setState(auctionEntity.getState());
                        auctionItemCO.setCover(auctionEntity.getCover());
                        auctionItemCO.setTitle(auctionEntity.getItemTitle());
                        auctionItemCO.setPrice(auctionEntity.getPrice());
                        auctionItemCO.setItemId(auctionEntity.getItemId());
                        auctionItemCO.setLastOfferUserId(auctionEntity.getOfferUserId());
                        auctionItemCO.setCurrentPrice(auctionEntity.getCurrentPrice());
                        auctionItemCO.setStartTime(auctionEntity.getStartTime());
                        auctionItemCO.setLastOfferUserName(auctionEntity.getOfferUserName());
                        return auctionItemCO;
                    }).collect(Collectors.toList());
                    sessionAuctionRel.setAuctionItemCOS(auctionItemCOS);
                }
                return sessionAuctionRel;
            }).filter(sessionAuctionRel -> sessionAuctionRel != null).collect(Collectors.toList());
        }
        return MultiResponse.ofWithoutTotal(sessionAuctionRels);
    }

    public MultiResponse<SessionAuctionCO> groupBySession(AuctionQry auctionQry) {
        if(Utils.isEmpty(auctionQry.getSessionIds())) {
            throw DivineException.failure("请传入专场id");
        }
        QueryArgs<AuctionEntity,?> queryArgs            = new QueryArgs();
        AbstractQueryFactory       abstractQueryFactory = QueryFactory.getFactory(BizType.ModuleType.AUCTION,auctionQry);
        ESSort                     esSort               = abstractQueryFactory.getSort();
        LeafQuery                  leafQuery            = abstractQueryFactory.getQuery();
        leafQuery.setSortList(esSort.getSorts());
        queryArgs.setQuery(leafQuery);
        queryArgs.setType(AuctionEntity.typeName);
        queryArgs.setIndex(AuctionEntity.indexName);
        queryArgs.setClazz(AuctionEntity.class);
        EsSearchResult         esSearchResult    = esSearchManager.search(queryArgs);
        List<AuctionEntity>    auctionEntities   = esSearchResult.getData();
        List<SessionAuctionCO> sessionAuctionCOS = new ArrayList<>();
        if(Utils.isNotEmpty(auctionEntities)) {
            sessionAuctionCOS = auctionEntities.stream().collect(Collectors.groupingBy(AuctionEntity::getSessionId)).entrySet().stream().map(entry -> {
                Long                key              = entry.getKey();
                List<AuctionEntity> entities         = entry.getValue();
                int                 minTotal         = auctionQry.getMinItemTotal() == null ? 0 : auctionQry.getMinItemTotal();
                SessionAuctionCO    sessionAuctionCO = new SessionAuctionCO();
                sessionAuctionCO.setSessionId(key);
                if(Utils.isNotEmpty(entities) && entities.size() >= minTotal) {
                    int                 maxTotal = auctionQry.getMaxItemTotal() == null ? entities.size() : auctionQry.getMaxItemTotal();
                    List<AuctionEntity> subAuctionEntities;
                    if(maxTotal < entities.size()) {
                        subAuctionEntities = entities.subList(0,maxTotal);
                    } else {
                        subAuctionEntities = entities;
                    }
                    List<AuctionCO> auctionCOS = subAuctionEntities.stream().map(auctionEntity -> {
                        return auctionItemEntityConvertor.dataToClient(auctionEntity);
                    }).collect(Collectors.toList());
                    sessionAuctionCO.setAuctionCOS(auctionCOS);
                }
                return sessionAuctionCO;
            }).filter(sessionAuctionCO -> sessionAuctionCO != null).collect(Collectors.toList());
        }
        return MultiResponse.ofWithoutTotal(sessionAuctionCOS);
    }
}
