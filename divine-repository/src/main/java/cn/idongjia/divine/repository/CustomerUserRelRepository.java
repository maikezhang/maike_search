package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.mapper.user.CustomerUserRelMapper;
import cn.idongjia.divine.db.mybatis.pojo.CustomerUserRelDO;
import cn.idongjia.divine.db.mybatis.query.CustomerUserRelQuery;
import cn.idongjia.log.Log;
import cn.idongjia.log.LogFactory;
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
 * @create at 2018/7/28.
 */
@Repository
public class CustomerUserRelRepository implements CustomerUserRelRepositoryI {
    protected static final Log logger = LogFactory.getLog(CustomerUserRelRepository.class);

    @Resource
    private CustomerUserRelMapper customerUserRelMapper;


    @Async
    @Override
    public Future<Map<Long, Long>> assemble(List<Long> userIds) {
        if (Utils.isEmpty(userIds)) {
            return new AsyncResult<>(new HashMap<>());
        }
        CustomerUserRelQuery    customerQuery      = CustomerUserRelQuery.builder().status(1).userIds(userIds).build();
        long                    now                = Utils.getCurrentMillis();
        List<CustomerUserRelDO> customerUserRelDOS = customerUserRelMapper.select(customerQuery);
        logger.info("execute sql takes {} ms", Utils.getCurrentMillis() - now);
        if (Utils.isEmpty(customerUserRelDOS)) {
            return new AsyncResult<>(new HashMap<>());
        }
        return new AsyncResult<>(customerUserRelDOS.stream().collect(Collectors.toMap(customerUserRelDO -> customerUserRelDO.getUserId().longValue(),
                customerUserRelDO -> customerUserRelDO.getCustomerId().longValue(), (v1, v2) -> v1)));
    }
}
