package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.mapper.kaipao.AuctionBookMapper;
import cn.idongjia.divine.db.mybatis.pojo.CountPO;
import cn.idongjia.divine.db.mybatis.query.AuctionBookQuery;
import cn.idongjia.divine.utils.Utils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/8/10.
 */
@Repository
public class AuctionBookRepository implements AuctionBookRepositoryI {
    @Resource
    private AuctionBookMapper auctionBookMapper;
    @Override
    public int countByContentId(Long sessionId,int contentType) {
        AuctionBookQuery auctionBookQuery = new AuctionBookQuery();
        auctionBookQuery.setStatus(0);
        auctionBookQuery.setContentType(contentType);
        auctionBookQuery.setContentId(sessionId);
        return auctionBookMapper.count(auctionBookQuery);
    }

    @Override
    public Map<Long,CountPO> countByContentId(List<Long> sessionIds,Integer contentType) {
        List<CountPO>     countPOS = auctionBookMapper.groupBySessionIds(sessionIds,contentType);
        Map<Long,CountPO> map      = null;
        if(Utils.isNotEmpty(countPOS)) {
            map = countPOS.stream().collect(Collectors.toMap(CountPO::getId,v1 -> v1,(v1,v2) -> v1));
        }
        if(map == null) {
            map = new HashMap<>();
        }
        return map;
    }
}
