package cn.idongjia.divine.repository;

import java.util.List;
import java.util.Map;

import cn.idongjia.divine.db.mybatis.pojo.MediaDO;

/**
 * @author lc on 2018/8/24.
 * @class cn.idongjia.divine.repository.MediaRepositoryI
 */
public interface MediaRepositoryI {
    /**
     * 获取商品图片
     * @param itemIds
     * @return
     */
    Map<Long,MediaDO> assembleByItemId(List<Long> itemIds);
	
	MediaDO getByItemId(Long iid);
}
