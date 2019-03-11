package cn.idongjia.divine.db.mybatis.mapper.kaipao.base;

import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import cn.idongjia.divine.db.mybatis.pojo.LiveAnchorBlackWhiteDO;
import cn.idongjia.divine.db.mybatis.query.LiveAnchorBlackWhiteQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：直播主播黑白名单表 db接口
 *
 * @author lc,longchuan@idongjia.cn
 * @date 2018/09/01
 */
public interface LiveAnchorBlackWhiteBaseMapper extends
        BasePrimaryMapper<Long,LiveAnchorBlackWhiteDO>,
        BaseQueryMapper<LiveAnchorBlackWhiteQuery, LiveAnchorBlackWhiteDO>,
        BaseSaveMapper<LiveAnchorBlackWhiteDO> {

    @Override
    int insert(LiveAnchorBlackWhiteDO liveAnchorBlackWhiteDO);

    @Override
    int update(LiveAnchorBlackWhiteDO liveAnchorBlackWhiteDO);

    @Override
    int deleteByPrimaryKey(@Param("id") Long id);

    @Override
    LiveAnchorBlackWhiteDO getByPrimaryKey(@Param("id") Long id);

    @Override
    int count(LiveAnchorBlackWhiteQuery liveAnchorBlackWhiteQuery);

    @Override
    List<LiveAnchorBlackWhiteDO> select(LiveAnchorBlackWhiteQuery liveAnchorBlackWhiteQuery);
}
