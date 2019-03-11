package cn.idongjia.divine.query.factory.session;

import cn.idongjia.divine.lib.pojo.request.session.SessionQry;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.lib.pojo.request.sort.GeneralLiveSort;
import cn.idongjia.divine.query.SessionESQuery;
import cn.idongjia.se.lib.engine.query.Direction;
import cn.idongjia.se.lib.engine.query.sort.ScriptSort;

/**
 * @author lc
 * @create at 2018/9/3.
 */
public class SessionStateQueryFactory extends SessionBaseQueryFactrory {
    public SessionStateQueryFactory(SessionQry sessionQry) {
        super(sessionQry);
    }

    @Override
    public SessionESQuery getQuery() {
        SessionESQuery sessionESQuery = new SessionESQuery();
        setCommonQuery(sessionESQuery, sessionQry);
        sessionESQuery.setCreatorId(sessionQry.getCreatorId());
        sessionESQuery.setCreatorType(sessionQry.getCreatorType());
        sessionESQuery.setDjtFlag(sessionQry.getDjtFlag());
        sessionESQuery.setPreview(sessionQry.getPreview());
        sessionESQuery.setSessionTypes(sessionQry.getSessionTypes());
        sessionESQuery.setState(sessionQry.getState());
        sessionESQuery.setStatus(sessionQry.getStatus());
        sessionESQuery.setSessionIds(sessionQry.getSessionIds());
        sessionESQuery.setStart(sessionQry.getOffset());
        sessionESQuery.setNum(sessionQry.getLimit());
        return sessionESQuery;
    }

    @Override
    public ESSort getSort() {
        ESSort esSort = new GeneralLiveSort();
//        ScriptSort scriptSort = new ScriptSort();
//        scriptSort.setScript(scriptSortManager.getSessionStateSort());
//        scriptSort.setDirection(Direction.DESC);
        ScriptSort scriptSort1 = new ScriptSort();
        scriptSort1.setScript(scriptSortManager.getSessionListSort1());
        scriptSort1.setDirection(Direction.DESC);
        ScriptSort scriptSort2 = new ScriptSort();
        scriptSort2.setScript(scriptSortManager.getSessionListSort2());
        scriptSort2.setDirection(Direction.DESC);
        ScriptSort scriptSort3 = new ScriptSort();
        scriptSort3.setScript(scriptSortManager.getSessionListSort3());
        scriptSort3.setDirection(Direction.DESC);
        esSort.addSort(scriptSort1);
        esSort.addSort(scriptSort2);
        esSort.addSort(scriptSort3);
        return esSort;
    }
}
