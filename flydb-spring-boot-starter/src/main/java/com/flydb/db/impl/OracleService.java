package com.flydb.db.impl;

import com.flydb.db.TargetService;
import com.flydb.entity.FlydbLogs;
import com.flydb.entity.Table;
import com.flydb.entity.TableInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Lazy
public class OracleService implements TargetService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean isExist(String dbName) {
        System.out.println("oracle");
        return false;
    }

    @Override
    public boolean createLogsTable() {
        return false;
    }

    @Override
    public boolean addLogs(FlydbLogs logs) {
        return false;
    }

    @Override
    public boolean updateLogs(FlydbLogs logs) {
        return false;
    }

    @Override
    public List<Table> getTableNames() {
        return null;
    }

    @Override
    public List<TableInfo> getTableInfo(String tableName) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getTableData(String tableName, int page, int pageSize) {
        return null;
    }
}
