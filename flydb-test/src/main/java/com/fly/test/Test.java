package com.fly.test;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@SpringBootTest
@Service
public class Test {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @org.junit.jupiter.api.Test
    public void getConn() throws SQLException {
        String sql=" use `flydb`; CREATE TABLE `flydb`.`test`  (\n" +
                "        `id` int NOT NULL AUTO_INCREMENT,\n" +
                "        `name` varchar(255) NULL,\n" +
                "        PRIMARY KEY (`id`)\n" +
                "      )";
        Statement statement = CCJSqlParserUtil.parse(sql);
    }
}
