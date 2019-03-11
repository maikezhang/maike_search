package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.mapper.kaipao.ItemCategoryMapper;
import cn.idongjia.divine.db.mybatis.pojo.AuctionSessionRelDO;
import cn.idongjia.divine.db.mybatis.query.ItemCategoryQuery;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author lc
 * @create at 2018/8/16.
 */
@Repository
public class ItemCategoryRepository implements ItemCategoryRepositoryI {

    @Resource
    private ItemCategoryMapper itemCategoryMapper;

}
