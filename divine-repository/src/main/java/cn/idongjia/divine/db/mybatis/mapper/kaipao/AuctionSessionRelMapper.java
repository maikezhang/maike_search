package cn.idongjia.divine.db.mybatis.mapper.kaipao;


import cn.idongjia.divine.db.mybatis.mapper.kaipao.base.AuctionSessionRelBaseMapper;
import cn.idongjia.divine.db.mybatis.pojo.CountPO;
import cn.idongjia.divine.db.mybatis.pojo.ItemRelPO;
import cn.idongjia.divine.db.mybatis.query.AuctionSessionRelQuery;

import java.util.List;

/**
 * 描述： 的自定义接口，所有自定义接口均定义在此
 *
 * @author lc
 * @date 2018/07/31
 */
public interface AuctionSessionRelMapper extends AuctionSessionRelBaseMapper {
    public List<CountPO> countValid(AuctionSessionRelQuery auctionSessionRelQuery);
    public List<ItemRelPO> listItem(AuctionSessionRelQuery auctionSessionRelQuery);

}
