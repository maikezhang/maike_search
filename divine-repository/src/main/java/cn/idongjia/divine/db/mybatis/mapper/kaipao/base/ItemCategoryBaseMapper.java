package cn.idongjia.divine.db.mybatis.mapper.kaipao.base;

import cn.idongjia.divine.db.mybatis.pojo.ItemCategoryDO;
import cn.idongjia.divine.db.mybatis.query.ItemCategoryQuery;
import org.apache.ibatis.annotations.Param;
import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import java.util.List;

/**
 * 描述：类目表 db接口
 *
 * @author lc
 * @date 2018/08/16
 */
public interface ItemCategoryBaseMapper extends
        BasePrimaryMapper<Integer, ItemCategoryDO>,
        BaseQueryMapper<ItemCategoryQuery,ItemCategoryDO>,
        BaseSaveMapper<ItemCategoryDO> {

    @Override
    int insert(ItemCategoryDO itemCategoryDO);

    @Override
    int update(ItemCategoryDO itemCategoryDO);

    @Override
    int deleteByPrimaryKey(@Param("icid") Integer icid);

    @Override
    ItemCategoryDO getByPrimaryKey(@Param("icid") Integer icid);

    @Override
    int count(ItemCategoryQuery itemCategoryQuery);

    @Override
    List<ItemCategoryDO> select(ItemCategoryQuery itemCategoryQuery);
}
