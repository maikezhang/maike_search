package cn.idongjia.divine.db.mybatis.mapper.kaipao;


import cn.idongjia.divine.db.mybatis.mapper.kaipao.base.AuctionBookBaseMapper;
import cn.idongjia.divine.db.mybatis.pojo.CountPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：拍卖订阅表 的自定义接口，所有自定义接口均定义在此
 *
 * @author lc
 * @date 2018/08/10
 */
public interface AuctionBookMapper extends AuctionBookBaseMapper {

    List<CountPO> groupBySessionIds(@Param("sessionIds") List<Long> sessionIds,@Param("contentType") Integer contentType);
}
