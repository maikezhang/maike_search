package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.pojo.ZooMessageDO;
import cn.idongjia.divine.db.mybatis.query.ZooMessageQuery;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyingjie on 2018/11/23.
 */
public interface ZooMessageRepositoryI extends RepositoryI {



    List<ZooMessageDO> select(ZooMessageQuery zooMessageQuery);

    ZooMessageDO getByPrimaryKey(Long zmid);

    int count(ZooMessageQuery liveShowQuery);

    long maxZmid();


    Map<Long,Integer> assembleZooMessageCount(List<Long> zooIds,List<Integer> types);

}
