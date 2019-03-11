package cn.idongjia.divine.impl;

import cn.idongjia.divine.annotation.Elapsed;
import cn.idongjia.divine.biz.LiveQueryBO;
import cn.idongjia.divine.lib.pojo.request.Page;
import cn.idongjia.divine.lib.pojo.request.live.LiveQry;
import cn.idongjia.divine.lib.pojo.request.sort.SortType;
import cn.idongjia.divine.lib.pojo.response.MultiResponse;
import cn.idongjia.divine.lib.pojo.response.live.general.GeneralLiveCO;
import cn.idongjia.divine.lib.service.LiveService;
import cn.idongjia.divine.utils.Utils;
import cn.idongjia.divine.utils.exception.DivineException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lc
 * @create at 2018/8/18.
 */
@Service("liveService")
public class LiveServiceImpl implements LiveService {

    @Resource
    private LiveQueryBO liveQueryBO;

    /**
     * 直播列表查询
     * 通用
     *
     * @param liveQry
     * @return
     */
    @Override
    @Elapsed
    public MultiResponse<GeneralLiveCO> search(LiveQry liveQry) {
        if(Utils.isNotEmpty(liveQry.getTitle())&&liveQry.getTitle().length()>50){
            throw  DivineException.failure("查询长度不能超过50个字符");
        }
        return liveQueryBO.query(liveQry);
    }

    /**
     * tab查询
     *
     * @param liveQry
     * @return
     */
    @Override
    @Elapsed
    public MultiResponse<GeneralLiveCO> tab(LiveQry liveQry) {
        liveQry.setSortType(SortType.TabSortType.TAB_SORT);
        return liveQueryBO.query(liveQry);
    }

    /**
     * 推荐查询
     *
     * @return
     */
    @Override
    @Elapsed
    public MultiResponse<GeneralLiveCO> recommend(LiveQry liveQry) {
        liveQry.setSortType(SortType.TabSortType.RECOMMEND);
        return liveQueryBO.query(liveQry);
    }

    /**
     * 按id获取数据
     *
     * @param ids
     * @return
     */
    @Override
    @Elapsed
    public MultiResponse<GeneralLiveCO> list(List<Long> ids) {
        if(Utils.isEmpty(ids)){
            return MultiResponse.of(null,0);
        }
        LiveQry liveQry = new LiveQry();
        liveQry.setLiveIds(ids);
        liveQry.setSortType(SortType.TabSortType.SELF);
        return liveQueryBO.query(liveQry);
    }
}
