package cn.idongjia.divine.db.mybatis.mapper.kaipao.base;

import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import cn.idongjia.divine.db.mybatis.pojo.LivePureDO;
import cn.idongjia.divine.db.mybatis.query.LivePureQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述： db接口
 *
 * @author lc
 * @date 2018/07/31
 */
public interface LivePureBaseMapper extends
        BasePrimaryMapper<Long, LivePureDO>,
        BaseQueryMapper<LivePureQuery, LivePureDO>,
        BaseSaveMapper<LivePureDO> {

    @Override
    int insert(LivePureDO livePureDO);

    @Override
    int update(LivePureDO livePureDO);

    @Override
    int deleteByPrimaryKey(@Param("id") Long id);

    @Override
    LivePureDO getByPrimaryKey(@Param("id") Long id);

    @Override
    int count(LivePureQuery livePureQuery);

    @Override
    List<LivePureDO> select(LivePureQuery livePureQuery);
}
