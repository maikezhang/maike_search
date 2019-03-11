package cn.idongjia.divine.cache;

import cn.idongjia.util.RedisTools;
import cn.idongjia.util.Utils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Description: 聊天消息的偏移量缓存
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/12/17
 * Time: 上午11:22
 */
@Component
public class ZooMessageUpdateOffsetCache {


    @Resource
    private RedisTools redisDB;

    private static final String KEY = "Zoo_Message_Update_Offset";

    public boolean lpushMessageOffset(Long zid) {

        if(Objects.isNull(zid)){
            return false;
        }
        Long lpush = redisDB.lpush(KEY, zid.toString());
        return lpush > 0 ? true : false;
    }


    public List<Long> lpopAllZid(){

        Long llen = redisDB.llen(KEY);
        List<String> lzid = redisDB.lpop(KEY, llen.intValue());

        if(Utils.isEmpty(lzid)){
            return new ArrayList<>();
        }

        return lzid.stream().filter(x->Objects.nonNull(x)).map(zid->Long.valueOf(zid)).collect(Collectors.toList());

    }
}
