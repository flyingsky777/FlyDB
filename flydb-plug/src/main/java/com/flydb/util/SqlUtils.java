package com.flydb.util;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.flydb.data.entity.DBConfig;

public class SqlUtils {

    public static DBConfig getSqlInfo(String url) {
        // 全部转为小写，忽略大小写
        String type = StrUtil.cleanBlank(url.toLowerCase());

        // 首先判断是否为标准的JDBC URL，截取jdbc:xxxx:中间部分
        final String name = ReUtil.getGroup1("jdbc:(.*?):", type);
        if (StrUtil.isNotBlank(name)) {
            type = name;
        }

        DBConfig db = new DBConfig();
        db.setUrl(url);
        if (type.contains("mysql") || type.contains("cobar")) {

            String trimmedUrl = url.substring("jdbc:mysql://".length());
            String[] hostPortAndDatabase = trimmedUrl.split("/", 2);
            String hostPort = hostPortAndDatabase[0].split(":")[0];
            String database = hostPortAndDatabase[1].split("\\?")[0];

            db.setHost(hostPort);
            db.setDatabase(database);

        } else if (type.contains("oracle")) {

        } else if (type.contains("postgresql")) {

        } else if (type.contains("sqlite")) {

        } else if (type.contains("sqlserver") || type.contains("microsoft")) {

        } else {

        }
        return db;
    }
}
