package com.flydb.service;

import cn.hutool.db.Db;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.flydb.entity.History;
import com.flydb.entity.HistoryInfo;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Service
public class FlySqliteService {
    public void init(String dbPath) {
        DataSource ds = new SimpleDataSource("jdbc:sqlite:" + dbPath, "", "");
        try {
            Db.use(ds).execute("CREATE TABLE IF NOT EXISTS  history (id text NOT NULL, title TEXT NOT NULL, name TEXT NOT NULL, time text NOT NULL, status TEXT NOT NULL, PRIMARY KEY (id))");
            Db.use(ds).execute("CREATE TABLE IF NOT EXISTS  history_info (historyId text NOT NULL, operate TEXT, type TEXT, dbName TEXT, tableName TEXT, fieldName TEXT, primaryVal TEXT, sql TEXT, time text,PRIMARY KEY ( historyId ))");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<History> getCommitLog() {
        return null;
    }

    public List<HistoryInfo> getCommitDetail(String commitId) {
        return null;
    }


}


