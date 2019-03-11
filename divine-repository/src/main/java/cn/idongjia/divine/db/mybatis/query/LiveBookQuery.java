package cn.idongjia.divine.db.mybatis.query;

import cn.idongjia.common.query.BaseSearch;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * 描述： Database Object
 *
 * @author lc
 * @date 2018/08/10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveBookQuery extends BaseSearch {

    /**
     * 唯一ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long uid;

    /**
     * 直播ID
     */
    private Long lid;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private java.util.Date createtm;

    /**
     * 最后修改时间
     */
    private java.util.Date modifiedtm;

}
