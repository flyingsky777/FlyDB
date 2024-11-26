package com.flydb.data.service.impl;

import com.flydb.data.entity.DBConfig;
import com.flydb.data.entity.HistoryInfo;
import com.flydb.data.service.DBService;
import net.sf.jsqlparser.JSQLParserException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class PostgreSQLService implements DBService {

    private final DBConfig config;

    public PostgreSQLService(DBConfig config) {
        this.config = config;
    }

    @Override
    public DataSource getDs() throws SQLException {
        return null;
    }

    @Override
    public boolean check() throws SQLException {
        return false;
    }

    @Override
    public List<HistoryInfo> getList() throws SQLException {
        return List.of();
    }

    @Override
    public void saveNow() throws SQLException {

    }
}
