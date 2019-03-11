package cn.idongjia.divine.convertor;

/**
 * @author lc
 * @create at 2018/7/7.
 */
public interface ConvertorI<C, D> {


    public C dataToClient(D dataObject);

    public default D clientToData(C entityObject){
        return null;
    }
}
