package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.mapper.kaipao.CategoryMapper;
import cn.idongjia.divine.db.mybatis.pojo.CategoryDO;
import cn.idongjia.divine.db.mybatis.query.CategoryQuery;
import cn.idongjia.divine.utils.Utils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/8/16.
 */
@Repository
public class CategoryRepository implements CategoryRepositoryI {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public Map<Long,CategoryDO> mapByCategoryId(List<Long> categoryIds) {
        if(Utils.isEmpty(categoryIds)) {
            return new HashMap<>();
        }

        CategoryQuery        categoryQuery = CategoryQuery.builder().categoryIds(categoryIds).build();
        List<CategoryDO>     categoryDOS   = categoryMapper.select(categoryQuery);
        Map<Long,CategoryDO> categoryDOMap = new HashMap<>();
        if(Utils.isNotEmpty(categoryDOS)) {
            categoryDOMap = categoryDOS.stream().collect(Collectors.toMap(categoryDO -> categoryDO.getId().longValue(),v1 -> v1,(v1,v2) -> v1));
        }
        return categoryDOMap;
    }

    @Override
    public CategoryDO getById(Long itemCategoryId) {
        return categoryMapper.getByPrimaryKey(itemCategoryId.intValue());
    }
}
