package cn.idongjia.divine.query.factory.session;

import cn.idongjia.divine.lib.pojo.request.session.SessionQry;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.manager.ScriptSortManager;
import cn.idongjia.divine.manager.SegManager;
import cn.idongjia.divine.query.SessionESQuery;
import cn.idongjia.divine.query.factory.AbstractQueryFactory;
import cn.idongjia.divine.utils.SpringBeanLoader;
import cn.idongjia.se.lib.engine.query.logical.Range;

/**
 * @author lc
 * @create at 2018/8/21.
 */
public abstract class SessionBaseQueryFactrory extends AbstractQueryFactory<SessionESQuery> {
    protected ScriptSortManager scriptSortManager = SpringBeanLoader.getBean("scriptSortManager", ScriptSortManager.class);
    protected SegManager        segManager        = SpringBeanLoader.getBean("segManager", SegManager.class);


    protected SessionQry sessionQry;

    public SessionBaseQueryFactrory(SessionQry sessionQry) {
        this.sessionQry = sessionQry;
    }

    /**
     * 设置一下共用的查询条件
     *
     * @param esQuery es查询条件的封装
     * @param qry     对外查询封装
     */
    public void setCommonQuery(SessionESQuery esQuery, SessionQry qry) {
        if (esQuery == null || qry == null) {
            return;
        }
        //结拍时间查询条件
        if (qry.getPlanEndTimeEnd() != null || qry.getPlanEndTimeStart() != null) {
            Range<Long> planEndTimeRange = new Range();
            if (qry.getPlanEndTimeStart() != null) {
                planEndTimeRange.setLower(true);
                planEndTimeRange.setLowValue(qry.getPlanEndTimeStart());
            }
            if (qry.getPlanEndTimeEnd() != null) {
                planEndTimeRange.setUper(true);
                planEndTimeRange.setUpValue(qry.getPlanEndTimeEnd());
            }
            esQuery.setPlanEndTime(planEndTimeRange);
        }
        esQuery.setWildTitle(qry.getWildTitle());
    }

    @Override
    public abstract SessionESQuery getQuery();

    @Override
    public abstract ESSort getSort();
}
