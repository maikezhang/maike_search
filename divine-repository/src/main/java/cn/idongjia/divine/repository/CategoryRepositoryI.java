package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.pojo.CategoryDO;

import java.util.List;
import java.util.Map;

/**
 * @author lc on 2018/8/16.
 * @class cn.idongjia.divine.repository.CategoryRepositoryI
 */
public interface CategoryRepositoryI {
    Map<Long,CategoryDO> mapByCategoryId(List<Long> categoryIds);

    CategoryDO getById(Long itemCategoryId);
}
