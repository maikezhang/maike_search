package cn.idongjia.divine.db.datasource;


public class DataSourceTypeManager {
    private static final ThreadLocal<String> dataSourceTypes = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return DataSourceAOP.kaipaoDataSource;
        }
    };

    public static String get() {
        return dataSourceTypes.get();
    }

    public static void set(String dataSourceType) {
        dataSourceTypes.set(dataSourceType);
    }

    public static void reset() {
        dataSourceTypes.set(DataSourceAOP.kaipaoDataSource);
    }
}
