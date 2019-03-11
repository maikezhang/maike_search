package cn.idongjia.divine.db.mybatis.mapper.kaipao;


import cn.idongjia.divine.db.mybatis.mapper.kaipao.base.LivePlaybackBaseMapper;
import cn.idongjia.divine.db.mybatis.pojo.CountPO;
import cn.idongjia.divine.db.mybatis.query.LivePlaybackQuery;

import java.util.List;

/**
 * 描述：回放表 的自定义接口，所有自定义接口均定义在此
 *
 * @author lc
 * @date 2018/07/31
 */
public interface LivePlaybackMapper extends LivePlaybackBaseMapper {
    List<CountPO> countValid(LivePlaybackQuery livePlaybackQuery);

}
