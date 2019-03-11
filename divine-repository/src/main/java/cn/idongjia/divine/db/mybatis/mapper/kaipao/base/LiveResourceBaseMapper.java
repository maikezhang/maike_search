package cn.idongjia.divine.db.mybatis.mapper.kaipao.base;

import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import cn.idongjia.divine.db.mybatis.pojo.LiveResourceDO;
import cn.idongjia.divine.db.mybatis.query.LiveResourceQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述： db接口
 *
 * @author lc
 * @date 2018/07/31
 */
public interface LiveResourceBaseMapper extends
        BasePrimaryMapper<Long, LiveResourceDO>,
        BaseQueryMapper<LiveResourceQuery, LiveResourceDO>,
        BaseSaveMapper<LiveResourceDO> {

    @Override
    int insert(LiveResourceDO liveResourceDO);

    @Override
    int update(LiveResourceDO liveResourceDO);

    @Override
    int deleteByPrimaryKey(@Param("id") Long id);

    @Override
    LiveResourceDO getByPrimaryKey(@Param("id") Long id);

    @Override
    int count(LiveResourceQuery liveResourceQuery);

    @Override
    List<LiveResourceDO> select(LiveResourceQuery liveResourceQuery);
}
