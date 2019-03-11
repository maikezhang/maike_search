package cn.idongjia.divine.query.factory.auction;

import cn.idongjia.desert.dto.WordsDTO;
import cn.idongjia.desert.query.SegQuery;
import cn.idongjia.divine.lib.pojo.request.auction.AuctionQry;
import cn.idongjia.divine.lib.pojo.request.sort.AuctionSort;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.query.AuctionESQuery;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.dw.lib.pojo.community.UserFavor;
import cn.idongjia.se.lib.engine.query.sort.Sort;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/8/21.
 */
public class AuctionSelfQueryFactory extends AuctionBaseQueryFactrory {

    public AuctionSelfQueryFactory(AuctionQry auctionQry,UserFavor userFavor) {
        super(auctionQry,userFavor);
    }

    @Override
    public AuctionESQuery getQuery() {
        AuctionESQuery auctionESQuery = new AuctionESQuery();
        setCommonQuery(auctionESQuery, auctionQry);
        String       itemTitle = auctionQry.getItemTitle();
        List<String> words     = null;
        if(!Utils.isEmpty(itemTitle)) {
            SegQuery query = new SegQuery(itemTitle);
            query.setSegMode((short)0);
            List<WordsDTO> wordsList = segManager.seg(query);
            if(!Utils.isEmpty(wordsList)) {
                words = wordsList.stream().map(word -> word.getWordStr()).collect(Collectors.toList());
                auctionESQuery.setItemTitleSmart(words);
            }
        }
        List<Long> itemIds = auctionQry.getItemIds();
        if(Utils.isNotEmpty(itemIds)) {
            auctionESQuery.setItemIds(itemIds);
        }
        List<Long> excludeCategoryIds = auctionQry.getExcludeCategoryIds();
        if(!Utils.isEmpty(excludeCategoryIds)) {
            auctionESQuery.setNoCategoryIds(excludeCategoryIds);
        }
        List<Long> categoryIds = auctionQry.getCategoryIds();
        if(!Utils.isEmpty(categoryIds)) {
            auctionESQuery.setCategoryIds(categoryIds);
        }
        auctionESQuery.setCraftsmanIds(auctionQry.getCraftsmanIds());
        auctionESQuery.setSessionIds(auctionQry.getSessionIds());
        auctionESQuery.setItemExtStatus(auctionQry.getItemExtStatus());
        auctionESQuery.setItemStatus(auctionQry.getItemStatus());
        Integer sessionPreview = auctionQry.getSessionPreview();
        auctionESQuery.setSessionPreviewStatus(sessionPreview);
        List<Integer> sessionStates = auctionQry.getSessionStates();
        auctionESQuery.setSessionStates(sessionStates);
        auctionESQuery.setSessionTypes(auctionQry.getSessionTypes());
        auctionESQuery.setZooIds(auctionQry.getZooIds());
        List<Integer> sessionStatus = auctionQry.getSessionStatus();
        auctionESQuery.setSessionStatus(sessionStatus);
        List<Long> groundIds = auctionQry.getGroundIds();
        auctionESQuery.setGroundIds(groundIds);
        List<Long> excludeGroundIds = auctionQry.getExcludeGroundIds();
        auctionESQuery.setNoGroundIds(excludeGroundIds);
        auctionESQuery.setAuctionStatus(auctionQry.getAuctionStatus());
        auctionESQuery.setAuctionTypes(auctionQry.getAuctionTypes());
        auctionESQuery.setAuctionState(auctionQry.getAuctionStates());
        auctionESQuery.setStart(auctionQry.getOffset());
        auctionESQuery.setNum(auctionQry.getLimit());
        auctionESQuery.setCraftsmanStatusList(auctionQry.getCraftsmanStatusList());
        return auctionESQuery;
    }

    @Override
    public ESSort getSort() {
        ESSort     esSort = new AuctionSort();
        List<Sort> sorts  = auctionQry.getSorts();
        if(Utils.isNotEmpty(sorts)) {
            esSort.addSorts(sorts);
        }
        return esSort;
    }
}
