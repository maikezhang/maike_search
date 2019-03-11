package cn.idongjia.divine.db.mybatis.mapper.kaipao.base;

import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import cn.idongjia.divine.db.mybatis.pojo.LiveVideoCoverDO;
import cn.idongjia.divine.db.mybatis.query.LiveVideoCoverQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：直播小视频封面 db接口
 *
 * @author lc
 * @date 2018/07/31
 */
public interface LiveVideoCoverBaseMapper extends
        BasePrimaryMapper<Long, LiveVideoCoverDO>,
        BaseQueryMapper<LiveVideoCoverQuery, LiveVideoCoverDO>,
        BaseSaveMapper<LiveVideoCoverDO> {

    @Override
    int insert(LiveVideoCoverDO liveVideoCoverDO);

    @Override
    int update(LiveVideoCoverDO liveVideoCoverDO);

    @Override
    int deleteByPrimaryKey(@Param("id") Long id);

    @Override
    LiveVideoCoverDO getByPrimaryKey(@Param("id") Long id);

    @Override
    int count(LiveVideoCoverQuery liveVideoCoverQuery);

    @Override
    List<LiveVideoCoverDO> select(LiveVideoCoverQuery liveVideoCoverQuery);
}
