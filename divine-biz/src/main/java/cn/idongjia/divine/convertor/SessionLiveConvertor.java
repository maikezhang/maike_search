package cn.idongjia.divine.convertor;

import cn.idongjia.divine.db.es.entity.SessionLiveEntity;
import cn.idongjia.divine.lib.pojo.response.session.SessionLiveCO;
import org.springframework.stereotype.Component;

/**
 * @author yuexiaodong@idongjia.cn
 * @date 2018-11-30
 */
@Component
public class SessionLiveConvertor implements ConvertorI<SessionLiveCO, SessionLiveEntity> {

    @Override
    public SessionLiveCO dataToClient(SessionLiveEntity dataObject) {
        SessionLiveCO co = new SessionLiveCO();
        co.setLiveId(dataObject.getLiveId());
        co.setSessionId(dataObject.getSessionId());
        co.setLiveState(dataObject.getLiveState());
        co.setLivePreStarTime(dataObject.getLivePreStarTime());
        co.setPlanEndTime(dataObject.getPlanEndTime());
        co.setPlanStartTime(dataObject.getPlanStartTime());
        co.setSessionPreview(dataObject.getSessionPreview());
        co.setSessionState(dataObject.getSessionState());
        co.setSessionStatus(dataObject.getSessionStatus());
        co.setSessionWeight(dataObject.getSessionWeight());
        co.setLiveCraftsmanAvatar(dataObject.getLiveCraftsmanAvatar());
        co.setLiveCraftsmanName(dataObject.getLiveCraftsmanName());
        co.setLiveCraftsmanTitle(dataObject.getLiveCraftsmanTitle());
        co.setSessionTitle(dataObject.getSessionTitle());
        co.setSessionPic(dataObject.getSessionPic());
        co.setLivePreViewTime(dataObject.getLivePreViewTime());
        co.setLiveCraftsmanId(dataObject.getLiveCraftsmanId());
        return co;
    }
}
