package com.fly.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@SpringBootTest
@Service
public class Test {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @org.junit.jupiter.api.Test
    public void getConn() throws SQLException {

    }
}
