package cn.idongjia.divine.query.factory.live;

import cn.idongjia.divine.lib.pojo.request.live.LiveQry;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.manager.ScriptSortManager;
import cn.idongjia.divine.manager.SegManager;
import cn.idongjia.divine.query.LiveESQuery;
import cn.idongjia.divine.query.factory.AbstractQueryFactory;
import cn.idongjia.divine.utils.SpringBeanLoader;
import cn.idongjia.dw.lib.pojo.community.UserFavor;

/**
 * @author lc
 * @create at 2018/8/21.
 */
public abstract class LiveBaseQueryFactrory extends AbstractQueryFactory<LiveESQuery> {
	protected ScriptSortManager	scriptSortManager = SpringBeanLoader.getBean("scriptSortManager", ScriptSortManager.class);
	protected SegManager		segManager		  = SpringBeanLoader.getBean("segManager", SegManager.class);
	protected UserFavor			userFavor;
	protected LiveQry			liveQry;
	
	public LiveBaseQueryFactrory(LiveQry liveQry, UserFavor userFavor) {
		this.liveQry = liveQry;
		this.userFavor = userFavor;
	}
	
	@Override
	public abstract LiveESQuery getQuery();
	
	@Override
	public abstract ESSort getSort();
}
