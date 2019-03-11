package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.mapper.kaipao.LiveUserStageRelMapper;
import cn.idongjia.divine.db.mybatis.pojo.LiveUserStageRelDO;
import cn.idongjia.divine.db.mybatis.query.LiveUserStageRelQuery;
import cn.idongjia.divine.utils.Utils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/8/16.
 */
@Repository
public class LiveUserStageRelRepository implements LiveUserStageRelRepositoryI {

    @Resource
    private LiveUserStageRelMapper liveUserStageRelMapper;

    @Override
    public List<LiveUserStageRelDO> list(Long liveId) {
        LiveUserStageRelQuery liveUserStageRelQuery = new LiveUserStageRelQuery();
        liveUserStageRelQuery.setLiveId(liveId);
        return liveUserStageRelMapper.select(liveUserStageRelQuery);
    }

    @Override
    public Map<Long,List<LiveUserStageRelDO>> assembleByLiveId(List<Long> liveIds) {
        List<LiveUserStageRelDO>           liveUserStageRelDOS = liveUserStageRelMapper.listByLiveIds(liveIds);
        Map<Long,List<LiveUserStageRelDO>> liveUserStageMap    = new HashMap<>();
        if(Utils.isNotEmpty(liveUserStageRelDOS)) {
            liveUserStageMap = liveUserStageRelDOS.stream().collect(Collectors.groupingBy(liveUserStageRelDO -> liveUserStageRelDO.getLiveId()));
        }

        return liveUserStageMap;
    }
}
