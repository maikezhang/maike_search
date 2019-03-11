package cn.idongjia.divine.db.mybatis.mapper.kaipao.base;

import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import cn.idongjia.divine.db.mybatis.pojo.CraftsmanCategoryAuthDO;
import cn.idongjia.divine.db.mybatis.query.CraftsmanCategoryAuthQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：匠人类目授权表 db接口
 *
 * @author lc
 * @date 2018/08/15
 */
public interface CraftsmanCategoryAuthBaseMapper extends
        BasePrimaryMapper<Long,CraftsmanCategoryAuthDO>,
        BaseQueryMapper<CraftsmanCategoryAuthQuery, CraftsmanCategoryAuthDO>,
        BaseSaveMapper<CraftsmanCategoryAuthDO> {

    @Override
    int insert(CraftsmanCategoryAuthDO craftsmanCategoryAuthDO);

    @Override
    int update(CraftsmanCategoryAuthDO craftsmanCategoryAuthDO);

    @Override
    int deleteByPrimaryKey(@Param("id") Long id);

    @Override
    CraftsmanCategoryAuthDO getByPrimaryKey(@Param("id") Long id);

    @Override
    int count(CraftsmanCategoryAuthQuery craftsmanCategoryAuthQuery);

    @Override
    List<CraftsmanCategoryAuthDO> select(CraftsmanCategoryAuthQuery craftsmanCategoryAuthQuery);
}
