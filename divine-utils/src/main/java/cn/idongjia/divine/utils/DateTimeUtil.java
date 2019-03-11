package cn.idongjia.divine.utils;

import cn.idongjia.divine.lib.pojo.Conf;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author longchuan
 */
public final class DateTimeUtil {
    private DateTimeUtil() {
    }

    private static final DateFormat dateformat = new SimpleDateFormat("yyyy年MM月dd日");

    /**
     * 根据毫秒时间获取timestamp时间戳
     *
     * @param timeLong Long型毫秒
     * @return 时间戳
     */
    public static Timestamp millis2Timestamp(Long timeLong) {
        if (timeLong == null) {
            return null;
        }
        timeLong = TimeUnit.MILLISECONDS.toMillis(timeLong);
        return new Timestamp(timeLong);
    }

    /**
     * 根据时间戳获取Long毫秒时间
     *
     * @param timestamp 时间戳
     * @return 毫秒长整型
     */
    public static Long timestamp2Millis(Timestamp timestamp) {
        Long curTime;
        if (timestamp == null) {
            return null;
        } else {
            curTime = timestamp.getTime();
        }
        return curTime;
    }

    public static Long date2Millis(Date date) {
        Long curTime;
        if (date == null) {
            return Conf.defaultDate;
        } else {
            curTime = date.getTime();
        }
        return curTime;
    }

    public static String getLong2Date(Long time) {
        if (time == null) {
            return null;
        }
        return dateformat.format(time);

    }

    public static String getLong2Time(Long time) {
        if (null == time) {
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(time);
    }

    public static String getLong2DateDetail(String dateStr) {

        if (null == dateStr) {
            return null;
        }
        long   time = System.currentTimeMillis();
        String date = dateformat.format(time);
        if (dateStr.equals(date)) {
            return "今日";
        } else {
            long zeroTime     = getZeroDateLong(time);
            long oneDayMillis = TimeUnit.DAYS.toMillis(1);
            long intervalTime = oneDayMillis + zeroTime;
            date = dateformat.format(intervalTime);
            if (dateStr.equals(date)) {
                return "明日";
            }
        }
        return dateStr;
    }

    private static Long getZeroDateLong(long date) {
        DateFormat format  = new SimpleDateFormat("yyyyMMdd");
        String     dateStr = format.format(date);
        try {
            return format.parse(dateStr).getTime();
        } catch (ParseException e) {
            return 0L;
        }
    }

}
