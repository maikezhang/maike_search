package cn.idongjia.divine.manager;

import cn.idongjia.outcry.service.DjtService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lc
 * @create at 2018/9/3.
 */
@Component
@Slf4j
public class AuctionManager {
    @Resource
    private DjtService djtService;

    public List<Long> listDJT(List<Long> userIds) {
        List<Long> djtCraftsmanIds = djtService.listAllCraftsmanUserIdInDjt(userIds);
        log.debug("userIds={} djtCraftsmanIds==>{}",userIds,djtCraftsmanIds);
        return djtCraftsmanIds;
    }
}
