package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.pojo.CustomerDO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author lc on 2018/8/6.
 * @class cn.idongjia.divine.repository.CustomerRepositoryI
 */
public interface CustomerRepositoryI extends RepositoryI {
    Future<Map<Long, CustomerDO>> mapByCustomerId(List<Long> customerIds);
}
