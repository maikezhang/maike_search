package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.pojo.AuctionExtDO;
import cn.idongjia.divine.db.mybatis.query.AuctionExtQuery;

import java.util.List;
import java.util.Map;

/**
 * @author yuexiaodong@idongjia.cn
 * @date 2018-12-18
 */
public interface AuctionExtRepositoryI {

    List<AuctionExtDO> select(AuctionExtQuery query);

    Map<Long, AuctionExtDO> mappingByItemIds(List<Long> itemIds);

}
