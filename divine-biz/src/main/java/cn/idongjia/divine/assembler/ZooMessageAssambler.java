package cn.idongjia.divine.assembler;

import cn.idongjia.divine.db.es.entity.ZooMessageEntity;
import cn.idongjia.divine.db.mybatis.pojo.ZooMessageDO;
import cn.idongjia.divine.dto.ZooMessageDTO;
import cn.idongjia.log.Log;
import cn.idongjia.log.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/11/23
 * Time: 上午10:29
 */
@Repository
public class ZooMessageAssambler implements AssemblerI<ZooMessageEntity, ZooMessageDO, ZooMessageDTO> {

    private static final Log logger = LogFactory.getLog(ZooMessageAssambler.class);

    @Override
    public List<ZooMessageEntity> assemble(List<ZooMessageDO> zooMessageDOS) {

        if (CollectionUtils.isEmpty(zooMessageDOS)) {
            return new ArrayList<>();
        }
        List<ZooMessageEntity> zooMessageEntityList = zooMessageDOS.stream().map(zooMessageDO -> {
            ZooMessageEntity entity  = new ZooMessageEntity();
            String           content = zooMessageDO.getContent();
            if (Objects.nonNull(content)) {
                entity.setContent(content.length() > 140 ? content.substring(0, 140) : content);
            }
            entity.setCreateTime(zooMessageDO.getCreatetm());
            entity.setStatus(zooMessageDO.getStatus());
            entity.setType(zooMessageDO.getType());
            entity.setUserId(zooMessageDO.getUid());
            entity.setZooId(zooMessageDO.getZid());
            entity.setZooMessageId(zooMessageDO.getZmid());
            entity.setId(zooMessageDO.getZmid().toString());
            return entity;
        }).collect(Collectors.toList());


        return zooMessageEntityList;
    }

    @Override
    public ZooMessageEntity assemble(ZooMessageDTO zooMessageDTO) {
        return null;
    }
}
