package cn.idongjia.divine.repository;

import java.util.List;
import java.util.Map;

import cn.idongjia.divine.db.mybatis.pojo.ItemDO;
import cn.idongjia.divine.db.mybatis.query.ItemQuery;

/**
 * @author lc on 2018/8/16.
 * @class cn.idongjia.divine.repository.ItemRepositoryI
 */
public interface ItemRepositoryI {
    /**
     * 批量获取商品
     * @param itemIds
     * @return
     */
    Map<Long,ItemDO> mapByItemIds(List<Long> itemIds);
	
	List<ItemDO> list(ItemQuery itemQuery);
	
}
