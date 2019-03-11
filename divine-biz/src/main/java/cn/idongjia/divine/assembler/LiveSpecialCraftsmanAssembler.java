package cn.idongjia.divine.assembler;

import cn.idongjia.divine.db.es.entity.LiveSpecialCraftsmanEntity;
import cn.idongjia.divine.db.mybatis.pojo.CustomerDO;
import cn.idongjia.divine.db.mybatis.pojo.LiveAnchorBlackWhiteDO;
import cn.idongjia.divine.dto.LiveSpecialCraftsmanDTO;
import cn.idongjia.divine.repository.CustomerRepositoryI;
import cn.idongjia.divine.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/9/3.
 */
@Component
@Slf4j
public class LiveSpecialCraftsmanAssembler implements AssemblerI<LiveSpecialCraftsmanEntity,LiveAnchorBlackWhiteDO,LiveSpecialCraftsmanDTO> {
    @Resource
    private CustomerRepositoryI customerRepositoryI;

    @Override
    public List<LiveSpecialCraftsmanEntity> assemble(List<LiveAnchorBlackWhiteDO> liveAnchorBlackWhiteDOS) {
        if(Utils.isEmpty(liveAnchorBlackWhiteDOS)) {
            return new ArrayList<>();
        }
        List<Long>           userIds = liveAnchorBlackWhiteDOS.stream().map(LiveAnchorBlackWhiteDO::getAnchorId).collect(Collectors.toList());
        Map<Long,CustomerDO> customerDOMap;
        try {
            customerDOMap = customerRepositoryI.mapByCustomerId(userIds).get();
            return liveAnchorBlackWhiteDOS.stream().map(liveAnchorBlackWhiteDO -> {
                CustomerDO customerDO = customerDOMap.get(liveAnchorBlackWhiteDO.getAnchorId());
                if(customerDO == null) {
                    return null;
                }
                LiveSpecialCraftsmanDTO liveSpecialCraftsmanDTO = new LiveSpecialCraftsmanDTO();
                liveSpecialCraftsmanDTO.setCraftsmanAvatar(customerDO.getAvatar());
                liveSpecialCraftsmanDTO.setCraftsmanName(customerDO.getName());
                liveSpecialCraftsmanDTO.setCraftsmanUserId(customerDO.getMainUserId().longValue());
                liveSpecialCraftsmanDTO.setCreateTime(liveAnchorBlackWhiteDO.getCreateTime());
                liveSpecialCraftsmanDTO.setShowType(liveAnchorBlackWhiteDO.getType());
                return assemble(liveSpecialCraftsmanDTO);
            }).filter(dto -> dto != null).collect(Collectors.toList());
        } catch(Exception e) {
            log.info("组装数据失败{}",e);
        }
        return new ArrayList<>();
    }

    @Override
    public LiveSpecialCraftsmanEntity assemble(LiveSpecialCraftsmanDTO liveSpecialCraftsmanDTO) {
        LiveSpecialCraftsmanEntity liveSpecialCraftsmanEntity = new LiveSpecialCraftsmanEntity();
        liveSpecialCraftsmanEntity.setCraftsmanAvatar(liveSpecialCraftsmanDTO.getCraftsmanAvatar());
        liveSpecialCraftsmanEntity.setCraftsmanName(liveSpecialCraftsmanDTO.getCraftsmanName());
        liveSpecialCraftsmanEntity.setCraftsmanUserId(liveSpecialCraftsmanDTO.getCraftsmanUserId());
        liveSpecialCraftsmanEntity.setCreateTime(liveSpecialCraftsmanDTO.getCreateTime());
        liveSpecialCraftsmanEntity.setShowType(liveSpecialCraftsmanDTO.getShowType());
        liveSpecialCraftsmanEntity.setId(liveSpecialCraftsmanDTO.getCraftsmanUserId().toString());
        return liveSpecialCraftsmanEntity;
    }
}
