package cn.idongjia.divine.query.factory.auction;

import cn.idongjia.desert.dto.WordsDTO;
import cn.idongjia.desert.query.SegQuery;
import cn.idongjia.divine.lib.pojo.request.auction.AuctionQry;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.lib.pojo.request.sort.GeneralLiveSort;
import cn.idongjia.divine.query.AuctionESQuery;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.dw.lib.pojo.community.UserFavor;
import cn.idongjia.se.lib.engine.query.Direction;
import cn.idongjia.se.lib.engine.query.boost.TermsField;
import cn.idongjia.se.lib.engine.query.logical.Logical;
import cn.idongjia.se.lib.engine.query.logical.OR;
import cn.idongjia.se.lib.engine.query.sort.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/8/21.
 */
public class AuctionSearchQueryFactory extends AuctionBaseQueryFactrory {

	public AuctionSearchQueryFactory(AuctionQry auctionQry, UserFavor userFavor) {
		super(auctionQry, userFavor);
	}

	@Override
	public AuctionESQuery getQuery() {
		AuctionESQuery auctionESQuery = new AuctionESQuery();
		setCommonQuery(auctionESQuery, auctionQry);
		String itemTitle = auctionQry.getItemTitle();
		Logical logical = null;
		if (!Utils.isEmpty(itemTitle)) {
			SegQuery query = new SegQuery(itemTitle);
			List<WordsDTO> wordsList = segManager.seg(query);
			if (Utils.isNotEmpty(wordsList)) {
				wordsList = wordsList.stream().filter(word -> word != null).collect(Collectors.toList());
			}
			if (Utils.isNotEmpty(wordsList)) {
				OR or = new OR();
				wordsList.stream().forEach(word -> {
					AuctionESQuery tmpESQuery = new AuctionESQuery();
					String wordStr = word.getWordStr();
					tmpESQuery.setItemTitleTerm(wordStr);
					tmpESQuery.addFieldBoost("itemTitleSmart", 100d);
					tmpESQuery.addFieldBoost("craftsmanNameSmart", 10d);
					tmpESQuery.setCraftsmanNameTerm(wordStr);
					tmpESQuery.setMust(false);
					or.addEsQuery(tmpESQuery);
				});
				logical = or;
			}
		}
		auctionESQuery.setLogicalQuery(logical);
		TermsField<Integer> termsField = new TermsField<Integer>();
		Map<Integer, Double> stateMap = new HashMap<>();
		stateMap.put(1, 1d);
		stateMap.put(0, 0.5d);
		termsField.setBoostFields(stateMap);
		auctionESQuery.setStatusTerms(termsField);
		auctionESQuery.setCraftsmanIds(auctionQry.getCraftsmanIds());
		auctionESQuery.setSessionIds(auctionQry.getSessionIds());
		auctionESQuery.setItemExtStatus(auctionQry.getItemExtStatus());
		auctionESQuery.setItemStatus(auctionQry.getItemStatus());
		Integer sessionPreview = auctionQry.getSessionPreview();
		auctionESQuery.setSessionPreviewStatus(sessionPreview);
		List<Integer> sessionStates = auctionQry.getSessionStates();
		auctionESQuery.setSessionStates(sessionStates);
		auctionESQuery.setItemIds(auctionQry.getItemIds());
		List<Integer> sessionStatus = auctionQry.getSessionStatus();
		auctionESQuery.setSessionStatus(sessionStatus);
		List<Long> groundIds = auctionQry.getGroundIds();
		auctionESQuery.setGroundIds(groundIds);
		List<Long> excludeGroundIds = auctionQry.getExcludeGroundIds();
		auctionESQuery.setNoGroundIds(excludeGroundIds);
		auctionESQuery.setAuctionStatus(auctionQry.getAuctionStatus());
		auctionESQuery.setAuctionTypes(auctionQry.getAuctionTypes());
		auctionESQuery.setAuctionState(auctionQry.getAuctionStates());
		auctionESQuery.setSessionTypes(auctionQry.getSessionTypes());
		auctionESQuery.setStart(auctionQry.getOffset());
		auctionESQuery.setNum(auctionQry.getLimit());
		return auctionESQuery;
	}

	@Override
	public ESSort getSort() {
		ESSort esSort = new GeneralLiveSort();
		List<Sort> sorts = new ArrayList<>();
		sorts.add(Sort.builder().field("_score").direction(Direction.DESC).build());
		sorts.add(Sort.builder().field("startTime").direction(Direction.ASC).build());
		esSort.addSorts(sorts);
		return esSort;
	}
}
