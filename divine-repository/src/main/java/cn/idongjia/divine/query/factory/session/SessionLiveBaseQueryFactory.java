package cn.idongjia.divine.query.factory.session;

import cn.idongjia.divine.lib.pojo.request.session.SessionLiveQry;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.manager.ScriptSortManager;
import cn.idongjia.divine.manager.SegManager;
import cn.idongjia.divine.query.SessionLiveESQuery;
import cn.idongjia.divine.query.factory.AbstractQueryFactory;
import cn.idongjia.divine.utils.SpringBeanLoader;

/**
 * @author yuexiaodong@idongjia.cn
 * @date 2018-11-30
 */
public abstract class SessionLiveBaseQueryFactory extends AbstractQueryFactory<SessionLiveESQuery> {
    protected ScriptSortManager scriptSortManager = SpringBeanLoader.getBean("scriptSortManager", ScriptSortManager.class);
    protected SegManager        segManager        = SpringBeanLoader.getBean("segManager", SegManager.class);

    protected SessionLiveQry sessionLiveQry;

    public SessionLiveBaseQueryFactory(SessionLiveQry sessionLiveQry) {
        this.sessionLiveQry = sessionLiveQry;
    }

    @Override
    public abstract SessionLiveESQuery getQuery();

    @Override
    public abstract ESSort getSort();
}
