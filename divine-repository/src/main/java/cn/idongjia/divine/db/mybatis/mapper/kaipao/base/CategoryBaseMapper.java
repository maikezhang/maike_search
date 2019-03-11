package cn.idongjia.divine.db.mybatis.mapper.kaipao.base;

import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import cn.idongjia.divine.db.mybatis.pojo.CategoryDO;
import cn.idongjia.divine.db.mybatis.query.CategoryQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：后台真实类目表 db接口
 *
 * @author lc
 * @date 2018/08/15
 */
public interface CategoryBaseMapper extends
        BasePrimaryMapper<Integer,CategoryDO>,
        BaseQueryMapper<CategoryQuery, CategoryDO>,
        BaseSaveMapper<CategoryDO> {

    @Override
    int insert(CategoryDO categoryDO);

    @Override
    int update(CategoryDO categoryDO);

    @Override
    int deleteByPrimaryKey(@Param("id") Integer id);

    @Override
    CategoryDO getByPrimaryKey(@Param("id") Integer id);

    @Override
    int count(CategoryQuery categoryQuery);

    @Override
    List<CategoryDO> select(CategoryQuery categoryQuery);
}
