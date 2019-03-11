package cn.idongjia.divine.query.factory.session;

import cn.idongjia.divine.lib.pojo.request.session.SessionLiveQry;
import cn.idongjia.divine.lib.pojo.request.sort.AuctionSort;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.query.SessionLiveESQuery;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.se.lib.engine.query.Direction;
import cn.idongjia.se.lib.engine.query.sort.ScriptSort;
import cn.idongjia.se.lib.engine.query.sort.Sort;

import java.util.List;

/**
 * @author yuexiaodong@idongjia.cn
 * @date 2018-11-30
 */
public class SessionLiveQueryFactory extends SessionLiveBaseQueryFactory {

    public SessionLiveQueryFactory(SessionLiveQry sessionLiveQry) {
        super(sessionLiveQry);
    }

    @Override
    public SessionLiveESQuery getQuery() {
        SessionLiveESQuery query = new SessionLiveESQuery();
        query.setLiveIds(sessionLiveQry.getLiveIds());
        query.setLiveStates(sessionLiveQry.getLiveStates());
        query.setSessionIds(sessionLiveQry.getSessionIds());
        query.setSessionPreview(sessionLiveQry.getSessionPreview());
        query.setSessionState(sessionLiveQry.getSessionStates());
        query.setSessionStatus(sessionLiveQry.getSessionStatus());
        query.setStart(sessionLiveQry.getOffset());
        query.setNum(sessionLiveQry.getLimit());
        return query;
    }

    @Override
    public ESSort getSort() {
        ESSort           esSort = new AuctionSort();
        final List<Sort> sorts  = sessionLiveQry.getSorts();
        if (!Utils.isEmpty(sorts)) {
            esSort.addSorts(sorts);
        } else {
            esSort.addSort(new Sort("sessionWeight", Direction.DESC));
            ScriptSort liveStateSort = new ScriptSort();
            liveStateSort.setScript(scriptSortManager.getSessionLiveSort());
            liveStateSort.setDirection(Direction.DESC);
            esSort.addSort(liveStateSort);
            esSort.addSort(new Sort("planEndTime", Direction.ASC));
            esSort.addSort(new Sort("livePreStarTime", Direction.ASC));
            esSort.addSort(new Sort("sessionId", Direction.ASC));
        }
        return esSort;
    }
}
