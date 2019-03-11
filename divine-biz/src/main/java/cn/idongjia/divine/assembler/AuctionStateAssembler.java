package cn.idongjia.divine.assembler;

import cn.idongjia.divine.db.es.entity.AuctionStateEntity;
import cn.idongjia.divine.db.mybatis.pojo.AuctionStateDO;
import cn.idongjia.divine.db.mybatis.pojo.CraftsmanDO;
import cn.idongjia.divine.db.mybatis.pojo.CustomerDO;
import cn.idongjia.divine.dto.AuctionStateDTO;
import cn.idongjia.divine.repository.CraftsmanRepositoryI;
import cn.idongjia.divine.repository.CustomerRepositoryI;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.log.Log;
import cn.idongjia.log.LogFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/7/30.
 */
@Repository
public class AuctionStateAssembler implements AssemblerI<AuctionStateEntity,AuctionStateDO,AuctionStateDTO> {
    private static final Log logger = LogFactory.getLog(AuctionStateAssembler.class);

    @Resource
    private CraftsmanRepositoryI craftsmanRepositoryI;

    @Resource
    private CustomerRepositoryI customerRepositoryI;

    @Override
    public List<AuctionStateEntity> assemble(List<AuctionStateDO> auctionSessionDOS) {
        List<Long> userIds = auctionSessionDOS.stream().map(AuctionStateDO::getCraftsmanId).collect(Collectors.toList());
        if(Utils.isEmpty(auctionSessionDOS)) {
            return new ArrayList<>();
        }
        try {
            Map<Long,CraftsmanDO> craftsmanDOMap = craftsmanRepositoryI.mapByCustomerId(userIds).get();
            Map<Long,CustomerDO>  customerDOMap  = customerRepositoryI.mapByCustomerId(userIds).get();
            return auctionSessionDOS.stream().map(auctionStateDO -> {
                if(auctionStateDO.getCraftsmanId() == null) {
                    return null;
                }
                AuctionStateDTO auctionStateDTO = new AuctionStateDTO();
                Long            craftsmanId     = auctionStateDO.getCraftsmanId();
                CraftsmanDO     craftsmanDO     = craftsmanDOMap.get(craftsmanId);
                if(craftsmanDO != null) {
                    auctionStateDTO.setCraftsmanTitle(craftsmanDO.getTitle());
                }
                CustomerDO customerDO = customerDOMap.get(craftsmanId);
                if(customerDO != null) {
                    auctionStateDTO.setCraftsmanName(customerDO.getName());
                    auctionStateDTO.setCraftsmanAvatar(customerDO.getAvatar());
                }
                auctionStateDTO.setCraftsmanId(auctionStateDO.getCraftsmanId());
                auctionStateDTO.setEnded(auctionStateDO.getEnded());
                auctionStateDTO.setStarted(auctionStateDO.getStarted());
                auctionStateDTO.setUnstart(auctionStateDO.getUnstart());
                return assemble(auctionStateDTO);
            }).filter(v -> v != null).collect(Collectors.toList());

        } catch(Exception e) {
            logger.error("获取数据失败{}",e);
        }
        return new ArrayList<>();
    }

    @Override
    public AuctionStateEntity assemble(AuctionStateDTO dto) {
        AuctionStateEntity auctionStateEntity = new AuctionStateEntity();
        auctionStateEntity.setCraftsmanId(dto.getCraftsmanId());
        auctionStateEntity.setEndTotal(dto.getEnded());
        auctionStateEntity.setStartedTotal(dto.getStarted());
        auctionStateEntity.setUnstartTotal(dto.getUnstart());
        auctionStateEntity.setId(dto.getCraftsmanId().toString());
        auctionStateEntity.setCraftsmanAvatar(dto.getCraftsmanAvatar());
        auctionStateEntity.setCraftsmanName(dto.getCraftsmanName());
        auctionStateEntity.setCraftsmanTitle(dto.getCraftsmanTitle());
        return auctionStateEntity;
    }

}
