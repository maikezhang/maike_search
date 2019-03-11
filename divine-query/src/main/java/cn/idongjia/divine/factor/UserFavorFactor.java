package cn.idongjia.divine.factor;

import cn.idongjia.divine.utils.Utils;
import cn.idongjia.dw.lib.pojo.community.UserFavor;
import cn.idongjia.util.RedisTools;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Tuple;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lc
 * @create at 2018/9/5.
 */
@Component
@Slf4j
public class UserFavorFactor {
    @Resource
    private RedisTools redisDB;
    @Resource
    private RedisTools recommendDB;

    public Double transForm(Double a,Double b) {

        return Math.log(a) / Math.log(b);

    }

    //todo 判断是新用户还是老用户
    public Boolean isOldUser(Long uid,String key) {

        return redisDB.sismember(key,uid.toString());
    }

    //todo redis 有序集合转换成 map
    public Map<Long,Double> getSortSetToMap(Long uid,String tag) {

        Set<Tuple> uc = null;

        uc = redisDB.zrangeWithScores(tag + uid,0L,-1L);

        log.info("uc{}",uc);

        Map<Long,Double> map = new HashMap<Long,Double>();

        if(!uc.isEmpty()) {

            Iterator<Tuple> it = uc.iterator();

            while(it.hasNext()) {

                Tuple tup = it.next();

                String elem = tup.getElement();

                Double score = tup.getScore();

                map.put(Long.valueOf(elem),score);

            }

            log.info(tag + " res " + map);
            return map;

        }

        return null;
    }

    //todo 获取首页为你优选用户偏好 保留前20
    public Map<Long,Double> getCateScore(Long uid,String devId) {

        Set<Tuple> uc = null;

        if(uid != null) {
            uc = recommendDB.zrangeWithScores("u_" + uid + "_c",0L,-1L);
        }

        log.info(uid + " get from redis " + uc);

        if(uc == null && devId != null) {
            uc = recommendDB.zrangeWithScores("d_" + devId + "_c",0L,-1L);
        }

        if(uc != null) {

            if(!uc.isEmpty()) {
                Map<Long,Double> hashMap = getCateCombine(uc);

                Map<Long,Double> top50 = hashMap.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(20).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(e1,e2) -> (e2),HashMap::new));

                log.info(uid + " getCateScore res " + top50);

                return top50;
            }

        }

        return null;
    }

    //todo 用户叶子类目扩展偏好
    public Map<Long,Double> getCateCombine(Set<Tuple> uc) {
        String          prefixKey = "au_";
        Iterator<Tuple> it        = uc.iterator();

        Map<Long,Double> map = new HashMap<Long,Double>();

        Map<String,Double> keyScore = new HashMap<>();
        List<String>       cateKey  = new ArrayList<>();
        while(it.hasNext()) {
            Tuple  tup      = it.next();
            String leafCate = tup.getElement();
            Double score    = transForm(tup.getScore() + 1,10.0);
            keyScore.put(leafCate,score);
            cateKey.add(prefixKey + leafCate);
        }
        if(Utils.isNotEmpty(cateKey)) {
            List<String> redisValues = redisDB.pipeGet(cateKey);
            if(Utils.isNotEmpty(redisValues)) {
                for(int i = cateKey.size() - 1;i >= 0;i--) {
                    String key      = cateKey.get(i);
                    String cate     = redisValues.get(i);
                    String leafCate = key.substring(prefixKey.length());
                    Double score    = keyScore.get(leafCate);
                    if(!Utils.isEmpty(cate)) {
                        JSONArray jsonArray = new JSONArray(cate);
                        int       cateLev   = jsonArray.length() + 1;
                        Double    lastScore = score / cateLev;
                        map.put(Long.valueOf(leafCate),lastScore);
                        for(int j = 0;j < jsonArray.length();j++) {
                            Long record     = jsonArray.getLong(j);
                            Long cateRecord = record;
                            if(map.containsKey(record)) {
                                Double thisScore = map.get(cateRecord);
                                map.put(cateRecord,thisScore + lastScore);
                            } else {
                                map.put(cateRecord,lastScore);
                            }
                        }
                    } else {
                        map.put(Long.valueOf(leafCate),score);
                    }
                }
            }
        }

        return map;
    }

    //todo 获取用户偏好合并
    public UserFavor getUserFavor(Long uid,String devId,Integer auctionOrLive) {

        UserFavor res = new UserFavor();

        //todo 判断用户是否新老用户 直播 0 拍卖 1
        res.setOldUser(false);

        if(uid != null) {
            if(auctionOrLive != null) {
                if(auctionOrLive.intValue() == 0) {

                    res.setOldUser(isOldUser(uid,"live_old_uid"));

                } else if(auctionOrLive.intValue() == 1) {

                    res.setOldUser(isOldUser(uid,"auction_old_uid"));

                }
            }

            //todo 关注直播
            res.setFollowLive(getSortSetToMap(uid,"live_book_"));

            //todo 关注商品
            res.setFollowAucItem(getSortSetToMap(uid,"follow_auc_item_"));

            //todo 关注匠人
            res.setFollowCraftsman(getSortSetToMap(uid,"follow_cra_"));

            //todo 追加字段
            res.setAppend(getSortSetToMap(uid,"auction_recommend_append"));
        }

        //todo 用户偏好
        res.setUserCateScore(getCateScore(uid,devId));

        res.setUid(uid);
        log.debug("user favor res {}",res);

        return res;
    }
}
