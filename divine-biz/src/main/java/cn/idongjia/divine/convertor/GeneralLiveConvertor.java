package cn.idongjia.divine.convertor;

import cn.idongjia.divine.db.es.entity.ItemEntity;
import cn.idongjia.divine.db.es.entity.LiveEntity;
import cn.idongjia.divine.lib.pojo.response.live.general.GeneralLiveCO;
import cn.idongjia.divine.lib.pojo.response.live.general.ItemCO;
import cn.idongjia.divine.utils.Utils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/7/26.
 */
@Component
public class GeneralLiveConvertor implements ConvertorI<GeneralLiveCO,LiveEntity> {

    @Override
    public GeneralLiveCO dataToClient(LiveEntity dataObject) {
        GeneralLiveCO generalLiveCO = new GeneralLiveCO();
        generalLiveCO.setZid(dataObject.getZid());
        generalLiveCO.setVideoCoverId(dataObject.getVideoId());
        generalLiveCO.setUpdateTime(dataObject.getUpdateTime());
        generalLiveCO.setUid(dataObject.getUserId());
        generalLiveCO.setTitle(dataObject.getTitle());
        generalLiveCO.setStartTime(dataObject.getStartTime());
        generalLiveCO.setRecommendWeight(dataObject.getRecommendWeight());
        generalLiveCO.setPreStartTime(dataObject.getPreStartTime());
        generalLiveCO.setPreEndTime(dataObject.getPreEndTime());
        generalLiveCO.setPic(dataObject.getPic());
        generalLiveCO.setName(dataObject.getCraftsmanName());
        List<ItemEntity> itemEntities = dataObject.getItems();
        if(Utils.isNotEmpty(itemEntities)) {
            List<ItemCO> itemCOS = itemEntities.stream().map(itemEntity -> {
                ItemCO itemCO = new ItemCO();
                itemCO.setItemId(Long.valueOf(itemEntity.getItemId()));
                itemCO.setPicture(itemEntity.getPicture());
                itemCO.setPrice(itemEntity.getPrice());
                itemCO.setTitle(itemEntity.getTitle());
                return itemCO;
            }).collect(Collectors.toList());
            generalLiveCO.setItems(itemCOS);
            generalLiveCO.setResourceCount(itemCOS.size());
        }
        generalLiveCO.setRoomId(dataObject.getRoomId());
        generalLiveCO.setId(Long.parseLong(dataObject.getId()));
        generalLiveCO.setEndTime(dataObject.getEndTime());
        generalLiveCO.setCreateTime(dataObject.getCreateTime());
        generalLiveCO.setAvatar(dataObject.getAvatar());
        generalLiveCO.setGeneralWeight(dataObject.getGeneralWeight());
        generalLiveCO.setOnline(dataObject.getOnline());
        generalLiveCO.setSessionId(dataObject.getSessionId());
        generalLiveCO.setCraftsmanCity(dataObject.getCity());
        generalLiveCO.setCraftsmanName(dataObject.getCraftsmanName());
        generalLiveCO.setCraftsmanTitle(dataObject.getCrftsmanTitle());
        generalLiveCO.setState(dataObject.getState());
        generalLiveCO.setStatus(dataObject.getStatus());
        generalLiveCO.setType(dataObject.getLiveType());
        generalLiveCO.setUv(dataObject.getUv());
        generalLiveCO.setVideoCoverDuration(dataObject.getDuration() == null ? null : dataObject.getDuration().intValue());
        generalLiveCO.setVideoCoverPic(dataObject.getVideoPic());
        generalLiveCO.setVideoCoverUrl(dataObject.getVideoUrl());
        generalLiveCO.setZrc(dataObject.getZrc());
        generalLiveCO.setScreenDirection(dataObject.getScreenDirection());
        generalLiveCO.setPreviewTime(dataObject.getPreViewTm());
        generalLiveCO.setSuid(dataObject.getSuid());
        return generalLiveCO;
    }
}
