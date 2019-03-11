package cn.idongjia.divine.query.factory.live;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.idongjia.desert.dto.WordsDTO;
import cn.idongjia.desert.query.SegQuery;
import cn.idongjia.divine.lib.pojo.request.live.LiveQry;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.lib.pojo.request.sort.GeneralLiveSort;
import cn.idongjia.divine.query.LiveESQuery;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.dw.lib.pojo.community.UserFavor;
import cn.idongjia.se.lib.engine.query.Direction;
import cn.idongjia.se.lib.engine.query.logical.OR;
import cn.idongjia.se.lib.engine.query.sort.ScriptSort;

/**
 * @author lc
 * @create at 2018/8/21.
 */

public class LiveListQueryFactory extends LiveBaseQueryFactrory {

	public LiveListQueryFactory(LiveQry liveQry, UserFavor userFavor) {
		super(liveQry, userFavor);
	}

	@Override
	public LiveESQuery getQuery() {
		LiveESQuery liveESQuery = new LiveESQuery();

		String title = liveQry.getTitle();
		List<String> words = null;
		if (!Utils.isEmpty(title)) {
			SegQuery query = new SegQuery(liveQry.getTitle());
			query.setSegMode((short) 0);
			List<WordsDTO> wordsList = segManager.seg(query);
			if (Utils.isNotEmpty(wordsList)) {
				words = wordsList.stream().map(word -> word.getWordStr()).collect(Collectors.toList());
			}
		}
		Integer hasPlayback = liveQry.getHasPlayback();
		if (hasPlayback != null) {
			OR or = new OR();
			LiveESQuery tmpQuery = new LiveESQuery();
			tmpQuery.setOnline(liveQry.getOnline());
			tmpQuery.setTitleSmart(words);
			tmpQuery.setStateList(liveQry.getStates());
			tmpQuery.setStatusList(liveQry.getStatus());
			tmpQuery.setTypeList(liveQry.getTypes());
			tmpQuery.setUid(liveQry.getUid());
			tmpQuery.setShowLocation(liveQry.getShowLocations());
			or.addEsQuery(tmpQuery);
			LiveESQuery tmpQuery2 = new LiveESQuery();
			tmpQuery2.setOnline(liveQry.getOnline());
			tmpQuery2.setTitleSmart(words);
			tmpQuery2.setStateList(Arrays.asList(3));
			tmpQuery2.setStatusList(liveQry.getStatus());
			tmpQuery2.setTypeList(liveQry.getTypes());
			tmpQuery2.setUid(liveQry.getUid());
			tmpQuery2.setHasPlayBack(true);
			tmpQuery2.setShowLocation(liveQry.getShowLocations());

			or.addEsQuery(tmpQuery2);
			liveESQuery.setLogicalQuery(or);
			liveESQuery.setStart(liveQry.getOffset());
			liveESQuery.setNum(liveQry.getLimit());
		} else {
			liveESQuery.setOnline(liveQry.getOnline());
			liveESQuery.setTitleSmart(words);
			liveESQuery.setStart(liveQry.getOffset());
			liveESQuery.setNum(liveQry.getLimit());
			liveESQuery.setStateList(liveQry.getStates());
			liveESQuery.setStatusList(liveQry.getStatus());
			liveESQuery.setTypeList(liveQry.getTypes());
			liveESQuery.setUid(liveQry.getUid());
			liveESQuery.setShowLocation(liveQry.getShowLocations());

		}

		return liveESQuery;
	}

	@Override
	public ESSort getSort() {
		ESSort esSort = new GeneralLiveSort();
		ScriptSort scriptSort = new ScriptSort();
		scriptSort.setScript(scriptSortManager.getLiveDefaultSort());
		scriptSort.setDirection(Direction.DESC);

		esSort.addSort(scriptSort);
		return esSort;
	}
}
