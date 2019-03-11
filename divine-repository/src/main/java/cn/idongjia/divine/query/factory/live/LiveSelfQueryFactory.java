package cn.idongjia.divine.query.factory.live;

import cn.idongjia.desert.dto.WordsDTO;
import cn.idongjia.desert.query.SegQuery;
import cn.idongjia.divine.lib.pojo.request.live.LiveQry;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.lib.pojo.request.sort.GeneralLiveSort;
import cn.idongjia.divine.query.LiveESQuery;
import cn.idongjia.divine.query.RangeBuilder;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.dw.lib.pojo.community.UserFavor;
import cn.idongjia.se.lib.engine.query.sort.Sort;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/8/21.
 */

public class LiveSelfQueryFactory extends LiveBaseQueryFactrory {

    public LiveSelfQueryFactory(LiveQry liveQry, UserFavor userFavor) {
        super(liveQry, userFavor);
    }

    @Override
    public LiveESQuery getQuery() {
        LiveESQuery liveESQuery = new LiveESQuery();

        String       title = liveQry.getTitle();
        List<String> words = null;
        if (!Utils.isEmpty(title)) {
            SegQuery query = new SegQuery(liveQry.getTitle());
            query.setSegMode((short) 0);
            List<WordsDTO> wordsList = segManager.seg(query);
            if (!Utils.isEmpty(wordsList)) {
                words = wordsList.stream().map(word -> word.getWordStr()).collect(Collectors.toList());
            }
        }
        liveESQuery.setNotContainIds(liveQry.getExcludeLiveIds());
        liveESQuery.setWildTitle(liveQry.getWildTitle());
        Integer hasPlayback = liveQry.getHasPlayback();
        liveESQuery.setHasPlayBack(hasPlayback == null ? null : hasPlayback == 1 ? true : false);
        List<Long> liveIds = liveQry.getLiveIds();
        liveESQuery.setIds(liveIds);
        liveESQuery.setZooId(liveQry.getZooId());
        liveESQuery.setZooIds(liveQry.getZooIds());
        liveESQuery.setCreateTime(RangeBuilder.build(liveQry.getMinCreateTime(), liveQry.getMaxCreateTime()));
        liveESQuery.setOnline(liveQry.getOnline());
        liveESQuery.setTitleSmart(words);
        liveESQuery.setMinWeight(RangeBuilder.build(liveQry.getMinWeight()));
        liveESQuery.setStartTime(RangeBuilder.build(liveQry.getMinStartTime(), liveQry.getMaxStartTime()));
        liveESQuery.setEndTime(RangeBuilder.build(liveQry.getMinEndTime(), liveQry.getMaxEndTime()));
        liveESQuery.setPreEndTime(RangeBuilder.build(liveQry.getMinPreEndTime(), liveQry.getMaxPreEndTime()));
        liveESQuery.setPreStartTime(RangeBuilder.build(liveQry.getMinPreStartTime(), liveQry.getMaxPreStartTime()));
        liveESQuery.setStart(liveQry.getOffset());
        liveESQuery.setShowLocation(liveQry.getShowLocations());
        liveESQuery.setNum(liveQry.getLimit());
        liveESQuery.setStateList(liveQry.getStates());
        liveESQuery.setStatusList(liveQry.getStatus());
        liveESQuery.setTypeList(liveQry.getTypes());
        liveESQuery.setUid(liveQry.getUid());
        liveESQuery.setSessionId(liveQry.getSessionId());

        return liveESQuery;
    }

    @Override
    public ESSort getSort() {
        ESSort     esSort = new GeneralLiveSort();
        List<Sort> sorts  = liveQry.getSorts();
        if (Utils.isNotEmpty(sorts)) {
            esSort.addSorts(sorts);
        }
        return esSort;
    }
}
