package cn.idongjia.divine.query.factory.live;

import cn.idongjia.desert.dto.WordsDTO;
import cn.idongjia.desert.query.SegQuery;
import cn.idongjia.divine.lib.pojo.request.live.LiveEnums;
import cn.idongjia.divine.lib.pojo.request.live.LiveQry;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.lib.pojo.request.sort.GeneralLiveSort;
import cn.idongjia.divine.query.LiveESQuery;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.dw.lib.pojo.community.UserFavor;
import cn.idongjia.se.lib.engine.query.Direction;
import cn.idongjia.se.lib.engine.query.boost.TermsField;
import cn.idongjia.se.lib.engine.query.logical.Logical;
import cn.idongjia.se.lib.engine.query.logical.OR;
import cn.idongjia.se.lib.engine.query.sort.Sort;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/8/21.
 */

@Slf4j
public class LiveSearchQueryFactory extends LiveBaseQueryFactrory {

    public LiveSearchQueryFactory(LiveQry liveQry,UserFavor userFavor) {
        super(liveQry,userFavor);
    }

    @Override
    public LiveESQuery getQuery() {
        LiveESQuery liveESQuery = new LiveESQuery();

        String  title   = liveQry.getTitle();
        Logical logical = null;
        if(!Utils.isEmpty(title)) {
            SegQuery       query     = new SegQuery(liveQry.getTitle());
            List<WordsDTO> wordsList = segManager.seg(query);
            if(Utils.isNotEmpty(wordsList)) {
                wordsList = wordsList.stream().filter(word -> word != null).collect(Collectors.toList());
            }
            if(Utils.isNotEmpty(wordsList)) {
                OR or = new OR();
                wordsList.stream().forEach(word -> {
                    String      wordStr    = word.getWordStr();
                    LiveESQuery tmpESQuery = new LiveESQuery();
                    tmpESQuery.addFieldBoost("titleSmart",100d);
                    tmpESQuery.addFieldBoost("craftsmanSmart",10d);
                    tmpESQuery.setTitleTerm(wordStr);
                    tmpESQuery.setCraftsmanTerm(wordStr);
                    tmpESQuery.setMust(false);
                    or.addEsQuery(tmpESQuery);
                });
                logical = or;
            }
        }
        liveESQuery.setShowLocation(liveESQuery.getShowLocation());

        TermsField<Integer> termsField = new TermsField<Integer>();
        liveESQuery.setLogicalQuery(logical);
        Map<Integer,Double> stateMap = new HashMap<Integer,Double>();
        stateMap.put(2,1d);
        stateMap.put(1,0.5d);
        termsField.setBoostFields(stateMap);
        liveESQuery.setStateTerms(termsField);
        liveESQuery.setOnline(liveQry.getOnline());
        liveESQuery.setStart(liveQry.getOffset());
        liveESQuery.setNum(liveQry.getLimit());
        liveESQuery.setStateList(liveQry.getStates());
        liveESQuery.setShowLocation(liveQry.getShowLocations());
        liveESQuery.setStatusList(liveQry.getStatus());
        liveESQuery.setTypeList(liveQry.getTypes());
        liveESQuery.setUid(liveQry.getUid());
        log.info("liveESQuery==>{},{}",liveESQuery,liveQry);
        return liveESQuery;
    }

    @Override
    public ESSort getSort() {
        ESSort     esSort = new GeneralLiveSort();
        List<Sort> sorts  = new ArrayList<>();
        sorts.add(Sort.builder().field("_score").direction(Direction.DESC).build());
        sorts.add(Sort.builder().field("startTime").direction(Direction.ASC).build());
        esSort.addSorts(sorts);
        return esSort;
    }
}
