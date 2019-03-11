package cn.idongjia.divine.db.mybatis.mapper.kaipao.base;

import cn.idongjia.divine.db.mybatis.pojo.LiveUserStageRelDO;
import cn.idongjia.divine.db.mybatis.query.LiveUserStageRelQuery;
import org.apache.ibatis.annotations.Param;
import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import java.util.List;

/**
 * 描述： db接口
 *
 * @author lc
 * @date 2018/08/15
 */
public interface LiveUserStageRelBaseMapper extends
        BasePrimaryMapper<Long, LiveUserStageRelDO>,
        BaseQueryMapper<LiveUserStageRelQuery,LiveUserStageRelDO>,
        BaseSaveMapper<LiveUserStageRelDO> {

    @Override
    int insert(LiveUserStageRelDO liveUserStageRelDO);

    @Override
    int update(LiveUserStageRelDO liveUserStageRelDO);

    @Override
    int deleteByPrimaryKey(@Param("id") Long id);

    @Override
    LiveUserStageRelDO getByPrimaryKey(@Param("id") Long id);

    @Override
    int count(LiveUserStageRelQuery liveUserStageRelQuery);

    @Override
    List<LiveUserStageRelDO> select(LiveUserStageRelQuery liveUserStageRelQuery);
}
