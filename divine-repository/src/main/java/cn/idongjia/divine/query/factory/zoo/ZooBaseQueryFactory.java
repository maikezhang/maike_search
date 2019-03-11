package cn.idongjia.divine.query.factory.zoo;

import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.lib.pojo.request.zoo.ZooMessageQry;
import cn.idongjia.divine.manager.ScriptSortManager;
import cn.idongjia.divine.manager.SegManager;
import cn.idongjia.divine.query.ZooMessageESQuery;
import cn.idongjia.divine.query.factory.AbstractQueryFactory;
import cn.idongjia.divine.utils.SpringBeanLoader;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/11/23
 * Time: 下午2:41
 */
public abstract class ZooBaseQueryFactory extends AbstractQueryFactory<ZooMessageESQuery> {
    protected ScriptSortManager scriptSortManager = SpringBeanLoader.getBean("scriptSortManager",ScriptSortManager.class);
    protected  SegManager        segManager        = SpringBeanLoader.getBean("segManager",SegManager.class);


    protected ZooMessageQry zooMessageQry;

    public ZooBaseQueryFactory(ZooMessageQry zooMessageQry){
        this.zooMessageQry=zooMessageQry;
    }

    @Override
    public abstract ZooMessageESQuery getQuery();

    @Override
    public abstract ESSort getSort();
}
