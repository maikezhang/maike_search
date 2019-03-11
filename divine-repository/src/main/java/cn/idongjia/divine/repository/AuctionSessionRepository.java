package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.mapper.kaipao.AuctionSessionMapper;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionDO;
import cn.idongjia.divine.db.mybatis.query.AuctionSessionQuery;
import cn.idongjia.util.Utils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/7/31.
 */
@Repository
public class AuctionSessionRepository implements AuctionSessionRepositoryI {
    @Resource
    private AuctionSessionMapper auctionSessionMapper;

    @Override
    public Map<Long, AuctionSessionDO> mapByLiveId(List<Long> liveIds) {
        if (Utils.isEmpty(liveIds)) {
            return new HashMap<>();
        }
        AuctionSessionQuery    auctionSessionQuery = AuctionSessionQuery.builder().liveIds(liveIds).build();
        List<AuctionSessionDO> auctionSessionDOS   = auctionSessionMapper.select(auctionSessionQuery);
        if (Utils.isEmpty(auctionSessionDOS)) {
            return new HashMap<>();
        }
        return auctionSessionDOS.stream().collect(Collectors.toMap(session -> session.getLsid().longValue(), v1 -> v1, (v1, v2) -> v1));
    }

    @Override
    public AuctionSessionDO getByPrimaryKey(int id) {
        return auctionSessionMapper.getByPrimaryKey(id);
    }

    @Override
    public Map<Long,AuctionSessionDO> mapBySessionIds(List<Long> sessionIds) {
        if (Utils.isEmpty(sessionIds)) {
            return new HashMap<>();
        }
        AuctionSessionQuery    auctionSessionQuery = AuctionSessionQuery.builder().sessionIds(sessionIds).build();
        List<AuctionSessionDO> auctionSessionDOS   = auctionSessionMapper.select(auctionSessionQuery);
        if (Utils.isEmpty(auctionSessionDOS)) {
            return new HashMap<>();
        }
        return auctionSessionDOS.stream().collect(Collectors.toMap(session -> session.getAsid().longValue(), v1 -> v1, (v1, v2) -> v1));
    }

    @Override
    public int count(AuctionSessionQuery auctionSessionQuery) {
        return auctionSessionMapper.count(auctionSessionQuery);
    }

    @Override
    public List<AuctionSessionDO> select(AuctionSessionQuery auctionSessionQuery) {
        return auctionSessionMapper.select(auctionSessionQuery);
    }
}
