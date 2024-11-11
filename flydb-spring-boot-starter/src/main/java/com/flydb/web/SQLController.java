package com.flydb.web;

import com.flydb.entity.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flydb/sql")
public class SQLController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 获取所有表名
    @GetMapping("/getTableNames")
    public List<Table> getTableNames() {
        String databaseName = jdbcTemplate.execute((ConnectionCallback<String>) (connection) -> connection.getMetaData().getDatabaseProductName());
        List<Table> list = jdbcTemplate.query("SELECT * FROM information_schema.TABLES WHERE TABLE_SCHEMA = '" + databaseName + "'", new BeanPropertyRowMapper<>(Table.class));
        return list;
    }

    // 获取表信息
    @GetMapping("/getTableInfo")
    public String getTableInfo(@RequestParam String tableName) {
        return tableName;
    }

    // 获取表数据
    @GetMapping("/getTableData")
    public String getTableData(String tableName, int page, int pageSize) {

        return null;
    }
}
