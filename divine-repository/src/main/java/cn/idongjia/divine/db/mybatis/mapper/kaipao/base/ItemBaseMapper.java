package cn.idongjia.divine.db.mybatis.mapper.kaipao.base;

import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import cn.idongjia.divine.db.mybatis.pojo.ItemDO;
import cn.idongjia.divine.db.mybatis.query.ItemQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：商品表 db接口
 *
 * @author lc
 * @date 2018/08/09
 */
public interface ItemBaseMapper extends
        BasePrimaryMapper<Long,ItemDO>,
        BaseQueryMapper<ItemQuery, ItemDO>,
        BaseSaveMapper<ItemDO> {

    @Override
    int insert(ItemDO itemDO);

    @Override
    int update(ItemDO itemDO);

    @Override
    int deleteByPrimaryKey(@Param("iid") Long iid);

    @Override
    ItemDO getByPrimaryKey(@Param("iid") Long iid);

    @Override
    int count(ItemQuery itemQuery);

    @Override
    List<ItemDO> select(ItemQuery itemQuery);
}
