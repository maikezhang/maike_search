package cn.idongjia.divine.db.mybatis.mapper.kaipao.base;

import cn.idongjia.divine.db.mybatis.pojo.AuctionBookDO;
import cn.idongjia.divine.db.mybatis.query.AuctionBookQuery;
import org.apache.ibatis.annotations.Param;
import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import java.util.List;

/**
 * 描述：拍卖订阅表 db接口
 *
 * @author lc
 * @date 2018/08/10
 */
public interface AuctionBookBaseMapper extends
        BasePrimaryMapper<Long, AuctionBookDO>,
        BaseQueryMapper<AuctionBookQuery,AuctionBookDO>,
        BaseSaveMapper<AuctionBookDO> {

    @Override
    int insert(AuctionBookDO auctionBookDO);

    @Override
    int update(AuctionBookDO auctionBookDO);

    @Override
    int deleteByPrimaryKey(@Param("id") Long id);

    @Override
    AuctionBookDO getByPrimaryKey(@Param("id") Long id);

    @Override
    int count(AuctionBookQuery auctionBookQuery);

    @Override
    List<AuctionBookDO> select(AuctionBookQuery auctionBookQuery);
}
