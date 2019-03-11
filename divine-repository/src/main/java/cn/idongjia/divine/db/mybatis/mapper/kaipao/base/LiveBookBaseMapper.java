package cn.idongjia.divine.db.mybatis.mapper.kaipao.base;

import cn.idongjia.divine.db.mybatis.pojo.LiveBookDO;
import cn.idongjia.divine.db.mybatis.query.LiveBookQuery;
import org.apache.ibatis.annotations.Param;
import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import java.util.List;

/**
 * 描述： db接口
 *
 * @author lc
 * @date 2018/08/10
 */
public interface LiveBookBaseMapper extends
        BasePrimaryMapper<Long, LiveBookDO>,
        BaseQueryMapper<LiveBookQuery,LiveBookDO>,
        BaseSaveMapper<LiveBookDO> {

    @Override
    int insert(LiveBookDO liveBookDO);

    @Override
    int update(LiveBookDO liveBookDO);

    @Override
    int deleteByPrimaryKey(@Param("id") Long id);

    @Override
    LiveBookDO getByPrimaryKey(@Param("id") Long id);

    @Override
    int count(LiveBookQuery liveBookQuery);

    @Override
    List<LiveBookDO> select(LiveBookQuery liveBookQuery);
}
