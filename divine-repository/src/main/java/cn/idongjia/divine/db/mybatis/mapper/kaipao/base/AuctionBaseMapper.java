package cn.idongjia.divine.db.mybatis.mapper.kaipao.base;

import cn.idongjia.divine.db.mybatis.pojo.AuctionDO;
import cn.idongjia.divine.db.mybatis.query.AuctionQuery;
import org.apache.ibatis.annotations.Param;
import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import java.util.List;

/**
 * 描述： db接口
 *
 * @author lc
 * @date 2018/08/16
 */
public interface AuctionBaseMapper extends
        BasePrimaryMapper<Long, AuctionDO>,
        BaseQueryMapper<AuctionQuery,AuctionDO>,
        BaseSaveMapper<AuctionDO> {

    @Override
    int insert(AuctionDO auctionDO);

    @Override
    int update(AuctionDO auctionDO);

    @Override
    int deleteByPrimaryKey(@Param("id") Long id);

    @Override
    AuctionDO getByPrimaryKey(@Param("id") Long id);

    AuctionDO getByItemId(@Param("itemId") Long itemId);
    @Override
    int count(AuctionQuery auctionQuery);

    @Override
    List<AuctionDO> select(AuctionQuery auctionQuery);
}
