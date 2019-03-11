package cn.idongjia.divine.query.factory.live;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.idongjia.divine.factor.LiveRecommendFactor;
import cn.idongjia.divine.lib.pojo.request.live.LiveQry;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.lib.pojo.request.sort.GeneralLiveSort;
import cn.idongjia.divine.query.CategoryESQuery;
import cn.idongjia.divine.query.LiveESQuery;
import cn.idongjia.divine.query.LiveExtAttrQuery;
import cn.idongjia.dw.lib.pojo.community.UserFavor;
import cn.idongjia.se.lib.engine.query.Direction;
import cn.idongjia.se.lib.engine.query.MatchAllQuery;
import cn.idongjia.se.lib.engine.query.boost.TermsField;
import cn.idongjia.se.lib.engine.query.logical.OR;
import cn.idongjia.se.lib.engine.query.sort.ScriptSort;

/**
 * @author lc
 * @create at 2018/8/21.
 */
public class LiveRecommendQueryFactory extends LiveBaseQueryFactrory {

    public LiveRecommendQueryFactory(LiveQry liveQry,UserFavor userFavor) {
        super(liveQry,userFavor);
    }

    @Override
    public LiveESQuery getQuery() {
        OR          orQuery     = new OR();
        LiveESQuery liveESQuery = new LiveESQuery();
        if(userFavor != null) {
            TermsField<Long> uidTermField      = buildTermsField(userFavor.getFollowCraftsman(),FOLLOW_CRAFTSMAN_MAX_NUM,"匠人");
            TermsField<Long> liveTermField     = buildTermsField(userFavor.getFollowLive(),FOLLOW_LIVE_MAX_NUM,"直播");
            TermsField<Long> categoryTermField = buildTermsField(userFavor.getUserCateScore(),USER_CATEGORY_MAX_NUM,"类目");
            TermsField<Long> extAttrTermField  = buildTermsField(userFavor.getAppend(),USER_EXT_ATTR_MAX_NUM,"扩展属性");
            if(null != uidTermField) {
                orQuery.addEsQuery(LiveESQuery.builder().userIds(uidTermField).build());
            }
            if(null != liveTermField) {
                List<Integer> stateList = new ArrayList<>();
                stateList.add(2);
                orQuery.addEsQuery(LiveESQuery.builder().liveIds(liveTermField).stateList(stateList).build());
            }
            if(null != categoryTermField) {
                CategoryESQuery categoryQuery = CategoryESQuery.builder().boostIdList(categoryTermField).build();
                orQuery.addEsQuery(LiveESQuery.builder().categoryESQuery(categoryQuery).build());
            }

            if(null != extAttrTermField) {
                LiveExtAttrQuery extAttrQuery = LiveExtAttrQuery.builder().id(extAttrTermField).build();
                orQuery.addEsQuery(LiveESQuery.builder().extAttrs(extAttrQuery).build());
            }
        }
        LiveRecommendFactor liveRecommendFactor = new LiveRecommendFactor();
        MatchAllQuery       matchAllQuery       = MatchAllQuery.builder().build();
        matchAllQuery.addQueryBoost(liveRecommendFactor.getNormalFator());
        orQuery.addEsQuery(matchAllQuery);
        liveESQuery.setStateList(liveQry.getStates());
        liveESQuery.setStatusList(liveQry.getStatus());
		liveESQuery.setOnline(liveQry.getOnline());
        liveESQuery.setLogicalQuery(orQuery);
        liveESQuery.setShowLocation(liveQry.getShowLocations());
        liveESQuery.setStart(liveQry.getOffset());
        liveESQuery.setNum(liveQry.getLimit());
        return liveESQuery;
    }

    @Override
    public ESSort getSort() {
        ESSort     esSort     = new GeneralLiveSort();
        ScriptSort scriptSort = new ScriptSort();
        scriptSort.setScript(scriptSortManager.getRecommendSort());
        scriptSort.setDirection(Direction.DESC);
        int                 userType            = (userFavor.getOldUser() != null && userFavor.getOldUser()) ? 2 : 1;
        LiveRecommendFactor liveRecommendFactor = new LiveRecommendFactor();
        Map<String,Object>  params              = liveRecommendFactor.buildScriptSortParams(userType);
        scriptSort.setParams(params);
        esSort.addSort(scriptSort);
        return esSort;
    }
}
