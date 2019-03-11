package cn.idongjia.divine.db.mybatis.mapper.kaipao.base;

import cn.idongjia.divine.db.mybatis.pojo.AuctionManualRecommendDO;
import cn.idongjia.divine.db.mybatis.query.AuctionManualRecommendQuery;
import org.apache.ibatis.annotations.Param;
import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import java.util.List;

/**
 * 描述：拍卖人工推荐数据 db接口
 *
 * @author lc
 * @date 2018/08/16
 */
public interface AuctionManualRecommendBaseMapper extends
        BasePrimaryMapper<Long,AuctionManualRecommendDO>,
        BaseQueryMapper<AuctionManualRecommendQuery, AuctionManualRecommendDO>,
        BaseSaveMapper<AuctionManualRecommendDO> {

    @Override
    int insert(AuctionManualRecommendDO auctionManualRecommendDO);

    @Override
    int update(AuctionManualRecommendDO auctionManualRecommendDO);

    @Override
    int deleteByPrimaryKey(@Param("id") Long id);

    @Override
    AuctionManualRecommendDO getByPrimaryKey(@Param("id") Long id);

    @Override
    int count(AuctionManualRecommendQuery auctionManualRecommendQuery);

    @Override
    List<AuctionManualRecommendDO> select(AuctionManualRecommendQuery auctionManualRecommendQuery);
}
