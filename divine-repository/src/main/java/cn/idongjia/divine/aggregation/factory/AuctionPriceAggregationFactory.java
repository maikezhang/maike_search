package cn.idongjia.divine.aggregation.factory;

import cn.idongjia.divine.aggregation.AggregateProperty;
import cn.idongjia.divine.db.es.entity.AuctionEntity;
import cn.idongjia.divine.db.es.entity.AuctionPriceDTO;
import cn.idongjia.se.lib.engine.query.LeafQuery;
import cn.idongjia.se.lib.engine.query.QueryArgs;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHitsAggregationBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * @author lc
 * @create at 2018/9/6.
 */
public class AuctionPriceAggregationFactory extends AbstractAggregationFactory<AuctionPriceDTO,AuctionEntity> {
    public AuctionPriceAggregationFactory(QueryArgs queryArgs,LeafQuery query,AggregateProperty aggregateField,Integer aggSize) {
        super(queryArgs,query,aggregateField,aggSize);
    }

    @Override
    public List<AbstractAggregationBuilder> getAggregationBuilder() {
        TermsAggregationBuilder   termsAggregationBuilder = AggregationBuilders.terms("first_agg").field(aggregateProperty.getAggField()).size(aggSize);
        TopHitsAggregationBuilder hitsAggregationBuilder  =
                AggregationBuilders.topHits("hit_agg").from(0).sort(aggregateProperty.getSortField(),aggregateProperty.getSortOrder()).size(aggregateProperty.getLimit());
        termsAggregationBuilder.subAggregation(hitsAggregationBuilder);
        return Arrays.asList(termsAggregationBuilder);
    }

    @Override
    public Function<Aggregations,List<AuctionPriceDTO>> getAggretionFunction() {
        return aggregations -> {
            Terms                 agg             = aggregations.get("first_agg");
            List<AuctionPriceDTO> aggregationDTOS = new ArrayList<>();
            for(Terms.Bucket entry: agg.getBuckets()) {
                Aggregations    aggs            = entry.getAggregations();
                AuctionPriceDTO auctionPriceDTO = new AuctionPriceDTO();
                auctionPriceDTO.setId((Long)entry.getKey());
                TopHits             topHits = aggs.get("hit_agg");
                SearchHits          hits    = topHits.getHits();
                List<AuctionEntity> tmpList = Arrays.asList(hits.getHits()).stream().map(query.parseSearchHit(queryArgs.getClazz())).collect(toList());
                auctionPriceDTO.setEntities(tmpList);
                aggregationDTOS.add(auctionPriceDTO);

            }
            return aggregationDTOS;
        };
    }
}
