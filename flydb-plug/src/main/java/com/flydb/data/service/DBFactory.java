package com.flydb.data.service;

import com.flydb.data.entity.DBConfig;
import com.flydb.data.service.impl.MySqlService;
import com.flydb.data.service.impl.PostgreSQLService;

public class DBFactory {
    public static DBService getDBService(DBConfig config) {
        return switch (config.getType()) {
            case "mysql" -> new MySqlService(config);
            case "postgresql" -> new PostgreSQLService(config);
            default -> throw new RuntimeException("不支持的数据库类型");
        };
    }
}
