package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.pojo.ZooDO;
import cn.idongjia.zoo.pojo.ZooCountCO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author lc on 2018/8/6.
 * @class cn.idongjia.divine.repository.ZooRepositoryI
 */
public interface ZooRepositoryI extends RepositoryI {
    Future<Map<Long, ZooDO>> assemble(List<Long> zooIds);

    Future<Map<Long, Integer>> assemble(List<Long> zooIds, Map<Long, Long> zooLiveMap);

    Future<Map<Long,ZooCountCO>> assembleCount(List<Long> zooIds);
}
