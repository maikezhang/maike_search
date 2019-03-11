package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionRelDO;
import cn.idongjia.divine.db.mybatis.pojo.ItemExtDO;
import cn.idongjia.divine.db.mybatis.query.ItemExtQuery;

import java.util.List;
import java.util.Map;

/**
 * @author lc on 2018/8/16.
 * @class cn.idongjia.divine.repository.ItemExtRepositoryI
 */
public interface ItemExtRepositoryI {
    Map<Long,ItemExtDO> mapByItemIds(List<Long> itemIds);

    List<ItemExtDO> list(ItemExtQuery itemExtQuery);
}
