package cn.idongjia.divine.db.mybatis.mapper.zoo.base;

import cn.idongjia.common.mybatis.base.BasePrimaryMapper;
import cn.idongjia.common.mybatis.base.BaseQueryMapper;
import cn.idongjia.common.mybatis.base.BaseSaveMapper;
import cn.idongjia.divine.db.mybatis.pojo.ZooDO;
import cn.idongjia.divine.db.mybatis.query.ZooQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：聊天室表 db接口
 *
 * @author lc
 * @date 2018/07/31
 */
public interface ZooBaseMapper extends
        BasePrimaryMapper<Long, ZooDO>,
        BaseQueryMapper<ZooQuery, ZooDO>,
        BaseSaveMapper<ZooDO> {

    @Override
    int insert(ZooDO zooDO);

    @Override
    int update(ZooDO zooDO);

    @Override
    int deleteByPrimaryKey(@Param("zooId") Long zid);

    @Override
    ZooDO getByPrimaryKey(@Param("zooId") Long zid);

    @Override
    int count(ZooQuery zooQuery);

    @Override
    List<ZooDO> select(ZooQuery zooQuery);
}
