package cn.idongjia.divine.lib.pojo.request.sort;

/**
 * @author lc
 * @create at 2018/8/9.
 */
public class BizType {

    public enum ModuleType implements BaseEnum {
        /**
         * 自定义排序
         */
        LIVE(1, "直播"),
        /**
         * 推荐排序
         */
        AUCTION(2,"拍卖"),
        CRAFTSMAN(3,"匠人"),
        SESSION(4,"专场"),
        AUCTION_STATE(5,"拍卖状态统计"),
        ZOO_MESSAGE(6,"聊天室消息"),
        SESSION_LIVE(7, "直播拍")
        ;


        ModuleType(int code, String msg) {
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
