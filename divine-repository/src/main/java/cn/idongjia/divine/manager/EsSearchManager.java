package cn.idongjia.divine.manager;

import cn.idongjia.divine.annotation.Elapsed;
import cn.idongjia.se.lib.dto.AggDTO;
import cn.idongjia.se.lib.engine.query.QueryArgs;
import cn.idongjia.se.lib.entities.EsEntity;
import cn.idongjia.se.lib.entities.EsSearchResult;
import cn.idongjia.se.lib.service.BaseService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lc
 * @create at 2018/8/3.
 */
@Component
public class EsSearchManager {
    @Resource
    private BaseService esService;


    @Elapsed
    public <T extends EsEntity, U extends AggDTO> EsSearchResult<T, U> search(QueryArgs<T, U> queryArgs) {
        return esService.search(queryArgs);
    }
}
