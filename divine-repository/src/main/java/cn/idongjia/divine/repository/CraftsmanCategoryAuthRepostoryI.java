package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.pojo.CategoryDO;
import cn.idongjia.divine.db.mybatis.pojo.CraftsmanCategoryAuthDO;
import cn.idongjia.divine.db.mybatis.pojo.CraftsmanCategoryRelDO;

import java.util.List;
import java.util.Map;

/**
 * @author lc on 2018/8/15.
 * @class cn.idongjia.divine.repository.CraftsmanCategoryAuthRepostoryI
 */
public interface CraftsmanCategoryAuthRepostoryI {
    List<CategoryDO> list(Integer craftsmanId);
    Map<Long,List<CraftsmanCategoryRelDO>> assemble(List<Long> craftsmanIds);
}
