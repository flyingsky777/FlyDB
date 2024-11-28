package com.flydb.db.impl;

import com.flydb.db.TargetService;
import com.flydb.entity.FlydbLogs;
import com.flydb.entity.Table;
import com.flydb.entity.TableInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class OracleService implements TargetService {

    @Override
    public boolean isExist(String dbName) {
        return false;
    }

    @Override
    public void createLogsTable() {
    }

    @Override
    public List<FlydbLogs> getLogs() {
        return List.of();
    }

    @Override
    public boolean addLogs(FlydbLogs logs, boolean isOldDel) {
        return false;
    }


    @Override
    public List<Table> getTableNames() {
        return List.of();
    }

    @Override
    public List<TableInfo> getTableInfo(String tableName) {
        return List.of();
    }

    @Override
    public List<Map<String, Object>> getTableData(String tableName, int page, int pageSize) {
        return List.of();
    }
}
