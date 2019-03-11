package cn.idongjia.divine.db.es.entity;

import cn.idongjia.se.lib.dto.AggDTO;
import cn.idongjia.se.lib.entities.BasicEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author lc
 * @create at 2018/8/21.
 */
@Getter
@Setter
@ToString
public class AggregationDTO<E extends BasicEntity> extends AggDTO {
    /**
     * 商品id
     */
    private Long    id;
    private List<E> entities;
}
