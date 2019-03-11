package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.mapper.zoo.ZooMessageMapper;
import cn.idongjia.divine.db.mybatis.pojo.ZooMessageCountDO;
import cn.idongjia.divine.db.mybatis.pojo.ZooMessageDO;
import cn.idongjia.divine.db.mybatis.query.ZooMessageQuery;
import cn.idongjia.divine.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author zhangyingjie
 * @create at 2018/11/23.
 */
@Repository
@Slf4j
public class ZooMessageRepository implements ZooMessageRepositoryI {
    @Resource
    private ZooMessageMapper zooMessageMapper;


    @Override
    public List<ZooMessageDO> select(ZooMessageQuery zooMessageQuery) {
        return zooMessageMapper.select(zooMessageQuery);
    }

    @Override
    public ZooMessageDO getByPrimaryKey(Long liveId) {
        return zooMessageMapper.getByPrimaryKey(liveId);
    }

    @Override
    public int count(ZooMessageQuery zooMessageQuery) {
        return zooMessageMapper.count(zooMessageQuery);
    }

    @Override
    public long maxZmid() {
        return zooMessageMapper.maxZmid();
    }

    @Override
    public Map<Long, Integer> assembleZooMessageCount(List<Long> zooIds, List<Integer> types) {
        List<ZooMessageCountDO> zooMessageCounts = new ArrayList<>();
        if (Utils.isEmpty(zooIds)) {
            return new HashMap<>(1);
        }
        if (zooIds.size() <= 500) {
            zooMessageCounts = zooMessageMapper.getZooMessageCount(zooIds, types);

        } else {
            int hightIndex = 500;
            int midIndex   = 0;
            List<ZooMessageCountDO> zooMessageCount;
            do {
                if(hightIndex>zooIds.size()){
                    hightIndex=zooIds.size();
                }
                List<Long> zids = zooIds.subList(midIndex, hightIndex);
                zooMessageCount = zooMessageMapper.getZooMessageCount(zids, types);
                midIndex=midIndex+500;
                hightIndex+=500;
                if(!Utils.isEmpty(zooMessageCount)){
                    zooMessageCounts.addAll(zooMessageCount);
                }
            } while (midIndex<zooIds.size());
        }


        if (Utils.isEmpty(zooMessageCounts)) {
            return new HashMap<>(1);
        }
        Map<Long, Integer> result = zooMessageCounts.stream().collect(Collectors.toMap(v1 -> v1.getZooId(), v2 -> v2.getCount(), (v1, v2) -> v1));

        return result;
    }


}
