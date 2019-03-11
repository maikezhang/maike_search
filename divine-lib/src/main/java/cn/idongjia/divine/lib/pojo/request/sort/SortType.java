package cn.idongjia.divine.lib.pojo.request.sort;

/**
 * @author lc
 * @create at 2018/8/9.
 */
public class SortType {

    public enum TabSortType implements BaseEnum {
        /**
         * 自定义排序
         */
        SELF(1,"自定义排序"),
        /**
         * 推荐排序
         */
        RECOMMEND(2,"给定脚本排序"),
        /**
         * 按默认排序
         */
        TAB_SORT(3,"给定TAB排序"),
        /**
         * 专场拍品聚合
         */
        AUCTION_GROUP(4,"专场拍品聚合"),
        /**
         * 直播默认排序
         */
        LIVE_DEFAULT(5,"直播默认排序"),
        /**
         * 查询功能排序
         */
        SEARCH(6,"直播默认排序"),
        SESSION_DEFAULT(7,"专场默认排序"),
        SESSION_STATE(8,"专场默认排序"),
        /**
         * 提供给首页直播展示的排序
         */
        HOME_PAGE_LIVE(9,"首页直播排序");


        TabSortType(int code,String msg) {
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
