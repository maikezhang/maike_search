package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.mapper.kaipao.base.AuctionExtBaseMapper;
import cn.idongjia.divine.db.mybatis.pojo.AuctionExtDO;
import cn.idongjia.divine.db.mybatis.query.AuctionExtQuery;
import cn.idongjia.divine.utils.Utils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yuexiaodong@idongjia.cn
 * @date 2018-12-18
 */
@Repository
public class AuctionExtRepository implements AuctionExtRepositoryI {

    @Resource
    private AuctionExtBaseMapper auctionExtBaseMapper;

    @Override
    public List<AuctionExtDO> select(AuctionExtQuery query) {
        if (query == null) {
            return Collections.emptyList();
        }
        return auctionExtBaseMapper.select(query);
    }

    @Override
    public Map<Long, AuctionExtDO> mappingByItemIds(List<Long> itemIds) {
        if (Utils.isEmpty(itemIds)) {
            return Collections.emptyMap();
        }
        AuctionExtQuery query = new AuctionExtQuery();
        query.setItemIds(itemIds);
        final List<AuctionExtDO> exts = select(query);
        if (Utils.isEmpty(exts)) {
            return Collections.emptyMap();
        }
        final Map<Long, AuctionExtDO> extMap = exts.stream()
                .collect(Collectors.toMap(x -> x.getItemId(), x -> x, (x1, x2) -> x1));
        return extMap;
    }
}
