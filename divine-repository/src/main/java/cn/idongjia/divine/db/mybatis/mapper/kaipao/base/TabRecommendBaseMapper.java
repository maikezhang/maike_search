package cn.idongjia.divine.db.mybatis.mapper.kaipao.base;

import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import cn.idongjia.divine.db.mybatis.pojo.TabRecommendDO;
import cn.idongjia.divine.db.mybatis.query.TabRecommendQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：只包含加精数据，未加精的会被删除 db接口
 *
 * @author lc
 * @date 2018/07/31
 */
public interface TabRecommendBaseMapper extends
        BasePrimaryMapper<Long, TabRecommendDO>,
        BaseQueryMapper<TabRecommendQuery, TabRecommendDO>,
        BaseSaveMapper<TabRecommendDO> {

    @Override
    int insert(TabRecommendDO tabRecommendDO);

    @Override
    int update(TabRecommendDO tabRecommendDO);

    @Override
    int deleteByPrimaryKey(@Param("rid") Long rid);

    @Override
    TabRecommendDO getByPrimaryKey(@Param("rid") Long rid);

    @Override
    int count(TabRecommendQuery tabRecommendQuery);

    @Override
    List<TabRecommendDO> select(TabRecommendQuery tabRecommendQuery);
}
