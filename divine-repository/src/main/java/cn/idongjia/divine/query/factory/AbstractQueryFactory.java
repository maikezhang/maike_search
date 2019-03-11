package cn.idongjia.divine.query.factory;

import java.util.Map;

import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.se.lib.engine.query.LeafQuery;
import cn.idongjia.se.lib.engine.query.boost.TermsField;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lc
 * @create at 2018/8/21.
 */
@Slf4j
public abstract class AbstractQueryFactory<T extends LeafQuery> {
    //直播、拍卖推荐 算法组个性化数据数量限制
    public static final int FOLLOW_CRAFTSMAN_MAX_NUM = 50;
    public static final int FOLLOW_LIVE_MAX_NUM      = 20;
    public static final int FOLLOW_AUCTION_MAX_NUM   = 20;
    public static final int USER_CATEGORY_MAX_NUM    = 20;
    public static final int USER_EXT_ATTR_MAX_NUM    = 20;

	/**
	 * 获取查询条件
	 * 
	 * @return
	 */
    public abstract T getQuery();

	/**
	 * 获取排序条件
	 * 
	 * @return
	 */
    public abstract ESSort getSort();

    protected TermsField<Long> buildTermsField(Map<Long,Double> map,int max,String message) {
        final TermsField<Long> termsField = new TermsField<Long>();

        if(null != map && map.size() > 0) {
            if(map.size() > max) {
                log.error("用户{}偏好数据超出{}限制",message,max);
            }

            map.forEach((id,score) -> {
                if(id != null && score != null) {
                    termsField.addTerm(id,score);
                }
            });
        }

        if(null == termsField.getBoostFields() || termsField.getBoostFields().size() == 0) {
            return null;
        }

        return termsField;
    }
}
