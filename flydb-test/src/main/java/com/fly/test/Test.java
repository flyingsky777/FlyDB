package com.fly.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

@SpringBootTest
@Service
public class Test {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    DataSource dataSource;

    @org.junit.jupiter.api.Test
    public void getConn() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            String type = metaData.getDatabaseProductName();
            System.out.println(metaData.getURL());
            System.out.println(metaData.getUserName());
//            String jsonStr = JSONUtil.toJsonStr(metaData);
//            System.out.println(jsonStr);
        } catch (SQLException ignored) {
        }
    }

}
