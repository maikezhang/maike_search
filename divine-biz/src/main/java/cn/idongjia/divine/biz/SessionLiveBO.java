package cn.idongjia.divine.biz;

import cn.idongjia.divine.convertor.ConvertorI;
import cn.idongjia.divine.db.es.entity.SessionLiveEntity;
import cn.idongjia.divine.lib.pojo.request.session.SessionLiveQry;
import cn.idongjia.divine.lib.pojo.request.sort.BizType;
import cn.idongjia.divine.lib.pojo.response.MultiResponse;
import cn.idongjia.divine.lib.pojo.response.session.SessionLiveCO;
import cn.idongjia.se.lib.entities.EsSearchResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yuexiaodong@idongjia.cn
 * @date 2018-11-30
 */
@Component
public class SessionLiveBO extends BaseQueryBO<SessionLiveQry, SessionLiveEntity> {

    @Resource
    private ConvertorI<SessionLiveCO, SessionLiveEntity> sessionLiveEntityConvertor;

    public MultiResponse<SessionLiveCO> query(SessionLiveQry qry) {
        final EsSearchResult          result = query(BizType.ModuleType.SESSION_LIVE, qry, SessionLiveEntity.class);
        final List<SessionLiveEntity> data   = result.getData();
        final Long                    total  = result.getTotal();
        final List<SessionLiveCO> coList = data.stream()
                .map(x -> sessionLiveEntityConvertor.dataToClient(x))
                .collect(Collectors.toList());
        return MultiResponse.of(coList, total.intValue());
    }

}
