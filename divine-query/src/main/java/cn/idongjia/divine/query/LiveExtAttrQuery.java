package cn.idongjia.divine.query;

import com.fasterxml.jackson.annotation.JsonInclude;

import cn.idongjia.se.lib.engine.annotations.QueryField;
import cn.idongjia.se.lib.engine.annotations.QueryFieldType;
import cn.idongjia.se.lib.engine.query.EsQuery;
import cn.idongjia.se.lib.engine.query.boost.TermsField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author lc
 * @date 2018/7/13 下午2:15
 **/
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LiveExtAttrQuery extends EsQuery {
	
	@QueryField(type = QueryFieldType.BOOST_TERMS)
	private TermsField id;
}
