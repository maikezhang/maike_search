package cn.idongjia.divine.db.mybatis.mapper.kaipao.base;

import cn.idongjia.divine.db.mybatis.pojo.AuctionOfferDO;
import cn.idongjia.divine.db.mybatis.query.AuctionOfferQuery;
import org.apache.ibatis.annotations.Param;
import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import java.util.List;

/**
 * 描述：拍品出价内容表 db接口
 *
 * @author lc
 * @date 2018/08/16
 */
public interface AuctionOfferBaseMapper extends
        BasePrimaryMapper<Long,AuctionOfferDO>,
        BaseQueryMapper<AuctionOfferQuery, AuctionOfferDO>,
        BaseSaveMapper<AuctionOfferDO> {

    @Override
    int insert(AuctionOfferDO auctionOfferDO);

    @Override
    int update(AuctionOfferDO auctionOfferDO);

    @Override
    int deleteByPrimaryKey(@Param("zmid") Long zmid);

    @Override
    AuctionOfferDO getByPrimaryKey(@Param("zmid") Long zmid);

    @Override
    int count(AuctionOfferQuery auctionOfferQuery);

    @Override
    List<AuctionOfferDO> select(AuctionOfferQuery auctionOfferQuery);
}
