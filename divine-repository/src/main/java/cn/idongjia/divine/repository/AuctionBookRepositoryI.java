package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.pojo.CountPO;

import java.util.List;
import java.util.Map;

/**
 * @author lc on 2018/8/10.
 * @class cn.idongjia.divine.repository.AuctionBookRepositoryI
 */
public interface AuctionBookRepositoryI {
    int countByContentId(Long contentId,int contentType);

    Map<Long,CountPO> countByContentId(List<Long> sessionIds,Integer contentType);
}
