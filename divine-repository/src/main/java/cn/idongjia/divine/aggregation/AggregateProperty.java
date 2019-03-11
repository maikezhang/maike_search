package cn.idongjia.divine.aggregation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.elasticsearch.search.sort.SortOrder;

/**
 * @author lc
 * @create at 2018/9/6.
 */
@Getter
@Setter
@ToString
public class AggregateProperty {
    /**
     * 聚合字段
     */
    private String            aggField;
    /**
     * 排序
     */
    private SortOrder         sortOrder;
    /**
     * 排序字段
     */
    private String            sortField;
    /**
     * 显示条数
     */
    private int               limit;
    /**
     * 下一层聚合
     */
    private AggregateProperty aggregateProperty;
}
