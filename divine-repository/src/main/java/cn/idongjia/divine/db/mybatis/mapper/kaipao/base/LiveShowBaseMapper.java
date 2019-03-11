package cn.idongjia.divine.db.mybatis.mapper.kaipao.base;

import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import cn.idongjia.divine.db.mybatis.pojo.LiveShowDO;
import cn.idongjia.divine.db.mybatis.query.LiveShowQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：基本直播信息表 db接口
 *
 * @author lc
 * @date 2018/07/31
 */
public interface LiveShowBaseMapper extends
        BasePrimaryMapper<Long, LiveShowDO>,
        BaseQueryMapper<LiveShowQuery, LiveShowDO>,
        BaseSaveMapper<LiveShowDO> {

    @Override
    int insert(LiveShowDO liveShowDO);

    @Override
    int update(LiveShowDO liveShowDO);

    @Override
    int deleteByPrimaryKey(@Param("id") Long id);

    @Override
    LiveShowDO getByPrimaryKey(@Param("id") Long id);

    @Override
    int count(LiveShowQuery liveShowQuery);

    @Override
    List<LiveShowDO> select(LiveShowQuery liveShowQuery);
}
