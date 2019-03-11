package cn.idongjia.divine.db.mybatis.mapper.kaipao;


import cn.idongjia.divine.db.mybatis.mapper.kaipao.base.AuctionSessionBaseMapper;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionRelDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述： 的自定义接口，所有自定义接口均定义在此
 *
 * @author lc
 * @date 2018/07/31
 */
public interface AuctionSessionMapper extends AuctionSessionBaseMapper {

    List<AuctionSessionRelDO> listByItemIds(@Param("itemIds") List<Long> itemIds);
}
