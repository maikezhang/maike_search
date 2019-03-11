package cn.idongjia.divine.factor;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/12/28
 * Time: 上午9:35
 */
@Getter
@Setter
public class HomePageLiveSortFactor implements Factor {

    private Double  msgWeight   = 1.4d;
    private Double  uvWeight    = 1d;
    private Double  freshWeight = 0.41;
    private Integer start       = 2;
    private Integer preStart    = 1;


    public Map<String, Object> buildScriptSortParams(Double msgWeight, Double uvWeight, Double freshWeight) {
        Map<String, Object> params = new HashMap<>();

        params.put("msgWeight", Objects.isNull(msgWeight) ? this.msgWeight : msgWeight);
        params.put("uvWeight", Objects.isNull(uvWeight) ? this.uvWeight : uvWeight);
        params.put("freshWeight", Objects.isNull(freshWeight) ? this.freshWeight : freshWeight);
        params.put("start", this.start);
        params.put("currentTime", System.currentTimeMillis());
        params.put("preStart", this.preStart);


        return params;
    }
}
