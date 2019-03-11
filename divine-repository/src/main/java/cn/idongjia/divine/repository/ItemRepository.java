package cn.idongjia.divine.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.idongjia.divine.db.mybatis.mapper.kaipao.ItemMapper;
import cn.idongjia.divine.db.mybatis.pojo.ItemDO;
import cn.idongjia.divine.db.mybatis.query.ItemQuery;
import cn.idongjia.divine.utils.Utils;

/**
 * @author lc
 * @create at 2018/8/16.
 */
@Repository
public class ItemRepository implements ItemRepositoryI {

    @Resource
    private ItemMapper itemMapper;

    /**
     * 批量获取商品
     *
     * @param itemIds
     * @return
     */
    @Override
    public Map<Long,ItemDO> mapByItemIds(List<Long> itemIds) {
        if(Utils.isEmpty(itemIds)) {
            return new HashMap<>();
        }
        ItemQuery        itemQuery = ItemQuery.builder().itemIds(itemIds).build();
        List<ItemDO>     itemDOS   = itemMapper.select(itemQuery);
        Map<Long,ItemDO> itemDOMap = new HashMap<>();
        if(Utils.isNotEmpty(itemDOS)) {
            itemDOMap = itemDOS.stream().collect(Collectors.toMap(ItemDO::getIid,v1 -> v1,(v1,v2) -> v1));
        }
        return itemDOMap;
    }
	
	@Override
	public List<ItemDO> list(ItemQuery itemQuery) {
		return itemMapper.select(itemQuery);
	}
}
