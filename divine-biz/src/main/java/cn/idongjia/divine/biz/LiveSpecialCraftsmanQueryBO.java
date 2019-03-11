package cn.idongjia.divine.biz;

import cn.idongjia.divine.convertor.ConvertorI;
import cn.idongjia.divine.db.es.entity.LiveSpecialCraftsmanEntity;
import cn.idongjia.divine.lib.pojo.request.live.CraftsmanQry;
import cn.idongjia.divine.lib.pojo.request.sort.BizType;
import cn.idongjia.divine.lib.pojo.response.MultiResponse;
import cn.idongjia.divine.lib.pojo.response.live.special.LiveSpecialCraftsmanCO;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.se.lib.entities.EsSearchResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/9/3.
 */
@Component
public class LiveSpecialCraftsmanQueryBO extends BaseQueryBO<CraftsmanQry,LiveSpecialCraftsmanEntity> {
    @Resource
    private ConvertorI<LiveSpecialCraftsmanCO,LiveSpecialCraftsmanEntity> liveSpecialCraftsmanEntityConvertor;

    public MultiResponse<LiveSpecialCraftsmanCO> query(CraftsmanQry craftsmanQry) {
        EsSearchResult                   esSearchResult               = query(BizType.ModuleType.CRAFTSMAN,craftsmanQry,LiveSpecialCraftsmanEntity.class);
        List<LiveSpecialCraftsmanEntity> liveSpecialCraftsmanEntities = esSearchResult.getData();
        List<LiveSpecialCraftsmanCO>     liveSpecialCraftsmanCOS      = new ArrayList<>();
        if(!Utils.isEmpty(liveSpecialCraftsmanEntities)) {
            liveSpecialCraftsmanCOS = liveSpecialCraftsmanEntities.stream().map(liveSpecialCraftsmanEntity -> {
                return liveSpecialCraftsmanEntityConvertor.dataToClient(liveSpecialCraftsmanEntity);
            }).collect(Collectors.toList());
        }
        return MultiResponse.of(liveSpecialCraftsmanCOS,esSearchResult.getTotal().intValue());
    }
}
