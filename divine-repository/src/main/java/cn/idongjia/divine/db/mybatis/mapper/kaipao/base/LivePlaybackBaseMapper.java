package cn.idongjia.divine.db.mybatis.mapper.kaipao.base;

import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import cn.idongjia.divine.db.mybatis.pojo.LivePlaybackDO;
import cn.idongjia.divine.db.mybatis.query.LivePlaybackQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：回放表 db接口
 *
 * @author lc
 * @date 2018/07/31
 */
public interface LivePlaybackBaseMapper extends
        BasePrimaryMapper<Long, LivePlaybackDO>,
        BaseQueryMapper<LivePlaybackQuery, LivePlaybackDO>,
        BaseSaveMapper<LivePlaybackDO> {

    @Override
    int insert(LivePlaybackDO livePlaybackDO);

    @Override
    int update(LivePlaybackDO livePlaybackDO);

    @Override
    int deleteByPrimaryKey(@Param("id") Long id);

    @Override
    LivePlaybackDO getByPrimaryKey(@Param("id") Long id);

    @Override
    int count(LivePlaybackQuery livePlaybackQuery);

    @Override
    List<LivePlaybackDO> select(LivePlaybackQuery livePlaybackQuery);


}
