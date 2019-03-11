package cn.idongjia.divine.annotation;

import cn.idongjia.common.context.DJContext;
import cn.idongjia.log.Log;
import cn.idongjia.log.LogFactory;
import cn.idongjia.util.Utils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author lc
 * @create at 2018/8/3.
 */
@Aspect
public class ElapsedHandler {
    private static final Log logger = LogFactory.getLog(ElapsedHandler.class);

    @Pointcut("@annotation(cn.idongjia.divine.annotation.Elapsed)")
    public void around() {
    }

    @Around("around()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long     before = Utils.getCurrentMillis();
        Class<?> aClass = joinPoint.getTarget().getClass();
        // 获取目标方法签名
        String signature  = joinPoint.getSignature().toString();
        String methodName = signature.substring(signature.lastIndexOf(".") + 1, signature.indexOf("("));
        Object obj        = joinPoint.proceed();
        long   after      = Utils.getCurrentMillis();
        logger.info(" uniqueId {} invork {}.{} spend {} ms",DJContext.getUniqueID(),aClass.getSimpleName(),methodName,after - before);
        return obj;
    }
}
