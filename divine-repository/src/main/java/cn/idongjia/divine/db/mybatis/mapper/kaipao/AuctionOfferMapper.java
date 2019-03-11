package cn.idongjia.divine.db.mybatis.mapper.kaipao;

import cn.idongjia.divine.db.mybatis.mapper.kaipao.base.AuctionOfferBaseMapper;
import cn.idongjia.divine.db.mybatis.pojo.AuctionOfferUserDO;
import cn.idongjia.divine.db.mybatis.pojo.CountPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：拍品出价内容表 的自定义接口，所有自定义接口均定义在此
 *
 * @author lc
 * @date 2018/08/16
 */
public interface AuctionOfferMapper extends AuctionOfferBaseMapper {
    List<AuctionOfferUserDO> groupByItemId(@Param("itemIds") List<Long> itemIds);

    List<CountPO> groupOffer(@Param("sessionIds") List<Long> sessionIds,@Param("limit") Integer limit,@Param("offset") Integer offset);
}
