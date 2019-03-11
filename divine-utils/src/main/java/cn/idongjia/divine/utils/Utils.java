package cn.idongjia.divine.utils;

import cn.idongjia.divine.lib.pojo.Conf;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
public class Utils {
    public static final long   SECOND_MAX                  = 9999999999L;
    public static final long   MILLISECOND_RATIO           = 1000L;
    public static final String LINE                        = "-";
    public static final String ATTACHMENT_KEY_OF_DJCONTEXT = "DJContext";

    public static boolean isEmpty(String str) {
        return null == str || str.isEmpty();
    }

    public static boolean isEmpty(Collection collection) {
        return null == collection || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection collection) {
        return !(null == collection || collection.isEmpty());
    }

    public static boolean isEmpty(Object[] objects) {
        return null == objects || objects.length == 0;
    }

    public static boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }

    public static boolean isEmpty(Map map) {
        return null == map || map.isEmpty();
    }

    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }

    public static boolean isNotEmpty(String str) {
        return !(isEmpty(str));
    }

    public static String generateLikeStr(String str) {
        return isEmpty(str) ? null : "%" + str + "%";
    }

    public static boolean isMobile(String mobile) {
        return Pattern.compile("^((\\+)?86)?1[0-9]{10}$").matcher(mobile).matches();
    }

    // TODO 国际手机号校验
    public static boolean isMobile(String code,String mobile) {
        return true;
    }

    public static String getMethodName(String s) {
        StringBuilder str = new StringBuilder();
        str.append("set");
        str.append(s.substring(0,1).toUpperCase()).append(s.substring(1));
        return str.toString();
    }

    public static int calculateStrLength(String str) {
        int m = 0;
        for(char c: str.toCharArray()) {
            if(c >= 0x0391 && c <= 0xFFE5) {
                m = m + 1;
            } else if(c <= 0x00FF) {
                m = m + 1;
            }
        }
        return m;
    }

    public static long getCurrentMillis() {
        return System.currentTimeMillis();
    }

    public static long getCurrentSecond() {
        return getCurrentMillis() / 1000;
    }

    public static Timestamp getNow() {
        return new Timestamp(getCurrentMillis());
    }

    public static String formatLikeLog4j(String formatter,Object... objects) {
        return MessageFormatter.arrayFormat(formatter,objects).getMessage();
    }

    public static <T> Map<String,Object> bean2Map(final Class<T> clazz,Map<String,Object> params) {
        if(null == params) {
            params = new HashMap<>();
        }

        try {
            Field[] fields = clazz.getDeclaredFields();
            for(Field field: fields) {
                String key = field.getName();
                field.setAccessible(true);
                Object value = field.get(key);

                params.put(key,value);
            }
        } catch(IllegalAccessException e) {
            log.warn("bean2Map got error",e);
        }

        return params;
    }

    public static <T> T map2Bean(Map map,final Class<T> clazz) {
        try {
            T       bean   = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for(Field field: fields) {
                String varName = field.getName();
                Object object  = map.get(varName);
                if(object != null) {
                    field.setAccessible(true);
                    field.set(varName,object);
                }
            }
            return bean;
        } catch(InstantiationException | IllegalAccessException e) {
            log.warn("map2Bean got error",e);
        }

        return null;
    }

    public static <E,T> T translateBean(E source,final Class<T> destClazz) {
        try {
            T       dest         = destClazz.newInstance();
            Field[] sourceFields = source.getClass().getDeclaredFields();
            copyProperties(source,sourceFields,dest);
            Field[] superFields = source.getClass().getSuperclass().getDeclaredFields();
            copyProperties(source,superFields,dest);
            return dest;
        } catch(Exception e) {
            log.warn("translateBean got error",e);
        }

        return null;
    }

    private static <E,T> void copyProperties(E source,Field[] sourceFields,T dest) {
        for(Field s: sourceFields) {
            int sourceModifier = s.getModifiers();
            if(!Modifier.isStatic(sourceModifier) && !Modifier.isFinal(sourceModifier)) {
                String name = s.getName();
                s.setAccessible(true);
                try {
                    Object object    = s.get(source);
                    Field  destField = null;
                    // TODO fix the dirty code
                    try {
                        destField = dest.getClass().getDeclaredField(name);
                    } catch(Exception e) {
                        try {
                            destField = dest.getClass().getSuperclass().getDeclaredField(name);
                        } catch(Exception e1) {

                        }
                    }
                    if(null != destField && null != object) {
                        destField.setAccessible(true);
                        PropertyDescriptor pd          = new PropertyDescriptor(name,dest.getClass());
                        Method             writeMethod = pd.getWriteMethod();
                        writeMethod.invoke(dest,object);
                    }
                } catch(Exception e) {
                    log.warn("field [{}] set error",name,e);
                }
            }
        }
    }

    public static <E,T> T translateBean(E source,T dest) {
        try {
            Field[] sourceFields = source.getClass().getDeclaredFields();
            copyProperties(source,sourceFields,dest);
            Field[] superFields = source.getClass().getDeclaredFields();
            copyProperties(source,superFields,dest);
            return dest;
        } catch(Exception e) {
            log.warn("translateBean got error",e);
        }

        return null;
    }

    public static String subString(String sourceString,int maxLength) {
        String resultString = "";
        if(sourceString == null || sourceString.equals("") || maxLength < 1) {
            return resultString;
        } else if(sourceString.length() <= maxLength) {
            return sourceString;
        } else if(sourceString.length() > 2 * maxLength) {
            sourceString = sourceString.substring(0,2 * maxLength);
        }

        if(sourceString.length() > maxLength) {
            char[] chr       = sourceString.toCharArray();
            int    strNum    = 0;
            int    strGBKNum = 0;
            int    len       = 0;

            for(int i = 0;i < sourceString.length();i++) {
                if(chr[i] >= 0xa1) { //0xa1汉字最小位开始
                    strNum = strNum + 2;
                    strGBKNum++;
                } else {
                    strNum++;
                }
                len++;

                if(len == maxLength) {
                    break;
                }

                if(strNum == 2 * maxLength || strNum == 2 * maxLength + 1) {
                    break;
                }
            }
            resultString = sourceString.substring(0,strNum - strGBKNum) + "...";
        }

        return resultString;
    }

    /**
     * 版本号比对方法
     *
     * @param version        基线版本号
     * @param currentVersion 当前版本号
     * @return 是否是基线版本号或更新的版本号
     */
    public static boolean afterVersion(String version,String currentVersion) {
        return currentVersion.compareToIgnoreCase(version) >= 0;
    }

    public static boolean isMilliSecond(long tm) {
        return tm > SECOND_MAX;
    }

    public static Long getMilliSecond(Long tm) {
        if(null != tm) {
            return isMilliSecond(tm) ? tm : tm * MILLISECOND_RATIO;
        }

        return null;
    }

    public static Long getSecond(Long tm) {
        if(null != tm) {
            return isMilliSecond(tm) ? tm / MILLISECOND_RATIO : tm;
        }

        return null;
    }

    public static BigDecimal getPrice(String price) {
        return new BigDecimal(price).setScale(2,BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal getPrice(Double price) {
        return new BigDecimal(price).setScale(2,BigDecimal.ROUND_HALF_UP);
    }

    public static Map<String,Object> objectToMap(Object obj,boolean ignoreNull) throws Exception {
        if(obj == null)
            return null;

        Map<String,Object> map = new HashMap<String,Object>();

        BeanInfo             beanInfo            = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for(PropertyDescriptor property: propertyDescriptors) {
            String key = property.getName();
            if(key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value  = getter != null ? getter.invoke(obj) : null;
            if(ignoreNull && value == null) {
                continue;
            }
            map.put(key,value);
        }

        return map;
    }

    public static Map<String,Object> objectToMap(Object obj) throws Exception {
        return Utils.objectToMap(obj,true);
    }

    public static String getDefaultString(String value) {
        return value == null ? "" : value;
    }

    public static Long getDefaultTime(Date date) {
        Long curTime;
        if(date == null) {
            return Conf.defaultDate;
        } else {
            curTime = date.getTime();
        }
        return curTime;
    }

    public static Long getDefaultTime(Long date) {
        return date != null ? date : Conf.defaultDate;
    }
    public static Long getDefaultShortTime(Long date) {
        return date == null ? Conf.defaultShortDate : date;
    }

    public static Long getDefaultTime(Integer date) {
        return date == null ? Conf.defaultDate : date;
    }
    public static Long getDefaultShortTime(Integer date) {
        return date == null ? Conf.defaultShortDate : date;
    }
    public static Long getDefault(Long date) {
        return date == null ? Conf.defaultDate : date;
    }

    public static Long getDefault(BigDecimal value) {
        return value == null ? Conf.defaultPrice : value.longValue();
    }

    public static Integer getDefault(Integer value) {
        return value == null ? 0 : value;
    }
    public static Integer getDefaultWeight(Integer value) {
        return value == null ? Conf.defaultWeight: value;
    }



    public static Long getDefaultId(Long id) {
        return id == null ? -1000L : id;
    }

    public static Long getDefaultId(Integer id) {
        return id == null ? -1000L : id;
    }

    public static Integer getDefaultIntegerId(Integer id) {
        return id == null ? -1000 : id;
    }

    public static Integer getDefaultEnum(Integer value) {
        return value == null ? -1000 : value;
    }

    /**
     * 0都不显示 1 app显示小程序不显示 2小程序显示 app不显示  3 小程序和app都显示
     *
     * @param sourceStatus
     * @param moveStatus
     * @return
     */
    public static Integer moveLoaction(Integer sourceStatus,Integer moveStatus) {

        byte[] sourceBytes = toBinary(sourceStatus);
        if(moveStatus > 0) {
            byte[] moveBytes = toBinary(moveStatus);
            byte[] bytes     = OR(sourceBytes,moveBytes);
            return toInt(bytes);
        } else if(moveStatus < 0) {
            moveStatus = -moveStatus;
            byte[] moveBytes   = toBinary(moveStatus);
            byte[] bytes       = NAND(sourceBytes,moveBytes);
            byte[] resultBytes = AND(sourceBytes,bytes);
            return toInt(resultBytes);
        }
        return sourceStatus;

    }

    public static int toInt(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.put(bytes);
        int result = buffer.getInt(0);
        return result;
    }

    public static byte[] AND(byte[] source,byte[] move) {
        byte[] result = new byte[source.length];
        for(int i = source.length - 1;i >= 0;i--) {
            byte src = source[i];
            byte mov = move[i];
            result[i] = (byte)(src & mov);
        }
        return result;
    }

    public static byte[] OR(byte[] source,byte[] move) {
        byte[] result = new byte[source.length];
        for(int i = source.length - 1;i >= 0;i--) {
            byte src = source[i];
            byte mov = move[i];
            result[i] = (byte)(src | mov);
        }
        return result;
    }

    public static byte[] NAND(byte[] source,byte[] move) {
        byte[] result = new byte[source.length];
        for(int i = source.length - 1;i >= 0;i--) {
            byte src = source[i];
            byte mov = move[i];
            result[i] = (byte)~(src & mov);
        }
        return result;
    }

    /**
     * int 转 byte[]
     * 大端
     *
     * @param data
     * @return
     */
    public static byte[] toBinary(int data) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putInt(data);
        byte[] bytes = buffer.array();
        return bytes;
    }
}
