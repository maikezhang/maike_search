package cn.idongjia.divine.query;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import cn.idongjia.se.lib.engine.annotations.QueryField;
import cn.idongjia.se.lib.engine.annotations.QueryFieldType;
import cn.idongjia.se.lib.engine.query.EsQuery;
import cn.idongjia.se.lib.engine.query.boost.TermsField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author luorenshu(626115221 @ qq.com)
 * @date 2018/6/11 上午10:42
 **/
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryESQuery extends EsQuery {
	
	private static final long serialVersionUID = 3613463531111146464L;
	
	@QueryField(type = QueryFieldType.IN, fieldName = "categoryId")
	private List<Long>		  id;
	@QueryField(type = QueryFieldType.NOT_IN, fieldName = "categoryId")
	private List<Long>		  excludeIds;
	
	@QueryField(type = QueryFieldType.IN)
	private List<String>	  name;
	
	@QueryField(type = QueryFieldType.IS, fieldName = "name")
	private String			  oneName;
	
	@QueryField(type = QueryFieldType.BOOST_TERMS, fieldName = "categoryId")
	private TermsField		  boostIdList;
}
