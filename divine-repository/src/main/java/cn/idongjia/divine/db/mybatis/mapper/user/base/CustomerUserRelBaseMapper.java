package cn.idongjia.divine.db.mybatis.mapper.user.base;

import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import cn.idongjia.divine.db.mybatis.pojo.CustomerUserRelDO;
import cn.idongjia.divine.db.mybatis.query.CustomerUserRelQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：客户用户关系表 db接口
 *
 * @author lc
 * @date 2018/07/31
 */
public interface CustomerUserRelBaseMapper extends
        BasePrimaryMapper<Integer, CustomerUserRelDO>,
        BaseQueryMapper<CustomerUserRelQuery, CustomerUserRelDO>,
        BaseSaveMapper<CustomerUserRelDO> {

    @Override
    int insert(CustomerUserRelDO customerUserRelDO);

    @Override
    int update(CustomerUserRelDO customerUserRelDO);

    @Override
    int deleteByPrimaryKey(@Param("user_id") Integer user_id);

    @Override
    CustomerUserRelDO getByPrimaryKey(@Param("user_id") Integer user_id);

    @Override
    int count(CustomerUserRelQuery customerUserRelQuery);

    @Override
    List<CustomerUserRelDO> select(CustomerUserRelQuery customerUserRelQuery);
}
