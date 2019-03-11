package cn.idongjia.divine.db.mybatis.mapper.kaipao.base;

import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import cn.idongjia.divine.db.mybatis.pojo.AuctionExtDO;
import cn.idongjia.divine.db.mybatis.query.AuctionExtQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述： db接口
 *
 * @author ,yuexiaodong@idongjia.cn
 * @date 2018/12/18
 */
public interface AuctionExtBaseMapper extends
        BasePrimaryMapper<Long, AuctionExtDO>,
        BaseQueryMapper<AuctionExtQuery, AuctionExtDO>,
        BaseSaveMapper<AuctionExtDO> {

    @Override
    int insert(AuctionExtDO auctionExtDO);

    @Override
    int update(AuctionExtDO auctionExtDO);

    @Override
    int deleteByPrimaryKey(@Param("id") Long id);

    @Override
    AuctionExtDO getByPrimaryKey(@Param("id") Long id);

    @Override
    int count(AuctionExtQuery auctionExtQuery);

    @Override
    List<AuctionExtDO> select(AuctionExtQuery auctionExtQuery);
}