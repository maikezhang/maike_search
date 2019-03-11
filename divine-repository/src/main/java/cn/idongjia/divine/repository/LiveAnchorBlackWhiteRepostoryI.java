package cn.idongjia.divine.repository;

import cn.idongjia.divine.db.mybatis.pojo.LiveAnchorBlackWhiteDO;

import java.util.List;
import java.util.Map;

/**
 * @author lc on 2018/9/1.
 * @class cn.idongjia.divine.repository.LiveAnchorBlackWhiteRepostoryI
 */
public interface LiveAnchorBlackWhiteRepostoryI {

    Map<Long,LiveAnchorBlackWhiteDO> mapStateByCraftsmanIds(List<Long> anchorIds);
}
