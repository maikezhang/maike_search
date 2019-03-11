package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.mapper.user.CraftsmanMapper;
import cn.idongjia.divine.db.mybatis.pojo.CraftsmanDO;
import cn.idongjia.divine.db.mybatis.query.CraftsmanQuery;
import cn.idongjia.util.Utils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/7/31.
 */
@Repository
public class CraftsmanRepository implements CraftsmanRepositoryI {
    @Resource
    private CraftsmanMapper craftsmanMapper;

    @Async
    @Override
    public Future<Map<Long, CraftsmanDO>> mapByCustomerId(List<Long> customerIds) {
        if (Utils.isEmpty(customerIds)) {
            return new AsyncResult<>(new HashMap<>());
        }
        CraftsmanQuery    craftsmanQuery = CraftsmanQuery.builder().customerIds(customerIds).build();
        List<CraftsmanDO> craftsmanDOS   = craftsmanMapper.select(craftsmanQuery);
        if (Utils.isEmpty(craftsmanDOS)) {
            return new AsyncResult<>(new HashMap<>());
        }
        return new AsyncResult<>(craftsmanDOS.stream().collect(Collectors.toMap(CraftsmanDO::getCustomerId, v1 -> v1, (v1, v2) -> v1)));
    }
}
