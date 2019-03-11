package cn.idongjia.divine.db.mybatis.mapper.user.base;

import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import cn.idongjia.divine.db.mybatis.pojo.CustomerDO;
import cn.idongjia.divine.db.mybatis.query.CustomerQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：客户表 db接口
 *
 * @author lc
 * @date 2018/07/31
 */
public interface CustomerBaseMapper extends
        BasePrimaryMapper<Integer, CustomerDO>,
        BaseQueryMapper<CustomerQuery, CustomerDO>,
        BaseSaveMapper<CustomerDO> {

    @Override
    int insert(CustomerDO customerDO);

    @Override
    int update(CustomerDO customerDO);

    @Override
    int deleteByPrimaryKey(@Param("id") Integer id);

    @Override
    CustomerDO getByPrimaryKey(@Param("id") Integer id);

    @Override
    int count(CustomerQuery customerQuery);

    @Override
    List<CustomerDO> select(CustomerQuery customerQuery);
}
