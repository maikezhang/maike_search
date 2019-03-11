package cn.idongjia.divine.lib.pojo.response.live.general;

import cn.idongjia.divine.lib.pojo.response.ClientObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lc
 * @create at 2018/8/9.
 */
@Getter
@Setter
@ToString
public class ItemCO extends ClientObject {
    private String title;
    private String picture;
    private Long   price;
    private Long   itemId;

}
