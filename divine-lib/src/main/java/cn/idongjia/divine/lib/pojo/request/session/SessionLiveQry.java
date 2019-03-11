package cn.idongjia.divine.lib.pojo.request.session;

import cn.idongjia.divine.lib.pojo.request.Page;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author yuexiaodong@idongjia.cn
 * @date 2018-11-30
 */
@Getter
@Setter
@ToString(callSuper = true)
public class SessionLiveQry extends Page {
    /**
     * 专场id
     */
    private List<Long>    sessionIds;
    /**
     * 直播id
     */
    private List<Long>    liveIds;
    /**
     * 直播进程1未开始2已开始3已结束
     */
    private List<Integer> liveStates;
    /**
     * 专场进程
     */
    private List<Integer> sessionStates;
    /**
     * 专场预展
     */
    private Integer       sessionPreview;
    /**
     * 专场状态
     */
    private List<Integer> sessionStatus;
}
