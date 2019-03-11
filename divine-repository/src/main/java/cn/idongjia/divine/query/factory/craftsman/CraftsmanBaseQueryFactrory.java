package cn.idongjia.divine.query.factory.craftsman;

import cn.idongjia.divine.lib.pojo.request.live.CraftsmanQry;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.manager.ScriptSortManager;
import cn.idongjia.divine.manager.SegManager;
import cn.idongjia.divine.query.CraftsmanESQuery;
import cn.idongjia.divine.query.factory.AbstractQueryFactory;
import cn.idongjia.divine.utils.SpringBeanLoader;
import cn.idongjia.dw.lib.pojo.community.UserFavor;

/**
 * @author lc
 * @create at 2018/8/21.
 */
public abstract class CraftsmanBaseQueryFactrory extends AbstractQueryFactory<CraftsmanESQuery> {
    protected ScriptSortManager scriptSortManager = SpringBeanLoader.getBean("scriptSortManager",ScriptSortManager.class);
    protected SegManager        segManager        = SpringBeanLoader.getBean("segManager",SegManager.class);


    protected CraftsmanQry craftsmanQry;

    public CraftsmanBaseQueryFactrory(CraftsmanQry craftsmanQry) {
        this.craftsmanQry = craftsmanQry;
    }

    @Override
    public abstract CraftsmanESQuery getQuery();

    @Override
    public abstract ESSort getSort();
}
