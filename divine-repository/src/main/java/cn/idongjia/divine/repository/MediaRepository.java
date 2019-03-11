package cn.idongjia.divine.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.idongjia.divine.db.mybatis.mapper.kaipao.MediaMapper;
import cn.idongjia.divine.db.mybatis.pojo.MediaDO;
import cn.idongjia.divine.db.mybatis.query.MediaQuery;
import cn.idongjia.divine.utils.Utils;

/**
 * @author lc
 * @create at 2018/8/24.
 */
@Repository
public class MediaRepository implements MediaRepositoryI {
    @Resource
    private MediaMapper mediaMapper;

    @Override
    public Map<Long,MediaDO> assembleByItemId(List<Long> itemIds) {
        MediaQuery        mediaQuery = MediaQuery.builder().resourceIds(itemIds).sourceType(1).mediaType(1).build();
        List<MediaDO>     mediaDOS   = mediaMapper.select(mediaQuery);
        Map<Long,MediaDO> mediaMap   = new HashMap<>();
        if(Utils.isNotEmpty(mediaDOS)) {
            mediaMap = mediaDOS.stream().collect(Collectors.toMap(MediaDO::getSourceId,v1 -> v1,(v1,v2) -> v1));
        }
        return mediaMap;
    }
	
	@Override
	public MediaDO getByItemId(Long iid) {
		MediaQuery mediaQuery = MediaQuery.builder().sourceId(iid).sourceType(1).mediaType(1).build();
		List<MediaDO> mediaDOS = mediaMapper.select(mediaQuery);
		if (Utils.isNotEmpty(mediaDOS)) {
			return mediaDOS.get(0);
		}
		return null;
	}
}
