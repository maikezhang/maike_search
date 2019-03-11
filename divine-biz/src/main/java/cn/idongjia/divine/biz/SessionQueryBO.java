package cn.idongjia.divine.biz;

import cn.idongjia.divine.convertor.ConvertorI;
import cn.idongjia.divine.db.es.entity.SessionEntity;
import cn.idongjia.divine.lib.pojo.request.session.SessionQry;
import cn.idongjia.divine.lib.pojo.request.sort.BizType;
import cn.idongjia.divine.lib.pojo.request.sort.SortType;
import cn.idongjia.divine.lib.pojo.response.MultiResponse;
import cn.idongjia.divine.lib.pojo.response.session.SessionCO;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.se.lib.entities.EsSearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/8/7.
 */
@Component
@Slf4j
public class SessionQueryBO extends BaseQueryBO<SessionQry,SessionEntity> {

    @Resource
    private ConvertorI<SessionCO,SessionEntity> sessionEntityConvertor;

    public MultiResponse<SessionCO> query(SessionQry sessionQry) {
        if(sessionQry.getSortType() == null) {
            sessionQry.setSortType(SortType.TabSortType.SESSION_DEFAULT);
        }
        EsSearchResult      esSearchResult  = query(BizType.ModuleType.SESSION,sessionQry,SessionEntity.class);
        List<SessionEntity> sessionEntities = esSearchResult.getData();
        List<SessionCO>     sessionCOS      = new ArrayList<>();
        if(!Utils.isEmpty(sessionEntities)) {
            sessionCOS = sessionEntities.stream().map(liveEntity -> {
                return sessionEntityConvertor.dataToClient(liveEntity);
            }).collect(Collectors.toList());
        }
        return MultiResponse.of(sessionCOS,esSearchResult.getTotal().intValue());
    }
}
