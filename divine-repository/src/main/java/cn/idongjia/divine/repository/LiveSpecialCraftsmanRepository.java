package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.mapper.kaipao.LiveAnchorBlackWhiteMapper;
import cn.idongjia.divine.db.mybatis.pojo.LiveAnchorBlackWhiteDO;
import cn.idongjia.divine.db.mybatis.query.LiveAnchorBlackWhiteQuery;
import cn.idongjia.divine.utils.Utils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/9/3.
 */
@Repository
public class LiveSpecialCraftsmanRepository implements LiveSpecialCraftsmanRepositoryI {
    @Resource
    private LiveAnchorBlackWhiteMapper liveAnchorBlackWhiteMapper;

    @Override
    public LiveAnchorBlackWhiteDO getByCraftsmanId(Long craftsmanId) {
        LiveAnchorBlackWhiteQuery liveAnchorBlackWhiteQuery = LiveAnchorBlackWhiteQuery.builder().anchorId(craftsmanId).build();
        liveAnchorBlackWhiteQuery.setLimit(1);
        List<LiveAnchorBlackWhiteDO> liveAnchorBlackWhiteDOS = liveAnchorBlackWhiteMapper.select(liveAnchorBlackWhiteQuery);
        LiveAnchorBlackWhiteDO       liveAnchorBlackWhiteDO  = null;
        if(Utils.isNotEmpty(liveAnchorBlackWhiteDOS)) {
            liveAnchorBlackWhiteDO = liveAnchorBlackWhiteDOS.get(0);
        }
        return liveAnchorBlackWhiteDO;
    }

    /**
     * 根据id集合map名单数据
     *
     * @param craftsmanIds
     * @return
     */
    @Override
    public Map<Long,LiveAnchorBlackWhiteDO> mapByCraftsmanId(List<Long> craftsmanIds) {
        LiveAnchorBlackWhiteQuery        liveAnchorBlackWhiteQuery = LiveAnchorBlackWhiteQuery.builder().anchorIds(craftsmanIds).build();
        List<LiveAnchorBlackWhiteDO>     liveAnchorBlackWhiteDOS   = liveAnchorBlackWhiteMapper.select(liveAnchorBlackWhiteQuery);
        Map<Long,LiveAnchorBlackWhiteDO> liveAnchorBlackWhiteDOMap = new HashMap<>();
        if(Utils.isNotEmpty(liveAnchorBlackWhiteDOS)) {
            liveAnchorBlackWhiteDOMap = liveAnchorBlackWhiteDOS.stream().collect(Collectors.toMap(LiveAnchorBlackWhiteDO::getAnchorId,v1 -> v1,(v1,v2) -> v1));
        }
        return liveAnchorBlackWhiteDOMap;
    }

    @Override
    public int count(LiveAnchorBlackWhiteQuery liveAnchorBlackWhiteQuery) {
        return liveAnchorBlackWhiteMapper.count(liveAnchorBlackWhiteQuery);
    }
}
