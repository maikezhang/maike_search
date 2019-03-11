package cn.idongjia.divine.db.datasource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * @author lc
 * @create at 2018/3/9.
 */
@Aspect    // for aop
@Component // for auto scan
@Order(0)  // execute before @Transactional
public class DataSourceAOP {
    public static final  String kaipaoDataSource = "kaipaoDataSource";
    public static final  String userDataSource   = "userDataSource";
    public static final  String zooDataSource    = "zooDataSource";

    @Around("execution(* cn.idongjia.divine.db.mybatis.mapper.zoo..*(..))")
    public Object doZooAround(ProceedingJoinPoint jp) throws Throwable {
        DataSourceTypeManager.set(zooDataSource);
        Object proceed = jp.proceed();
        DataSourceTypeManager.reset();
        return proceed;
    }

    @Around("execution(* cn.idongjia.divine.db.mybatis.mapper.kaipao..*(..))")
    public Object doKaipaoAround(ProceedingJoinPoint jp) throws Throwable {
        DataSourceTypeManager.set(kaipaoDataSource);
        Object proceed = jp.proceed();
        DataSourceTypeManager.reset();
        return proceed;
    }

    @Around("execution(* cn.idongjia.divine.db.mybatis.mapper.user..*(..))")
    public Object doUserAround(ProceedingJoinPoint jp) throws Throwable {
        DataSourceTypeManager.set(userDataSource);
        Object proceed = jp.proceed();
        DataSourceTypeManager.reset();
        return proceed;
    }

}
