package cn.idongjia.divine.lib.pojo.request.session;

import cn.idongjia.divine.lib.pojo.request.Page;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author lc
 * @create at 2018/7/25.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class SessionQry extends Page {
    /**
     * 纯直播状态(-5修改中-4待审核-3审核中-2审核结束-1删除0未上线1已上线)
     */
    private List<Integer> status;
    /**
     * 直播进程1未开始2已开始3已结束
     */
    private List<Integer> state;
    /**
     * 创建人类型
     */
    private Integer       creatorType;
    /**
     * 创建人id
     */
    private Long          creatorId;
    /**
     * 专场类型
     */
    private List<Integer> sessionTypes;
    /**
     * 是否预展
     */
    private Integer       preview;
    /**
     * flag
     */
    public  Integer       djtFlag;
    /**
     * 专场id
     */
    private List<Long>    sessionIds;
    /**
     * 专场结拍时间查询条件，包含上下界
     */
    private Long          planEndTimeStart;
    private Long          planEndTimeEnd;
    /**
     * 专场标题模糊查询
     */
    private String        wildTitle;

}
