package cn.idongjia.divine.lib.pojo.request.live;

import cn.idongjia.divine.lib.pojo.request.Page;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author lc
 * @create at 2018/9/3.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class CraftsmanQry extends Page {
    /**
     * 匠人名称
     */
    private String        craftsmanName;
    /**
     * 显示类型
     */
    private List<Integer> showType;
    /**
     * 匠人用户id
     */
    private Long          userId;
}
