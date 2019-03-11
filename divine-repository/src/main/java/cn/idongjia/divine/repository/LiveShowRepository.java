package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.mapper.kaipao.LiveShowMapper;
import cn.idongjia.divine.db.mybatis.pojo.LiveShowDO;
import cn.idongjia.divine.db.mybatis.query.LiveShowQuery;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lc
 * @create at 2018/8/3.
 */
@Repository
public class LiveShowRepository implements LiveShowRepositoryI {
    @Resource
    private LiveShowMapper liveShowMapper;

    @Override
    public List<LiveShowDO> select(LiveShowQuery liveShowQuery) {
        return liveShowMapper.select(liveShowQuery);
    }

    @Override
    public LiveShowDO getByPrimaryKey(Long liveId) {
        return liveShowMapper.getByPrimaryKey(liveId);
    }

    @Override
    public int count(LiveShowQuery liveShowQuery) {
        return liveShowMapper.count(liveShowQuery);
    }
}
