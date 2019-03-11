package cn.idongjia.divine.repository;

import java.util.List;
import java.util.Map;

import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionRelDO;

/**
 * @author lc on 2018/8/16.
 * @class cn.idongjia.divine.repository.AuctionSessionRelRepositoryI
 */
public interface AuctionSessionRelRepositoryI {
    /**
     * 根据直播id查询专场
     *
     * @param itemIds
     * @return
     */
    Map<Long,AuctionSessionRelDO> mapByItemIds(List<Long> itemIds);
	
	List<AuctionSessionRelDO> listBySessionId(Integer asid);



}
