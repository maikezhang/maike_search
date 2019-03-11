package cn.idongjia.divine.aggregation.factory;

import cn.idongjia.divine.aggregation.AggregateProperty;
import cn.idongjia.se.lib.dto.AggDTO;
import cn.idongjia.se.lib.engine.query.LeafQuery;
import cn.idongjia.se.lib.engine.query.QueryArgs;
import cn.idongjia.se.lib.entities.EsEntity;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregations;

import java.util.List;
import java.util.function.Function;

/**
 * @author lc
 * @create at 2018/9/6.
 */
public abstract class AbstractAggregationFactory<U extends AggDTO,E extends EsEntity> {
    protected QueryArgs<E,U>    queryArgs;
    protected LeafQuery         query;
    protected AggregateProperty aggregateProperty;
    protected Integer           aggSize;

    public AbstractAggregationFactory(QueryArgs<E,U> queryArgs,LeafQuery query,AggregateProperty aggregateProperty,Integer aggSize) {
        this.queryArgs = queryArgs;
        this.query = query;
        this.aggregateProperty = aggregateProperty;
        this.aggSize=aggSize;
    }

    public abstract List<AbstractAggregationBuilder> getAggregationBuilder();

    public abstract Function<Aggregations,List<U>> getAggretionFunction();

}
