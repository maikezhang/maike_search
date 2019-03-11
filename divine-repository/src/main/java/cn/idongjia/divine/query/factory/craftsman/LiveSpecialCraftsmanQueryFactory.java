package cn.idongjia.divine.query.factory.craftsman;

import cn.idongjia.desert.dto.WordsDTO;
import cn.idongjia.desert.query.SegQuery;
import cn.idongjia.divine.lib.pojo.request.live.CraftsmanQry;
import cn.idongjia.divine.lib.pojo.request.sort.AuctionSort;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.query.CraftsmanESQuery;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.dw.lib.pojo.community.UserFavor;
import cn.idongjia.se.lib.engine.query.sort.Sort;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/9/3.
 */
public class LiveSpecialCraftsmanQueryFactory extends CraftsmanBaseQueryFactrory {
    public LiveSpecialCraftsmanQueryFactory(CraftsmanQry craftsmanQry) {
        super(craftsmanQry);
    }

    /**
     * 获取查询条件
     *
     * @return
     */
    @Override
    public CraftsmanESQuery getQuery() {
        CraftsmanESQuery craftsmanESQuery = new CraftsmanESQuery();
        String           craftsmanName    = craftsmanQry.getCraftsmanName();
        if(Utils.isNotEmpty(craftsmanName)) {
            List<WordsDTO> wordsDTOS = segManager.seg(new SegQuery(craftsmanName));
            if(Utils.isNotEmpty(wordsDTOS)) {
                List<String> words = wordsDTOS.stream().map(wordsDTO -> wordsDTO.getWordStr()).collect(Collectors.toList());
                craftsmanESQuery.setCraftsmanNameSmart(words);
            }
        }
        craftsmanESQuery.setCraftsmanUserId(craftsmanQry.getUserId());
        craftsmanESQuery.setShowTypes(craftsmanQry.getShowType());
        craftsmanESQuery.setNum(craftsmanQry.getLimit());
        craftsmanESQuery.setStart(craftsmanQry.getOffset());
        return craftsmanESQuery;
    }

    /**
     * 获取排序条件
     *
     * @return
     */
    @Override
    public ESSort getSort() {
        ESSort     esSort = new AuctionSort();
        List<Sort> sorts  = craftsmanQry.getSorts();
        if(Utils.isNotEmpty(sorts)) {
            esSort.addSorts(sorts);
        }
        return esSort;
    }
}
