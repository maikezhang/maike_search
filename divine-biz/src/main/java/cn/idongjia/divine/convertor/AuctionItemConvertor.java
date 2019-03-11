package cn.idongjia.divine.convertor;

import cn.idongjia.divine.db.es.entity.AuctionEntity;
import cn.idongjia.divine.lib.pojo.response.auction.AuctionCO;
import org.springframework.stereotype.Component;

/**
 * @author lc
 * @create at 2018/7/26.
 */
@Component
public class AuctionItemConvertor implements ConvertorI<AuctionCO, AuctionEntity> {

    @Override
    public AuctionCO dataToClient(AuctionEntity dataObject) {
        AuctionCO auctionCO = new AuctionCO();
        auctionCO.setBookTotal(dataObject.getBookTotal());
        auctionCO.setCover(dataObject.getCover());
        auctionCO.setCraftsmaneAvatar(dataObject.getCraftsmaneAvatar());
        auctionCO.setCraftsmanId(dataObject.getCraftsmanId());
        auctionCO.setCraftsmanName(dataObject.getCraftsmanName());
        auctionCO.setCurrentPrice(dataObject.getCurrentPrice());
        auctionCO.setEndTime(dataObject.getEndTime());
        auctionCO.setExtStatus(dataObject.getExtStatus());
        auctionCO.setGroundId(dataObject.getGroundId());
        auctionCO.setItemCategoryId(dataObject.getItemCategoryId());
        auctionCO.setItemId(dataObject.getItemId());
        auctionCO.setItemTitle(dataObject.getItemTitle());
        auctionCO.setOfferUserTotal(dataObject.getOfferTotal());
        auctionCO.setOfferUserId(dataObject.getOfferUserId());
        auctionCO.setPrice(dataObject.getPrice());
        auctionCO.setSessionId(dataObject.getSessionId());
        auctionCO.setSessionPreviewStatus(dataObject.getSessionPreviewStatus());
        auctionCO.setSessionState(dataObject.getSessionState());
        auctionCO.setSessionStatus(dataObject.getSessionStatus());
        auctionCO.setSessionType(dataObject.getSessionType());
        auctionCO.setStartTime(dataObject.getStartTime());
        auctionCO.setState(dataObject.getState());
        auctionCO.setStatus(dataObject.getStatus());
        auctionCO.setPlanEndTime(dataObject.getPlanEndTime());
        auctionCO.setAuctionType(dataObject.getAuctionType());
        auctionCO.setCellingPrice(dataObject.getCellingPrice());
        auctionCO.setOfferUserAvatar(dataObject.getOfferUserAvatar());
        auctionCO.setOfferUserName(dataObject.getOfferUserName());
        auctionCO.setCraftsmanTitle(dataObject.getCraftsmanTitle());
        auctionCO.setMinOfferInterval(dataObject.getMinOfferInterval());
        auctionCO.setMaxOfferInterval(dataObject.getMaxOfferInterval());
        auctionCO.setMuid(dataObject.getMuid());
        auctionCO.setLadderId(dataObject.getLadderId());
        auctionCO.setZooId(dataObject.getZooId());
        auctionCO.setNextItemId(dataObject.getNextItemId());
        auctionCO.setCreateTime(dataObject.getCreateTime());
        auctionCO.setHot(dataObject.getHot());
        auctionCO.setUpdateTime(dataObject.getUpdateTime());
        return auctionCO;
    }
}
