package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.pojo.CountPO;
import cn.idongjia.divine.db.mybatis.pojo.ItemRelPO;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author lc on 2018/8/6.
 * @class cn.idongjia.divine.repository.SessionItemCountRepositoryI
 */
public interface SessionItemCountRepositoryI extends RepositoryI {
    @Async
    Future<Map<Long, CountPO>> assemble(List<Long> sessionIds, Map<Long, Long> sessionLiveIdMap);

    Map<Long, Integer> assembleRelCount(List<Long> sessionIds);

    Future<Map<Long,List<ItemRelPO>>> assembleItems(List<Long> sessionIds, Map<Long, Long> sessionLiveIdMap);

}
