package cn.idongjia.divine.db.mybatis.mapper.kaipao;


import cn.idongjia.divine.db.mybatis.mapper.kaipao.base.LiveUserStageRelBaseMapper;
import cn.idongjia.divine.db.mybatis.pojo.LiveUserStageRelDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述： 的自定义接口，所有自定义接口均定义在此
 *
 * @author lc
 * @date 2018/08/15
 */
public interface LiveUserStageRelMapper extends LiveUserStageRelBaseMapper {

    List<LiveUserStageRelDO> listByLiveIds(@Param("liveIds") List<Long> liveIds);
}
