package cn.idongjia.divine.db.mybatis.mapper.kaipao.base;

import cn.idongjia.divine.db.mybatis.pojo.ItemExtDO;
import cn.idongjia.divine.db.mybatis.query.ItemExtQuery;
import org.apache.ibatis.annotations.Param;
import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import java.util.List;

/**
 * 描述：拍品扩展表 db接口
 *
 * @author lc
 * @date 2018/08/16
 */
public interface ItemExtBaseMapper extends
        BasePrimaryMapper<Long,ItemExtDO>,
        BaseQueryMapper<ItemExtQuery, ItemExtDO>,
        BaseSaveMapper<ItemExtDO> {

    @Override
    int insert(ItemExtDO itemExtDO);

    @Override
    int update(ItemExtDO itemExtDO);

    @Override
    int deleteByPrimaryKey(@Param("iid") Long iid);

    @Override
    ItemExtDO getByPrimaryKey(@Param("iid") Long iid);

    @Override
    int count(ItemExtQuery itemExtQuery);

    @Override
    List<ItemExtDO> select(ItemExtQuery itemExtQuery);
}
