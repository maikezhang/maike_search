package cn.idongjia.divine.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.idongjia.divine.db.mybatis.mapper.kaipao.AuctionSessionMapper;
import cn.idongjia.divine.db.mybatis.mapper.kaipao.AuctionSessionRelMapper;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionRelDO;
import cn.idongjia.divine.db.mybatis.query.AuctionSessionRelQuery;
import cn.idongjia.divine.utils.Utils;

/**
 * @author lc
 * @create at 2018/8/16.
 */
@Repository
public class AuctionSessionRelRepository implements AuctionSessionRelRepositoryI {
    @Resource
    private AuctionSessionMapper auctionSessionMapper;

	@Resource
	private AuctionSessionRelMapper	auctionSessionRelMapper;
    /**
     * 根据直播id查询专场
     *
     * @param itemIds
     * @return
     */
    @Override
    public Map<Long,AuctionSessionRelDO> mapByItemIds(List<Long> itemIds) {
        List<AuctionSessionRelDO>     auctionSessionRelDOS   = auctionSessionMapper.listByItemIds(itemIds);
        Map<Long,AuctionSessionRelDO> auctionSessionRelDOMap = new HashMap<>();
        if(Utils.isNotEmpty(auctionSessionRelDOS)) {
            auctionSessionRelDOMap = auctionSessionRelDOS.stream().collect(Collectors.toMap(AuctionSessionRelDO::getAid,v1 -> v1,(v1,v2) -> {
                return v1.getAsid() >= v2.getAsid() ? v1 : v2;
            }));
        }
        return auctionSessionRelDOMap;
    }
	
	@Override
	public List<AuctionSessionRelDO> listBySessionId(Integer asid) {
		return auctionSessionRelMapper.select(AuctionSessionRelQuery.builder().asid(asid).build());
	}
}
