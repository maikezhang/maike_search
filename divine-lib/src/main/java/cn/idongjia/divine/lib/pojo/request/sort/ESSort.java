package cn.idongjia.divine.lib.pojo.request.sort;

import cn.idongjia.se.lib.engine.query.boost.TermsField;
import cn.idongjia.se.lib.engine.query.sort.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lc on 2018/8/9.
 * @class cn.idongjia.guice.lib.pojo.request.sort.ESSort
 */


public abstract class ESSort {

    protected List<Sort>          sorts = new ArrayList<>();
    protected TermsField<Integer> statusTerms;

    public void addSorts(List<Sort> sorts) {
        this.sorts.addAll(sorts);
    }
    public void addSort(Sort sort) {
        this.sorts.add(sort);
    }
    public List<Sort> getSorts(){
        return this.sorts;
    }

    public void setTermsField(TermsField termsField) {
        statusTerms = termsField;
    }
}
