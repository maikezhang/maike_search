package cn.idongjia.divine.db.mybatis.mapper.kaipao.base;

import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionDO;
import cn.idongjia.divine.db.mybatis.query.AuctionSessionQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述： db接口
 *
 * @author lc
 * @date 2018/07/31
 */
public interface AuctionSessionBaseMapper extends
        BasePrimaryMapper<Integer, AuctionSessionDO>,
        BaseQueryMapper<AuctionSessionQuery, AuctionSessionDO>,
        BaseSaveMapper<AuctionSessionDO> {

    @Override
    int insert(AuctionSessionDO auctionSessionDO);

    @Override
    int update(AuctionSessionDO auctionSessionDO);

    @Override
    int deleteByPrimaryKey(@Param("asid") Integer asid);

    @Override
    AuctionSessionDO getByPrimaryKey(@Param("asid") Integer asid);

    @Override
    int count(AuctionSessionQuery auctionSessionQuery);

    @Override
    List<AuctionSessionDO> select(AuctionSessionQuery auctionSessionQuery);
}
