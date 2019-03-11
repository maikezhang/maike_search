package cn.idongjia.divine.query;

import cn.idongjia.se.lib.engine.annotations.QueryField;
import cn.idongjia.se.lib.engine.annotations.QueryFieldType;
import cn.idongjia.se.lib.engine.query.LeafQuery;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author yuexiaodong@idongjia.cn
 * @date 2018-11-30
 */
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class SessionLiveESQuery extends LeafQuery {
    /**
     * 专场状态
     */
    @QueryField(type = QueryFieldType.IN, fieldName = "sessionStatus")
    private List<Integer> sessionStatus;
    /**
     * 专场进程
     */
    @QueryField(type = QueryFieldType.IN, fieldName = "sessionState")
    private List<Integer> sessionState;
    /**
     * 直播id
     */
    @QueryField(type = QueryFieldType.IN, fieldName = "liveId")
    private List<Long>    liveIds;
    /**
     * 直播进程1未开始2已开始3已结束
     */
    @QueryField(type = QueryFieldType.IN, fieldName = "liveState")
    private List<Integer> liveStates;
    /**
     * 是否预展
     */
    @QueryField(type = QueryFieldType.IS, fieldName = "sessionPreview")
    private Integer       sessionPreview;
    /**
     * 专场id
     */
    @QueryField(type = QueryFieldType.IN, fieldName = "sessionId")
    private List<Long>    sessionIds;
}
