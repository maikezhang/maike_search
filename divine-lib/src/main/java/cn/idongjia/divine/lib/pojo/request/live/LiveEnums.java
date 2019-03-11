package cn.idongjia.divine.lib.pojo.request.live;

import cn.idongjia.divine.lib.pojo.request.sort.BaseEnum;

/**
 * @author lc
 * @create at 2018/8/9.
 */
public class LiveEnums {

    public enum ShowLocation implements BaseEnum {
        /**
         * 自定义排序
         */
        APP(1,"APP显示"),
        /**
         * 推荐排序
         */
        XIAOCHENGXU(2,"小程序显示");

        ShowLocation(int code,String msg) {
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
