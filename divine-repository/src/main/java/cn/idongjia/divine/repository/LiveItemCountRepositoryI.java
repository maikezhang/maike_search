package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.pojo.CountPO;
import cn.idongjia.divine.db.mybatis.pojo.ItemRelPO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author lc on 2018/8/6.
 * @class cn.idongjia.divine.repository.LiveItemCountRepositoryI
 */
public interface LiveItemCountRepositoryI extends RepositoryI {
    Future<Map<Long, CountPO>> assemble(List<Long> pureLiveIds);

    Future<Map<Long, List<ItemRelPO>>> assembleItems(List<Long> pureLiveIds);

    List<Long> assembleByItemIds(List<Long> itemIds);
}
