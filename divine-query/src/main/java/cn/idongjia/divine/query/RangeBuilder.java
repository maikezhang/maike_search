package cn.idongjia.divine.query;

import cn.idongjia.se.lib.engine.query.logical.Range;

/**
 * @author lc
 * @create at 2018/8/7.
 */
public class RangeBuilder {

    /**
     * 默认包含上下界
     *
     * @param min
     * @param <T>
     * @return
     */
    public static <T> Range<T> build(T min) {
        if (min == null) {
            return null;
        }
        Range<T> range = new Range<>();
        range.setLowValue(min);
        return range;
    }

    public static <T> Range<T> build(T min, T max) {
        if (min == null && max == null) {
            return null;
        }
        Range<T> range = new Range<>();
        range.setLowValue(min);
        range.setUpValue(max);
        return range;
    }

    public static <T> Range<T> build(T min, T max, Boolean lower) {
        if (min == null && max == null) {
            return null;
        }
        Range<T> range = new Range<>();
        range.setLowValue(min);
        range.setUpValue(max);
        range.setLower(lower);
        return range;
    }

    public static <T> Range<T> build(T min, T max, Boolean lower, Boolean uper) {
        if (min == null && max == null) {
            return null;
        }
        Range<T> range = new Range<>();
        range.setLowValue(min);
        range.setUpValue(max);
        range.setLower(lower);
        range.setUper(uper);
        return range;
    }

    public static <T> Range<T> build(T min, T max, Boolean lower, Boolean uper, Boolean matchAll) {
        if (min == null && max == null) {
            return null;
        }
        Range<T> range = new Range<>();
        range.setLowValue(min);
        range.setUpValue(max);
        range.setLower(lower);
        range.setUper(uper);
        range.setMatchAll(matchAll);
        return range;
    }
}
