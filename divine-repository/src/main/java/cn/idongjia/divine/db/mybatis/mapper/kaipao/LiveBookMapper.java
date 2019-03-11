package cn.idongjia.divine.db.mybatis.mapper.kaipao;


import cn.idongjia.divine.db.mybatis.mapper.kaipao.base.LiveBookBaseMapper;
import cn.idongjia.divine.db.mybatis.pojo.CountPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述： 的自定义接口，所有自定义接口均定义在此
 *
 * @author lc
 * @date 2018/08/10
 */
public interface LiveBookMapper extends LiveBookBaseMapper {

    List<CountPO> groupByLiveIds(@Param("liveIds") List<Long> liveIds);
}
