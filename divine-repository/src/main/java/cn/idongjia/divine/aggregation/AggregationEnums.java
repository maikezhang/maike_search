package cn.idongjia.divine.aggregation;

import cn.idongjia.divine.lib.pojo.request.sort.BaseEnum;

/**
 * @author lc
 * @create at 2018/9/6.
 */
public class AggregationEnums {
    public enum AggregationType implements BaseEnum {
        /**
         * 自定义排序
         */
        AUCTION_PRICE(1,"自定义排序");

        AggregationType(int code,String msg) {
            this.code = code;
            this.msg = msg;
        }

        private int    code;
        private String msg;

        /**
         * 获取对应code
         *
         * @return code
         */
        @Override
        public int getCode() {
            return this.code;
        }

        /**
         * 获取对应msg
         *
         * @return msg
         */
        @Override
        public String getMsg() {
            return this.msg;
        }
    }
}
