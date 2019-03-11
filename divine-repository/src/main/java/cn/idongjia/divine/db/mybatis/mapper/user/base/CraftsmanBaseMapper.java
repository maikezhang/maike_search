package cn.idongjia.divine.db.mybatis.mapper.user.base;

import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import cn.idongjia.divine.db.mybatis.pojo.CraftsmanDO;
import cn.idongjia.divine.db.mybatis.query.CraftsmanQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述： db接口
 *
 * @author lc
 * @date 2018/07/31
 */
public interface CraftsmanBaseMapper extends
        BasePrimaryMapper<Long, CraftsmanDO>,
        BaseQueryMapper<CraftsmanQuery, CraftsmanDO>,
        BaseSaveMapper<CraftsmanDO> {

    @Override
    int insert(CraftsmanDO craftsmanDO);

    @Override
    int update(CraftsmanDO craftsmanDO);

    @Override
    int deleteByPrimaryKey(@Param("id") Long id);

    @Override
    CraftsmanDO getByPrimaryKey(@Param("id") Long id);

    @Override
    int count(CraftsmanQuery craftsmanQuery);

    @Override
    List<CraftsmanDO> select(CraftsmanQuery craftsmanQuery);
}
