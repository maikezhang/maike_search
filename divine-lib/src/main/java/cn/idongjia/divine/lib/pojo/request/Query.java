package cn.idongjia.divine.lib.pojo.request;

import cn.idongjia.divine.lib.pojo.Command;
import lombok.Getter;
import lombok.Setter;

/**
 * 命令
 *
 * @author lc
 * @create at 2018/7/7.
 */
@Getter
@Setter
public abstract class Query extends Command {
    public static final String ORDERBY = "orderby";
    public static final String PAGE    = "page";
    public static final String LIMIT   = "perpage";

    public static final String ORDERBY_DEFAULT = "createtm desc";



}
