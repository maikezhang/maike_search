package cn.idongjia.divine.db.mybatis.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import cn.idongjia.common.base.Base;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 描述：拍品扩展表 Database Object
 *
 * @author lc
 * @date 2018/08/16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemExtDO extends Base {

    /**
     * 商品编号
     */
    private Long iid;

    /**
     * 匠人uid
     */
    private Long cuid;

    /**
     * 尺寸
     */
    private String size;

    /**
     * 规格
     */
    private String standard;

    /**
     * 材质
     */
    private String material;

    /**
     * 年代
     */
    private String age;

    /**
     * 作者
     */
    private String author;

    /**
     * 工艺
     */
    private String craft;

    /**
     * 瑕疵
     */
    private String flaw;

    /**
     * 状态:0-已作废,1-待审核,2-修改中,3-已回退,4-审核中,5-已通过审核,6-已可用
     */
    private Integer status;

    /**
     * 16:9封面图
     */
    private String coverrect;

    /**
     * 商品来源:0-管理员,1-匠人
     */
    private Integer source;

    /**
     * 产地
     */
    private String nativee;

    /**
     * 0-普通方式,1-拍卖,2-搭伙,3-众筹
     */
    private Integer saletype;

}
