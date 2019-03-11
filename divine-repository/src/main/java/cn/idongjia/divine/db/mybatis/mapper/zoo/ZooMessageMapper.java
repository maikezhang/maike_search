package cn.idongjia.divine.db.mybatis.mapper.zoo;

import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import cn.idongjia.divine.db.mybatis.mapper.zoo.base.ZooMessageBaseMapper;
import cn.idongjia.divine.db.mybatis.pojo.ZooMessageCountDO;
import cn.idongjia.divine.db.mybatis.pojo.ZooMessageDO;
import cn.idongjia.divine.db.mybatis.query.ZooMessageQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhangmaike on 2018/11/22.
 */
public interface ZooMessageMapper extends ZooMessageBaseMapper{


    long maxZmid();

    List<ZooMessageCountDO> getZooMessageCount(@Param("zids") List<Long> zooIds,@Param("types") List<Integer> types);





}
