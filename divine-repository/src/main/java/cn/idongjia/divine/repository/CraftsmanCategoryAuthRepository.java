package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.mapper.kaipao.CraftsmanCategoryAuthMapper;
import cn.idongjia.divine.db.mybatis.pojo.CategoryDO;
import cn.idongjia.divine.db.mybatis.pojo.CraftsmanCategoryRelDO;
import cn.idongjia.divine.utils.Utils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/8/15.
 */
@Repository
public class CraftsmanCategoryAuthRepository implements CraftsmanCategoryAuthRepostoryI {
    @Resource
    private CraftsmanCategoryAuthMapper craftsmanCategoryAuthMapper;

    @Override
    public List<CategoryDO> list(Integer craftsmanId) {
        return craftsmanCategoryAuthMapper.listByCraftsmanId(Arrays.asList(craftsmanId.longValue()));

    }

    @Override
    public Map<Long,List<CraftsmanCategoryRelDO>> assemble(List<Long> craftsmanIds) {
        List<CraftsmanCategoryRelDO>           craftsmanCategoryRelDOS = craftsmanCategoryAuthMapper.groupByCraftsmanId(craftsmanIds);
        Map<Long,List<CraftsmanCategoryRelDO>> categoryMap = new HashMap<>();
        if(Utils.isNotEmpty(craftsmanCategoryRelDOS)) {
            categoryMap = craftsmanCategoryRelDOS.stream().collect(Collectors.groupingBy(craftsmanCategoryRelDO -> craftsmanCategoryRelDO.getCraftsmanId().longValue()));

        }
        return categoryMap;
    }
}
