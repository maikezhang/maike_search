package cn.idongjia.divine.db.mybatis.mapper.kaipao;


import cn.idongjia.divine.db.mybatis.mapper.kaipao.base.AuctionBaseMapper;
import cn.idongjia.divine.db.mybatis.pojo.AuctionStateDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述： 的自定义接口，所有自定义接口均定义在此
 *
 * @author lc
 * @date 2018/08/16
 */
public interface AuctionMapper extends AuctionBaseMapper {
    List<AuctionStateDO> groupCraftsmanAuctionBystate(@Param("userIds") List<Long> userIds,
                                                      @Param("limit") Integer limit,
                                                      @Param("offset") Integer offset,
                                                      @Param("itemId") Long itemId);

    Long countCraftsmanAuctionByState();

}
