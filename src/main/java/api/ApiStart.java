package api;

import cn.hutool.http.HttpUtil;
import cn.hutool.http.server.SimpleServer;

public class ApiStart {
    public static Integer port;
    public static String rootPath;

    public static void start() {
        SimpleServer server = HttpUtil.createServer(port);
        server.setRoot(rootPath);
        server.addAction("/sql", new SQLService());
        server.start();
    }
}
