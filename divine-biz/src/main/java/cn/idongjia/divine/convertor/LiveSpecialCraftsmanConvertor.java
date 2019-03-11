package cn.idongjia.divine.convertor;

import cn.idongjia.divine.db.es.entity.LiveSpecialCraftsmanEntity;
import cn.idongjia.divine.lib.pojo.response.live.special.LiveSpecialCraftsmanCO;
import org.springframework.stereotype.Component;

/**
 * @author lc
 * @create at 2018/9/3.
 */
@Component
public class LiveSpecialCraftsmanConvertor implements ConvertorI<LiveSpecialCraftsmanCO,LiveSpecialCraftsmanEntity> {
    @Override
    public LiveSpecialCraftsmanCO dataToClient(LiveSpecialCraftsmanEntity dataObject) {
        LiveSpecialCraftsmanCO liveSpecialCraftsmanCO=new LiveSpecialCraftsmanCO();
        liveSpecialCraftsmanCO.setCraftsmanAvatar(dataObject.getCraftsmanAvatar());
        liveSpecialCraftsmanCO.setCraftsmanName(dataObject.getCraftsmanName());
        liveSpecialCraftsmanCO.setCraftsmanUserId(dataObject.getCraftsmanUserId());
        liveSpecialCraftsmanCO.setShowType(dataObject.getShowType());
        liveSpecialCraftsmanCO.setCreateTime(dataObject.getCreateTime());
        return liveSpecialCraftsmanCO;
    }
}
