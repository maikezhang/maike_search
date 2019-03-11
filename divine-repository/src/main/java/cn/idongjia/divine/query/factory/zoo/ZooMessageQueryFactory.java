package cn.idongjia.divine.query.factory.zoo;

import cn.idongjia.divine.lib.pojo.request.sort.AuctionSort;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.lib.pojo.request.sort.ZooMessageSort;
import cn.idongjia.divine.lib.pojo.request.zoo.ZooMessageQry;
import cn.idongjia.divine.query.ZooMessageESQuery;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.se.lib.engine.query.Direction;
import cn.idongjia.se.lib.engine.query.logical.Range;
import cn.idongjia.se.lib.engine.query.sort.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/11/23
 * Time: 下午2:44
 */
public class ZooMessageQueryFactory extends ZooBaseQueryFactory{
    public ZooMessageQueryFactory(ZooMessageQry zooMessageQry) {
        super(zooMessageQry);
    }

    @Override
    public ZooMessageESQuery getQuery(){
        ZooMessageESQuery zooMessageESQuery=new ZooMessageESQuery();

        zooMessageESQuery.setUserIds(zooMessageQry.getUserIds());
        zooMessageESQuery.setUserId(zooMessageQry.getUserId());
        zooMessageESQuery.setStatus(zooMessageQry.getStatus());
        zooMessageESQuery.setTypes(zooMessageQry.getTypes());
        zooMessageESQuery.setWildContent(zooMessageQry.getWildContent());
        zooMessageESQuery.setZooId(zooMessageQry.getZooId());
        zooMessageESQuery.setZooIds(zooMessageQry.getZooIds());
        zooMessageESQuery.setCreateTime(Range.build(zooMessageQry.getMinCreateTime(),zooMessageQry.getMaxCreateTime()));
        zooMessageESQuery.setZooMessageId(zooMessageQry.getZooMessageId());
        zooMessageESQuery.setZooMessageIds(zooMessageQry.getZooMessageIds());
        zooMessageESQuery.setStart(zooMessageQry.getOffset());
        zooMessageESQuery.setNum(zooMessageQry.getLimit());
        return zooMessageESQuery;


    }

    /**
     * 获取排序条件
     *
     * @return
     */
    @Override
    public ESSort getSort() {
        ESSort     esSort = new ZooMessageSort();
        List<Sort> sorts  = zooMessageQry.getSorts();


        if(Utils.isNotEmpty(sorts)) {
            esSort.addSorts(sorts);
        }else{
            List<Sort> sorts1=new ArrayList<>();
            Sort sort=new Sort();
            sort.setField("zooMessageId");
            sort.setDirection(Direction.DESC);
            sorts1.add(sort);
            esSort.addSorts(sorts1);
        }
        return esSort;
    }
}
