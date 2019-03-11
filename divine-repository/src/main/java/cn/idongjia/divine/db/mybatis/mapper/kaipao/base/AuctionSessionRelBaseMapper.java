package cn.idongjia.divine.db.mybatis.mapper.kaipao.base;

import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionRelDO;
import cn.idongjia.divine.db.mybatis.query.AuctionSessionRelQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述： db接口
 *
 * @author lc
 * @date 2018/07/31
 */
public interface AuctionSessionRelBaseMapper extends
        BasePrimaryMapper<Integer, AuctionSessionRelDO>,
        BaseQueryMapper<AuctionSessionRelQuery, AuctionSessionRelDO>,
        BaseSaveMapper<AuctionSessionRelDO> {

    @Override
    int insert(AuctionSessionRelDO auctionSessionRelDO);

    @Override
    int update(AuctionSessionRelDO auctionSessionRelDO);

    @Override
    int deleteByPrimaryKey(@Param("asrid") Integer asrid);

    @Override
    AuctionSessionRelDO getByPrimaryKey(@Param("asrid") Integer asrid);

    @Override
    int count(AuctionSessionRelQuery auctionSessionRelQuery);

    @Override
    List<AuctionSessionRelDO> select(AuctionSessionRelQuery auctionSessionRelQuery);
}
