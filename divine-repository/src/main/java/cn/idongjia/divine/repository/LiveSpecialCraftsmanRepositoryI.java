package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.pojo.LiveAnchorBlackWhiteDO;
import cn.idongjia.divine.db.mybatis.query.LiveAnchorBlackWhiteQuery;
import cn.idongjia.divine.db.mybatis.query.LiveShowQuery;

import java.util.List;
import java.util.Map;

/**
 * @author lc on 2018/9/3.
 * @class cn.idongjia.divine.repository.LiveSpecialCraftsmanRepository
 */
public interface LiveSpecialCraftsmanRepositoryI {

    LiveAnchorBlackWhiteDO getByCraftsmanId(Long craftsmanId);

    /**
     * 根据id集合map名单数据
     * @param craftsmanIds
     * @return
     */
    Map<Long,LiveAnchorBlackWhiteDO> mapByCraftsmanId(List<Long> craftsmanIds);

    int count(LiveAnchorBlackWhiteQuery liveAnchorBlackWhiteQuery);
}
