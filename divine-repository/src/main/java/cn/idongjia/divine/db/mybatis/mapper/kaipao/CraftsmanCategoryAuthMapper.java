package cn.idongjia.divine.db.mybatis.mapper.kaipao;


import cn.idongjia.divine.db.mybatis.mapper.kaipao.base.CraftsmanCategoryAuthBaseMapper;
import cn.idongjia.divine.db.mybatis.pojo.CategoryDO;
import cn.idongjia.divine.db.mybatis.pojo.CraftsmanCategoryRelDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：匠人类目授权表 的自定义接口，所有自定义接口均定义在此
 *
 * @author lc
 * @date 2018/08/15
 */
public interface CraftsmanCategoryAuthMapper extends CraftsmanCategoryAuthBaseMapper {
    public List<CategoryDO> listByCraftsmanId(@Param("craftsmanIds") List<Long> craftsmanIds);

    List<CraftsmanCategoryRelDO> groupByCraftsmanId(@Param("craftsmanIds") List<Long> craftsmanIds);
}
