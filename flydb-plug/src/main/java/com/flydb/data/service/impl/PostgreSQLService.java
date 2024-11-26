package com.flydb.data.service.impl;

import com.flydb.data.entity.DBConfig;
import com.flydb.data.entity.HistoryInfo;
import com.flydb.data.service.DBService;

import javax.sql.DataSource;
import java.util.List;

public class PostgreSQLService implements DBService {

    private final DBConfig config;

    public PostgreSQLService(DBConfig config) {
        this.config = config;
    }


    @Override
    public DataSource getDs() throws Exception {
        return null;
    }

    @Override
    public boolean checkLog() throws Exception {
        return false;
    }

    @Override
    public List<HistoryInfo> getList() throws Exception {
        return List.of();
    }

    @Override
    public void saveNow() throws Exception {

    }
}
