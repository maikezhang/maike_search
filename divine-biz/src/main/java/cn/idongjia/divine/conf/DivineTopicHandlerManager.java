package cn.idongjia.divine.conf;

import cn.idongjia.common.base.Base;
import cn.idongjia.divine.mq.kafka.listener.BinLogListener;
import cn.idongjia.excephandler.exception.DJSysException;
import cn.idongjia.kafka.annocation.KafkaListener;
import cn.idongjia.kafka.annocation.TopicHandler;
import cn.idongjia.kafka.codec.ProtostuffSerialize;
import cn.idongjia.kafka.consumer.TopicHandlerManager;
import cn.idongjia.kafka.message.IMessageHandler;
import cn.idongjia.kafka.message.Message;
import cn.idongjia.log.Log;
import cn.idongjia.log.LogFactory;
import com.fasterxml.jackson.databind.JavaType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import static cn.idongjia.excephandler.common.constant.ErrorCodeConst.SYSTEM_ERROR;
import static cn.idongjia.kafka.utils.JacksonUtil.JSON_MAPPER;

/**
 * @Author luorenshu(626115221 @ qq.com)
 * @date 2018/5/2 下午4:06
 **/
public class DivineTopicHandlerManager implements BeanPostProcessor {

    private static final Log                         logger          = LogFactory.getLog(DivineTopicHandlerManager.class);
    private static final Log                         KAFKA_DISPACHER = LogFactory.getLog(DivineTopicHandlerManager.class);
    @Resource
    private              DisconfValueManager         disconfValueManager;
    /**
     * 集群任务执行者
     */
    private              Map<String,IMessageHandler> cluserHandleMap = new ConcurrentHashMap<String,IMessageHandler>();

    private String[] annotationPackages;

    public void setBasePackage(String annotationPackage) {
        this.annotationPackages = (annotationPackage == null || annotationPackage.length() == 0) ? null : Pattern.compile("\\s*[,]+\\s*").split(annotationPackage);
    }

    private DivineTopicHandlerManager() {
    }

    @Override
    public Object postProcessAfterInitialization(Object bean,String beanName) throws BeansException {
        logger.debug("开始注册bean" + beanName);
        Class<?> clazz = bean.getClass();

        if(!isMatchPackage(clazz)) {
            return bean;
        }

        if(!clazz.isAnnotationPresent(KafkaListener.class)) {
            return bean;
        }

        logger.debug("开始注册taskRunner" + beanName);
        addTopicHandler(bean);
        return bean;
    }

    public void addTopicHandler(Object bean) {
        Class<?> clazz   = bean.getClass();
        Method[] methods = clazz.getMethods();
        if(methods != null && methods.length > 0) {
            TopicHandlerManager topicHandlerManager = TopicHandlerManager.getInstance();
            for(final Method method: methods) {
                logger.debug("###解析method:{}###" + method);
                if(method.isAnnotationPresent(TopicHandler.class)) {
                    TopicHandler topicHandler = method.getAnnotation(TopicHandler.class);
                    String       topic        = topicHandler.topic();
                    if(topic.startsWith(BinLogListener.KAIPAO_TOPIC)) {
                        topic = topic.replace(BinLogListener.KAIPAO_TOPIC,disconfValueManager.getKaipaoTopic());
                    } else if(topic.startsWith(BinLogListener.USER_TOPIC)) {
                        topic = topic.replace(BinLogListener.USER_TOPIC,disconfValueManager.getUserTopic());

                    } else if(topic.startsWith(BinLogListener.ZOO_TOPIC)) {
                        topic = topic.replace(BinLogListener.ZOO_TOPIC,disconfValueManager.getZooTopic());
                    }
                    IMessageHandler messageHandler = build(bean,method,method.getParameterTypes());
                    cluserHandleMap.put(topic,messageHandler);

                    logger.debug("###topic:{} 注册处理器 成功###",topic);
                }
            }
            topicHandlerManager.registClusterTopicHandler(cluserHandleMap);
        }
    }

    private IMessageHandler build(final Object targetObject,final Method targetMethod,final Class<?>[] pTypes) {
        //参数长度目前只支持两种,一种是带结构体的,一种是byte数组
        int paramSize = pTypes.length;

        if(paramSize == 1) {
            Type              type      = targetMethod.getGenericParameterTypes()[0];
            ParameterizedType paramType = (ParameterizedType)type;
            Class             msgType;
            Class             messageType;

            if(paramType.getActualTypeArguments()[0] instanceof Class) {
                msgType = (Class)paramType.getActualTypeArguments()[0];
                messageType = null;
            } else if(paramType.getActualTypeArguments()[0] instanceof Type) {
                ParameterizedType parameInnerType = (ParameterizedType)paramType.getActualTypeArguments()[0];
                messageType = (Class)parameInnerType.getRawType();
                msgType = (Class)parameInnerType.getActualTypeArguments()[0];
            } else {
                throw new DJSysException(SYSTEM_ERROR,"消息监听器参数有误");
            }

            return (topic,key,body) -> {
                Message message = new Message();
                message.setTopic(topic);
                message.setKey(key);
                if(body instanceof byte[] && messageType == null) {
                    final ProtostuffSerialize serialize   = ProtostuffSerialize.getInstance(msgType);
                    Base                      messageBody = (Base)serialize.decoder((byte[])body,msgType.newInstance());
                    message.setBody(messageBody);
                    KAFKA_DISPACHER.info("###topic:{} key:{} body:{}###",topic,key,messageBody.toString());
                } else if(isMysqlMsg(body,messageType)) {
                    JavaType javaType    = JSON_MAPPER.getTypeFactory().constructParametricType(messageType,msgType);
                    Base     messageBody = JSON_MAPPER.readValue(String.valueOf(body),javaType);
                    message.setBody(messageBody);
                }
                targetMethod.invoke(targetObject,message);
            };
        } else if(paramSize == 2) {
            return (topic,key,body) -> {
                KAFKA_DISPACHER.info("###topic:{} key:{}###",topic,key);
                targetMethod.invoke(targetObject,key,body);
            };
        }
        logger.error("###没有找到合适的处理类,参数个数:{}###",paramSize);
        return null;
    }

    private boolean isMysqlMsg(Object body,Class messageType) {
        return body instanceof String && messageType != null;
    }

    private boolean isMatchPackage(Class<?> clazz) {
        if(annotationPackages == null || annotationPackages.length == 0) {
            return true;
        }
        String beanClassName = clazz.getName();
        beanClassName = beanClassName.replaceAll("\\."," ");

        for(String pkg: annotationPackages) {
            pkg = pkg.replaceAll("\\."," ");
            if(beanClassName.startsWith(pkg)) {
                return true;
            }
        }
        return false;
    }
}
