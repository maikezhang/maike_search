package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.mapper.kaipao.ItemExtMapper;
import cn.idongjia.divine.db.mybatis.pojo.ItemExtDO;
import cn.idongjia.divine.db.mybatis.query.ItemExtQuery;
import cn.idongjia.divine.utils.Utils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/8/16.
 */
@Repository
public class ItemExtRepository implements ItemExtRepositoryI {

    @Resource
    private ItemExtMapper itemExtMapper;

    @Override
    public Map<Long,ItemExtDO> mapByItemIds(List<Long> itemIds) {
        if(Utils.isEmpty(itemIds)) {
            return new HashMap<>();
        }
        Map<Long,ItemExtDO> itemExtDOMap = new HashMap<>();
        ItemExtQuery        itemExtQuery = ItemExtQuery.builder().itemIds(itemIds).build();
        List<ItemExtDO>     itemExtDOS   = itemExtMapper.select(itemExtQuery);
        if(Utils.isNotEmpty(itemExtDOS)) {
            itemExtDOMap = itemExtDOS.stream().collect(Collectors.toMap(ItemExtDO::getIid,v1 -> v1,(v1,v2) -> v1));
        }
        return itemExtDOMap;
    }

    @Override
    public List<ItemExtDO> list(ItemExtQuery itemExtQuery) {
        return itemExtMapper.select(itemExtQuery);
    }
}
