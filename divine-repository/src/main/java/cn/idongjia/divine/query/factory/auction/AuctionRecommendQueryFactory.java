package cn.idongjia.divine.query.factory.auction;

import cn.idongjia.divine.factor.AuctionRecommendFactor;
import cn.idongjia.divine.lib.pojo.request.auction.AuctionQry;
import cn.idongjia.divine.lib.pojo.request.sort.AuctionSort;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.query.AuctionESQuery;
import cn.idongjia.divine.query.LiveExtAttrQuery;
import cn.idongjia.dw.lib.pojo.community.UserFavor;
import cn.idongjia.se.lib.engine.query.Direction;
import cn.idongjia.se.lib.engine.query.MatchAllQuery;
import cn.idongjia.se.lib.engine.query.boost.TermsField;
import cn.idongjia.se.lib.engine.query.logical.OR;
import cn.idongjia.se.lib.engine.query.sort.ScriptSort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lc
 * @create at 2018/8/21.
 */
public class AuctionRecommendQueryFactory extends AuctionBaseQueryFactrory {
	
	public AuctionRecommendQueryFactory(AuctionQry auctionQry, UserFavor userFavor) {
		super(auctionQry, userFavor);
	}
	
	@Override
	public AuctionESQuery getQuery() {
		OR orQuery = new OR();
		AuctionESQuery auctionESQuery = new AuctionESQuery();
		setCommonQuery(auctionESQuery, auctionQry);
		if (userFavor != null) {
			TermsField<Long> uidTermField = buildTermsField(userFavor.getFollowCraftsman(), FOLLOW_CRAFTSMAN_MAX_NUM, "匠人");
			TermsField<Long> itemTermField = buildTermsField(userFavor.getFollowAucItem(), FOLLOW_AUCTION_MAX_NUM, "直播");
			TermsField<Long> categoryTermField = buildTermsField(userFavor.getUserCateScore(), USER_CATEGORY_MAX_NUM, "类目");
			TermsField<Long> extAttrTermField = buildTermsField(userFavor.getAppend(), USER_EXT_ATTR_MAX_NUM, "扩展属性");
			if (null != uidTermField) {
				orQuery.addEsQuery(AuctionESQuery.builder().userIds(uidTermField).build());
			}
			if (null != itemTermField) {
				List<Integer> stateList = new ArrayList<>();
				stateList.add(1);
				orQuery.addEsQuery(AuctionESQuery.builder().itemIdsTerm(itemTermField).auctionState(stateList).build());
			}
			if (null != categoryTermField) {
				orQuery.addEsQuery(AuctionESQuery.builder().categoryIdsTerms(categoryTermField).build());
			}
			
			if (null != extAttrTermField) {
				LiveExtAttrQuery extAttrQuery = LiveExtAttrQuery.builder().id(extAttrTermField).build();
				orQuery.addEsQuery(AuctionESQuery.builder().extAttrs(extAttrQuery).build());
			}
		}
		AuctionRecommendFactor auctionRecommendFactor = new AuctionRecommendFactor();
		MatchAllQuery matchAllQuery = MatchAllQuery.builder().build();
		matchAllQuery.addQueryBoost(auctionRecommendFactor.getNormalFator());
		orQuery.addEsQuery(matchAllQuery);
		auctionESQuery.setLogicalQuery(orQuery);
		auctionESQuery.setStart(auctionQry.getOffset());
		auctionESQuery.setNum(auctionQry.getLimit());
		auctionESQuery.setItemExtStatus(auctionQry.getItemExtStatus());
		auctionESQuery.setItemStatus(auctionQry.getItemStatus());
		Integer sessionPreview = auctionQry.getSessionPreview();
		auctionESQuery.setSessionPreviewStatus(sessionPreview);
		List<Integer> sessionStates = auctionQry.getSessionStates();
		auctionESQuery.setSessionStates(sessionStates);
		auctionESQuery.setSessionTypes(auctionQry.getSessionTypes());
		List<Integer> sessionStatus = auctionQry.getSessionStatus();
		auctionESQuery.setSessionStatus(sessionStatus);
		List<Long> groundIds = auctionQry.getGroundIds();
		auctionESQuery.setGroundIds(groundIds);
		List<Long> excludeGroundIds = auctionQry.getExcludeGroundIds();
		auctionESQuery.setNoGroundIds(excludeGroundIds);
		auctionESQuery.setAuctionStatus(auctionQry.getAuctionStatus());
		auctionESQuery.setAuctionTypes(auctionQry.getAuctionTypes());
		auctionESQuery.setAuctionState(auctionQry.getAuctionStates());
		return auctionESQuery;
	}
	
	@Override
	public ESSort getSort() {
		ESSort esSort = new AuctionSort();
		ScriptSort scriptSort = new ScriptSort();
		scriptSort.setScript(scriptSortManager.getAuctionRecommendSort());
		scriptSort.setDirection(Direction.DESC);
		int userType = (userFavor.getOldUser() != null && userFavor.getOldUser()) ? 2 : 1;
		AuctionRecommendFactor auctionRecommendFactor = new AuctionRecommendFactor();
		Map<String, Object> params = auctionRecommendFactor.buildScriptSortParams(userType);
		scriptSort.setParams(params);
		esSort.addSort(scriptSort);
		return esSort;
	}
}
