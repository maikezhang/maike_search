package cn.idongjia.divine.db.es.entity;

import cn.idongjia.se.lib.entities.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import cn.idongjia.se.lib.engine.annotations.EsField;
import cn.idongjia.se.lib.engine.annotations.EsFieldType;
import cn.idongjia.se.lib.entities.BasicEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author lc
 * @create at 2018/8/9.
 */
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemEntity extends BaseEntity {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@EsField(type = EsFieldType.Keyword)
    private String title;
    @EsField(type = EsFieldType.Keyword)
    private String picture;
    @EsField(type = EsFieldType.Long)
    private Long   price;
    @EsField(type = EsFieldType.Long)
    private Long   itemId;
}
