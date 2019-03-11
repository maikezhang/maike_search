package cn.idongjia.divine.db.mybatis.mapper.zoo.base;

import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import cn.idongjia.divine.db.mybatis.pojo.ZooMessageDO;
import cn.idongjia.divine.db.mybatis.query.ZooMessageQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhangmaike on 2018/11/22.
 */
public interface ZooMessageBaseMapper extends
        BasePrimaryMapper<Long, ZooMessageDO>,
        BaseQueryMapper<ZooMessageQuery, ZooMessageDO>,
        BaseSaveMapper<ZooMessageDO> {


    @Override
    int insert(ZooMessageDO zooDO);

    @Override
    int update(ZooMessageDO zooDO);

    @Override
    int deleteByPrimaryKey(@Param("zmid") Long zmid);

    @Override
    ZooMessageDO getByPrimaryKey(@Param("zmid") Long zmid);

    @Override
    int count(ZooMessageQuery zooMessageQuery);

    @Override
    List<ZooMessageDO> select(ZooMessageQuery zooMessageQuery);



}
