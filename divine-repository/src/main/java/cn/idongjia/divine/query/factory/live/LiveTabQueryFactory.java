package cn.idongjia.divine.query.factory.live;

import cn.idongjia.divine.lib.pojo.request.live.LiveQry;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.lib.pojo.request.sort.GeneralLiveSort;
import cn.idongjia.divine.query.CategoryESQuery;
import cn.idongjia.divine.query.LiveESQuery;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.dw.lib.pojo.community.UserFavor;
import cn.idongjia.se.lib.engine.query.Direction;
import cn.idongjia.se.lib.engine.query.sort.ScriptSort;

import java.util.List;

/**
 * @author lc
 * @create at 2018/8/21.
 */
public class LiveTabQueryFactory extends LiveBaseQueryFactrory {

    public LiveTabQueryFactory(LiveQry liveQry,UserFavor userFavor) {
        super(liveQry,userFavor);
    }

    @Override
    public LiveESQuery getQuery() {
        LiveESQuery liveESQuery = new LiveESQuery();
        List<Long>  categoryIds = liveQry.getCategoryIds();
        if(Utils.isNotEmpty(categoryIds)) {
            CategoryESQuery categoryESQuery = new CategoryESQuery();
            categoryESQuery.setId(categoryIds);
            liveESQuery.setCategoryESQuery(categoryESQuery);
        }
        List<Long> excludeCategoryIds = liveQry.getExcludeCategoryIds();
        if(Utils.isNotEmpty(excludeCategoryIds)) {
            CategoryESQuery categoryESQuery = new CategoryESQuery();
            categoryESQuery.setExcludeIds(excludeCategoryIds);
            liveESQuery.setCategoryESQuery(categoryESQuery);
        }
        liveESQuery.setShowLocation(liveQry.getShowLocations());
        liveESQuery.setOnline(liveQry.getOnline());
        liveESQuery.setStateList(liveQry.getStates());
        liveESQuery.setStatusList(liveQry.getStatus());
        liveESQuery.setStart(liveQry.getOffset());
        liveESQuery.setNum(liveQry.getLimit());
        return liveESQuery;
    }

    @Override
    public ESSort getSort() {
        ESSort     esSort     = new GeneralLiveSort();
        ScriptSort scriptSort = new ScriptSort();
        scriptSort.setScript(scriptSortManager.getLiveTabSort());
        scriptSort.setDirection(Direction.DESC);

        esSort.addSort(scriptSort);
        return esSort;
    }
}
