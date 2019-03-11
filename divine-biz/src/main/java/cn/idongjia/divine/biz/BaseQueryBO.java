package cn.idongjia.divine.biz;

import cn.idongjia.divine.lib.pojo.request.Page;
import cn.idongjia.divine.lib.pojo.request.sort.BizType;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.manager.EsSearchManager;
import cn.idongjia.divine.query.factory.AbstractQueryFactory;
import cn.idongjia.divine.query.factory.QueryFactory;
import cn.idongjia.se.lib.engine.annotations.EsDocument;
import cn.idongjia.se.lib.engine.query.LeafQuery;
import cn.idongjia.se.lib.engine.query.QueryArgs;
import cn.idongjia.se.lib.entities.BasicEntity;
import cn.idongjia.se.lib.entities.EsSearchResult;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/9/6.
 */
public class BaseQueryBO<Q extends Page,E extends BasicEntity> {

    @Resource
    private EsSearchManager esSearchManager;

    public EsSearchResult query(BizType.ModuleType moduleType,Q q,Class<? extends E> clazz) {
        AbstractQueryFactory abstractQueryFactory = QueryFactory.getFactory(moduleType,q);
        LeafQuery            leafQuery            = abstractQueryFactory.getQuery();
        QueryArgs            queryArgs            = new QueryArgs();
        EsDocument           clazzAnnotation      = clazz.getAnnotation(EsDocument.class);
        queryArgs.setType(clazzAnnotation.type());
        queryArgs.setIndex(clazzAnnotation.indexName());
        queryArgs.setClazz(clazz);
        ESSort esSort = abstractQueryFactory.getSort();
        leafQuery.setSortList(esSort.getSorts());
        queryArgs.setQuery(leafQuery);
        return esSearchManager.search(queryArgs);
    }
}
