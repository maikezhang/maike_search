package cn.idongjia.divine.convertor;

import cn.idongjia.divine.db.es.entity.AuctionStateEntity;
import cn.idongjia.divine.db.es.entity.SessionEntity;
import cn.idongjia.divine.lib.pojo.response.session.AuctionStateCO;
import cn.idongjia.divine.lib.pojo.response.session.SessionCO;
import org.springframework.stereotype.Component;

/**
 * @author lc
 * @create at 2018/9/3.
 */
@Component
public class AuctionStateConvertor implements ConvertorI<AuctionStateCO,AuctionStateEntity> {
    @Override
    public AuctionStateCO dataToClient(AuctionStateEntity dataObject) {
        AuctionStateCO auctionStateCO = new AuctionStateCO();
        auctionStateCO.setCraftsmanId(dataObject.getCraftsmanId());
        auctionStateCO.setEnded(dataObject.getEndTotal());
        auctionStateCO.setStarted(dataObject.getStartedTotal());
        auctionStateCO.setUnstart(dataObject.getUnstartTotal());
        auctionStateCO.setCraftsmanAvatar(dataObject.getCraftsmanAvatar());
        auctionStateCO.setCraftsmanName(dataObject.getCraftsmanName());
        auctionStateCO.setCraftsmanTitle(dataObject.getCraftsmanTitle());
        return auctionStateCO;
    }
}
