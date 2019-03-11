package cn.idongjia.divine.repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author lc on 2018/8/6.
 * @class cn.idongjia.divine.repository.CustomerUserRelRepositoryI
 */
public interface CustomerUserRelRepositoryI extends RepositoryI {
    Future<Map<Long, Long>> assemble(List<Long> userIds);
}
