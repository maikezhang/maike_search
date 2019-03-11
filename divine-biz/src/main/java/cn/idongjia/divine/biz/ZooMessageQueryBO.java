package cn.idongjia.divine.biz;

import cn.idongjia.divine.convertor.ConvertorI;
import cn.idongjia.divine.db.es.entity.ZooMessageEntity;
import cn.idongjia.divine.lib.pojo.request.sort.BizType;
import cn.idongjia.divine.lib.pojo.request.zoo.ZooMessageQry;
import cn.idongjia.divine.lib.pojo.response.MultiResponse;
import cn.idongjia.divine.lib.pojo.response.zoo.ZooMessageCO;
import cn.idongjia.se.lib.entities.EsSearchResult;
import cn.idongjia.util.Utils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/11/23
 * Time: 下午2:20
 */
@Component
public class ZooMessageQueryBO extends BaseQueryBO<ZooMessageQry, ZooMessageEntity> {


    @Resource
    private ConvertorI<ZooMessageCO, ZooMessageEntity> coZooMessageEntityConvertorI;


    public MultiResponse<ZooMessageCO> query(ZooMessageQry zooMessageQry) {
        EsSearchResult         esSearchResult       = query(BizType.ModuleType.ZOO_MESSAGE, zooMessageQry, ZooMessageEntity.class);
        List<ZooMessageEntity> zooMessageEntityList = esSearchResult.getData();
        List<ZooMessageCO>     zooMessageCOS        = new ArrayList<>();
        if(!Utils.isEmpty(zooMessageEntityList)) {
            zooMessageCOS = zooMessageEntityList.stream().map(zooMessage -> {

                return coZooMessageEntityConvertorI.dataToClient(zooMessage);

            }).collect(Collectors.toList());
        }

        return MultiResponse.of(zooMessageCOS, esSearchResult.getTotal().intValue());
    }
}
