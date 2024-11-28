package com.flydb.config;

import com.flydb.db.TargetService;
import com.flydb.db.impl.MySqlService;
import com.flydb.db.impl.OracleService;
import com.flydb.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * 是否开启web服务
 */
@Slf4j
@Configuration
public class FlyDBConfig implements WebMvcConfigurer {
    @Autowired
    FlyDbWebInterceptor flyDbWebInterceptor;
    @Autowired
    DataSource dataSource;
    @Autowired
    FlyDBProperties properties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(flyDbWebInterceptor)
                .addPathPatterns("/flydb/**");
    }

    @Bean
    public TargetService dbService() {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            String type = metaData.getDatabaseProductName();
            String url = metaData.getURL();
            String database = SqlUtils.getDatabase(url);
            properties.setDbName(database);
            log.info("===============> FlyDB 数据库类型：" + type + " <===============");
            switch (type) {
                case "MySQL":
                    return new MySqlService();
                case "Oracle":
                    return new OracleService();
            }
        } catch (SQLException ignored) {
        }
        throw new RuntimeException("数据库连接失败、或不支持的数据库类型！");
    }
}

