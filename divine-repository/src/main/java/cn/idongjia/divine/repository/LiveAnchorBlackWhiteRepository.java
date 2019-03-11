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
 * @create at 2018/9/1.
 */
@Repository
public class LiveAnchorBlackWhiteRepository implements LiveAnchorBlackWhiteRepostoryI {

    @Resource
    private LiveAnchorBlackWhiteMapper liveAnchorBlackWhiteMapper;

    @Override
    public Map<Long,LiveAnchorBlackWhiteDO> mapStateByCraftsmanIds(List<Long> anchorIds) {
        if(Utils.isEmpty(anchorIds)){
            return new HashMap<>();
        }
        List<LiveAnchorBlackWhiteDO> liveAnchorBlackWhiteDOS = liveAnchorBlackWhiteMapper.select(LiveAnchorBlackWhiteQuery.builder().anchorIds(anchorIds).build());
        if(Utils.isNotEmpty(liveAnchorBlackWhiteDOS)) {
            return liveAnchorBlackWhiteDOS.stream().collect(Collectors.toMap(LiveAnchorBlackWhiteDO::getAnchorId,v1->v1,(v1,v2)->v1));
        }
        return new HashMap<>();
    }
}
