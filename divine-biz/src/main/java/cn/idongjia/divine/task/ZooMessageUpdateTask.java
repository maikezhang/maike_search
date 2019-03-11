package cn.idongjia.divine.task;

import cn.idongjia.common.query.BaseSearch;
import cn.idongjia.divine.assembler.ZooMessageAssambler;
import cn.idongjia.divine.db.es.entity.ZooMessageEntity;
import cn.idongjia.divine.db.mybatis.mapper.zoo.base.ZooMessageBaseMapper;
import cn.idongjia.divine.db.mybatis.pojo.ZooMessageDO;
import cn.idongjia.divine.db.mybatis.query.ZooMessageQuery;
import cn.idongjia.divine.manager.ESUpsertManager;
import cn.idongjia.divine.utils.SpringBeanLoader;
import cn.idongjia.util.Utils;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/11/23
 * Time: 下午5:16
 */
public class ZooMessageUpdateTask implements Callable<Integer> {

    private static final ZooMessageBaseMapper zooMessageBaseMapper = SpringBeanLoader.getBean("zooMessageBaseMapper", ZooMessageBaseMapper.class);
    private              ESUpsertManager      esUpsertManager      = SpringBeanLoader.getBean("esUpsertManager", ESUpsertManager.class);
    private              ZooMessageAssambler  zooMessageAssambler  = SpringBeanLoader.getBean("zooMessageAssambler", ZooMessageAssambler.class);
    private Long updateTime;

    private BaseSearch baseSearch;
    private Long       minZmid;

    public ZooMessageUpdateTask(BaseSearch baseSearch, Long updateTime, Long minZmid) {
        this.baseSearch = baseSearch;
        this.updateTime = updateTime;
        this.minZmid = minZmid;
    }


    @Override
    public Integer call() {
        ZooMessageQuery    zooMessageQuery = ZooMessageQuery.builder().limit(baseSearch.getLimit()).zmidRange(minZmid).build();
        List<ZooMessageDO> zooMessageDOS   = zooMessageBaseMapper.select(zooMessageQuery);

        List<ZooMessageEntity> zooMessageEntityList = zooMessageAssambler.assemble(zooMessageDOS);

        if (!Utils.isEmpty(zooMessageEntityList)) {
            esUpsertManager.bulkZooMessageUpsert(zooMessageEntityList);

        }
        return zooMessageEntityList.size();

    }


}
