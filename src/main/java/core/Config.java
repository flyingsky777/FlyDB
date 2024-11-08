package core;

public class Config {
    /**
     * 是否开启FlyDB服务
     */
    public static Boolean enable;

    /**
     * Sqlite 数据库文件路径、默认为resoureces/flydb.db
     */
    public static String dbPath = "flydb.db";

    /**
     * 网页端口、默认为当前服务端口
     */
    public static Integer port;

    public static String rootPath;

}
