package cn.idongjia.divine.factor;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lc
 * @create at 2018/8/18.
 */
@Getter
@Setter
public class AuctionRecommendFactor implements Factor {
    private int    userType          = 0;
    private Double subscribersFactor = 10d;
    private Double startTimeFactor   = -2d;
    private Double endTimeFactor     = -2d;
    private Double startFactor       = 3d;
    private Double preStartFactor    = 1d;
    private Double uvFactor          = 1d;
    private Long   currentTime       = System.currentTimeMillis() / 1000;
    /**
     * 不能为0
     */
    private Double division          = 30d;
    private Double timeBaseScore     = 480d;
    private Double maxQuality        = 100000000d;
    /**
     * 非个性化数据权重， 如果数据是个性化，那么他的权重等于个性化权重 + normalFator
     */
    private Double normalFator       = 0.5d;
    private int    startStatus       = 1;
    private int    preStartStatus    = 0;
    private long   limitation        = 10000000000L;
    public Map<String,Object> buildScriptSortParams(int userType) {
        Map<String,Object> params = new HashMap<>();
        if(null == this.getDivision() || 0 == this.getDivision()) {
            this.setDivision(80d);
        }
        params.put("end_time_f",this.endTimeFactor);
        params.put("subscribers_f",this.subscribersFactor);
        params.put("start_time_f",this.startTimeFactor);
        params.put("uv_f",this.uvFactor);
        params.put("current_time",System.currentTimeMillis() / 1000);
        params.put("start",this.startStatus);
        params.put("start_f",this.startFactor);
        params.put("pre_start",this.preStartStatus);
        params.put("pre_start_f",this.preStartFactor);
        params.put("user_type",userType);
        params.put("division",this.division);
        params.put("time_base_score",this.timeBaseScore);
        params.put("max_quality",this.maxQuality);

        return params;
    }

}
