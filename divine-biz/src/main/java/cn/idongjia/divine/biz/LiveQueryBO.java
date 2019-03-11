package cn.idongjia.divine.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import cn.idongjia.common.base.Base;
import org.springframework.stereotype.Component;

import cn.idongjia.divine.convertor.ConvertorI;
import cn.idongjia.divine.db.es.entity.LiveEntity;
import cn.idongjia.divine.lib.pojo.request.live.LiveQry;
import cn.idongjia.divine.lib.pojo.request.sort.BizType;
import cn.idongjia.divine.lib.pojo.request.sort.ESSort;
import cn.idongjia.divine.lib.pojo.response.MultiResponse;
import cn.idongjia.divine.lib.pojo.response.live.general.GeneralLiveCO;
import cn.idongjia.divine.manager.EsSearchManager;
import cn.idongjia.divine.query.factory.AbstractQueryFactory;
import cn.idongjia.divine.query.factory.QueryFactory;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.se.lib.engine.query.LeafQuery;
import cn.idongjia.se.lib.engine.query.QueryArgs;
import cn.idongjia.se.lib.entities.EsSearchResult;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lc
 * @create at 2018/8/7.
 */
@Component
@Slf4j
public class LiveQueryBO extends BaseQueryBO<LiveQry,LiveEntity> {

    @Resource
    private ConvertorI<GeneralLiveCO,LiveEntity> generalLiveConvertor;

    public MultiResponse<GeneralLiveCO> query(LiveQry liveQuery) {
        EsSearchResult       esSearchResult                = query(BizType.ModuleType.LIVE,liveQuery,LiveEntity.class);
        List<LiveEntity>    liveEntities = esSearchResult.getData();
        List<GeneralLiveCO> generalLiveCOS      = new ArrayList<>();
        if(!Utils.isEmpty(liveEntities)) {
            generalLiveCOS = liveEntities.stream().map(liveEntity -> {
                return generalLiveConvertor.dataToClient(liveEntity);
            }).collect(Collectors.toList());
        }
        return MultiResponse.of(generalLiveCOS,esSearchResult.getTotal().intValue());
    }
}
