package cn.idongjia.divine.lib.pojo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.ws.rs.QueryParam;
import java.io.Serializable;

/**
 * @author lc
 * @create at 2018/8/18.
 */

@Setter
@Getter
@ToString
public class UserInfo implements Serializable {
    /**
     * 设备号
     */
    @QueryParam("deviceId")
    private String deviceId;
    /**
     * 用户id
     */
    @QueryParam("userId")
    private Long   userId;
}
