package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.mapper.user.CustomerMapper;
import cn.idongjia.divine.db.mybatis.pojo.CustomerDO;
import cn.idongjia.divine.db.mybatis.query.CustomerQuery;
import cn.idongjia.util.Utils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/7/28.
 */
@Repository
public class CustomerRepository implements CustomerRepositoryI {
    @Resource
    private CustomerMapper customerMapper;


    @Async
    @Override
    public Future<Map<Long, CustomerDO>> mapByCustomerId(List<Long> customerIds) {
        if (Utils.isEmpty(customerIds)) {
            return new AsyncResult<>(Collections.emptyMap());
        }
        CustomerQuery    customerQuery = CustomerQuery.builder().customerIds(customerIds).build();
        List<CustomerDO> customerDOS   = customerMapper.select(customerQuery);
        if (Utils.isEmpty(customerDOS)) {
            return new AsyncResult<>(Collections.emptyMap());
        }
        return new AsyncResult<>(customerDOS.stream().collect(Collectors.toMap(v1 -> v1.getId().longValue(), v1 -> v1, (v1, v2) -> v1)));
    }
}
