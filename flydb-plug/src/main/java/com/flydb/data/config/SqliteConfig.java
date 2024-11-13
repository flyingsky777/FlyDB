package com.flydb.data.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConfig {
    /**
     * 默认的数据库名称。读取配置文件时，如果没有配置，则使用这个默认值
     */
    public static final String DB_NAME = "flydb.db";

    private Connection connection;

    public SqliteConfig() {
        // 读取Springboot的所有yml配置文件、查找flydb里的配置信息

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
