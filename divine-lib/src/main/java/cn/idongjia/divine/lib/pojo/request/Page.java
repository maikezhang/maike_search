package cn.idongjia.divine.lib.pojo.request;

import cn.idongjia.divine.lib.pojo.request.sort.SortType;
import cn.idongjia.se.lib.engine.query.sort.Sort;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * 分页数
 *
 * @author lc
 * @create at 2018/7/6.
 */
@Getter
@Setter
@ToString
public class Page extends Query {
    public Page() {

    }

    @QueryParam(Query.PAGE)
    private Integer page = 1;

    @QueryParam(Query.LIMIT)
    private Integer limit = 20;

    private Integer offset;

    @QueryParam(Query.ORDERBY)
    private List<Sort> sorts;

    @QueryParam("sortType")
    private SortType.TabSortType sortType;
    @QueryParam("userInfo")
    private UserInfo             userInfo;

    public void setPage(Integer page) {
        if (page < 1) {
            this.page = 1;
        } else {
            this.page = page;
        }
    }

    public void setLimit(Integer limit) {
        if (limit < 1) {
            this.limit = 1;
        } else {
            this.limit = limit;
        }
    }

    public Integer getOffset() {
        if (null == offset && page != null && limit != null) {
            offset = (page - 1) * limit;
        }
        return offset;
    }
}
