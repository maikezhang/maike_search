package cn.idongjia.divine.convertor;

import cn.idongjia.divine.db.es.entity.SessionEntity;
import cn.idongjia.divine.lib.pojo.response.session.SessionCO;
import org.springframework.stereotype.Component;

/**
 * @author lc
 * @create at 2018/9/3.
 */
@Component
public class SessionConvertor implements ConvertorI<SessionCO,SessionEntity> {
    @Override
    public SessionCO dataToClient(SessionEntity dataObject) {
        SessionCO sessionCO = new SessionCO();
        sessionCO.setCraftsmanAvatar(dataObject.getCraftsmanAvatar());
        sessionCO.setCraftsmanName(dataObject.getCraftsmanName());
        sessionCO.setCraftsmanTitle(dataObject.getCraftsmanTitle());
        sessionCO.setCreatorId(dataObject.getCreatorId());
        sessionCO.setCreatorType(dataObject.getCreatorType());
        sessionCO.setDjtFlag(dataObject.getCreatorType());
        sessionCO.setForNewUser(dataObject.getForNewUser());
        sessionCO.setHotWeight(dataObject.getHotWeight());
        sessionCO.setLiveId(dataObject.getLiveId());
        sessionCO.setPic(dataObject.getPic());
        sessionCO.setPlanEndTime(dataObject.getPlanEndTime());
        sessionCO.setPlanStartTime(dataObject.getPlanStartTime());
        sessionCO.setPreview(dataObject.getPreview());
        sessionCO.setSessionId(dataObject.getSessionId());
        sessionCO.setSessionType(dataObject.getSessionType());
        sessionCO.setState(dataObject.getState());
        sessionCO.setStatus(dataObject.getStatus());
        sessionCO.setTitle(dataObject.getTitle());
        sessionCO.setOfferTotal(dataObject.getOfferTotal());
        sessionCO.setWeight(dataObject.getWeight());
        sessionCO.setAinterval(dataObject.getAinterval());
        sessionCO.setDeposit(dataObject.getDeposit());
        sessionCO.setRelatedCount(dataObject.getRelatedCount());
        sessionCO.setCreateTime(dataObject.getCreateTime());
        return sessionCO;
    }
}
