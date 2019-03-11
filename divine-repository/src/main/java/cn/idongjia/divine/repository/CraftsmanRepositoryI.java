package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.pojo.CraftsmanDO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author lc on 2018/8/6.
 * @class cn.idongjia.divine.repository.CraftsmanRepositoryI
 */
public interface CraftsmanRepositoryI {
    Future<Map<Long, CraftsmanDO>> mapByCustomerId(List<Long> customerIds);
}
