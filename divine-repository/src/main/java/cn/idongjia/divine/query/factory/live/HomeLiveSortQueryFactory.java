package cn.idongjia.divine.query.factory.live;

import cn.idongjia.divine.disconfManager.DisconfHomePageSortManager;
import cn.idongjia.divine.factor.HomePageLiveSortFactor;
import cn.idongjia.divine.lib.pojo.request.live.LiveQry;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.lib.pojo.request.sort.GeneralLiveSort;
import cn.idongjia.divine.query.CategoryESQuery;
import cn.idongjia.divine.query.LiveESQuery;
import cn.idongjia.divine.utils.SpringBeanLoader;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.dw.lib.pojo.community.UserFavor;
import cn.idongjia.se.lib.engine.query.Direction;
import cn.idongjia.se.lib.engine.query.sort.ScriptSort;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author lc
 * @create at 2018/8/21.
 */
public class HomeLiveSortQueryFactory extends LiveBaseQueryFactrory {

    private final  DisconfHomePageSortManager manager= SpringBeanLoader.getBean("disconfHomePageSortManager",DisconfHomePageSortManager.class);

    public HomeLiveSortQueryFactory(LiveQry liveQry, UserFavor userFavor) {
        super(liveQry, userFavor);
    }

    @Override
    public LiveESQuery getQuery() {
        LiveESQuery liveESQuery = new LiveESQuery();

        liveESQuery.setOnline(liveQry.getOnline());
        liveESQuery.setStart(liveQry.getOffset());
        liveESQuery.setNum(liveQry.getLimit());
        liveESQuery.setStateList(liveQry.getStates());
        liveESQuery.setShowLocation(liveQry.getShowLocations());
        liveESQuery.setStatusList(liveQry.getStatus());
        liveESQuery.setTypeList(liveQry.getTypes());
        liveESQuery.setUid(liveQry.getUid());
        liveESQuery.setNotContainIds(liveQry.getExcludeLiveIds());
        liveESQuery.setIds(liveQry.getLiveIds());

        return liveESQuery;
    }

    @Override
    public ESSort getSort() {
        ESSort     esSort     = new GeneralLiveSort();
        ScriptSort scriptSort = new ScriptSort();
        scriptSort.setScript(scriptSortManager.getHomepageLiveSort());
        scriptSort.setDirection(Direction.DESC);
        HomePageLiveSortFactor homePageLiveSortFactor = new HomePageLiveSortFactor();

        Map<String, Object> params = homePageLiveSortFactor.buildScriptSortParams(
                manager.getMsgWeight(),
                manager.getUvWeight(),
                manager.getFreshWeight());
        scriptSort.setParams(params);
        esSort.addSort(scriptSort);
        return esSort;
    }
}
