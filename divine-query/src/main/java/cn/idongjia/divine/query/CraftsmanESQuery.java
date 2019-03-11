package cn.idongjia.divine.query;

import cn.idongjia.se.lib.engine.annotations.QueryField;
import cn.idongjia.se.lib.engine.annotations.QueryFieldType;
import cn.idongjia.se.lib.engine.query.LeafQuery;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author lc
 * @create at 2018/8/7.
 */
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CraftsmanESQuery extends LeafQuery {

    /**
     * 显示类型
     */
    @QueryField(type = QueryFieldType.IN, fieldName = "showType")
    private List<Integer> showTypes;

    /**
     * 匠人名称
     */
    @QueryField(type = QueryFieldType.IN,fieldName = "craftsmanSmart")
    private List<String> craftsmanNameSmart;

    @QueryField(type = QueryFieldType.IS, fieldName = "craftsmanUserId")
    private Long craftsmanUserId;

}
