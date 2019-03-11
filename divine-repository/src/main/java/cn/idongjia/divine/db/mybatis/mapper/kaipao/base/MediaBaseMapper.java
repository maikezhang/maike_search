package cn.idongjia.divine.db.mybatis.mapper.kaipao.base;

import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import cn.idongjia.divine.db.mybatis.pojo.MediaDO;
import cn.idongjia.divine.db.mybatis.query.MediaQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：视频图片表 db接口
 *
 * @author lc
 * @date 2018/08/24
 */
public interface MediaBaseMapper extends
        BasePrimaryMapper<Long,MediaDO>,
        BaseQueryMapper<MediaQuery, MediaDO>,
        BaseSaveMapper<MediaDO> {

    @Override
    int insert(MediaDO mediaDO);

    @Override
    int update(MediaDO mediaDO);

    @Override
    int deleteByPrimaryKey(@Param("id") Long id);

    @Override
    MediaDO getByPrimaryKey(@Param("id") Long id);

    @Override
    int count(MediaQuery mediaQuery);

    @Override
    List<MediaDO> select(MediaQuery mediaQuery);
}
