package cn.idongjia.divine.query.factory.session;

import cn.idongjia.divine.lib.pojo.request.session.SessionQry;
import cn.idongjia.divine.lib.pojo.request.sort.AuctionSort;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.query.SessionESQuery;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.se.lib.engine.query.sort.Sort;

import java.util.List;

/**
 * @author lc
 * @create at 2018/9/3.
 */
public class SessionQueryFactory extends SessionBaseQueryFactrory {
    public SessionQueryFactory(SessionQry sessionQry) {
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
        ESSort     esSort = new AuctionSort();
        List<Sort> sorts  = sessionQry.getSorts();
        if(Utils.isNotEmpty(sorts)) {
            esSort.addSorts(sorts);
        }
        return esSort;
    }
}
