package com.flydb.db;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Session;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.flydb.entity.History;
import com.flydb.entity.HistoryInfo;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Service
public class ResourceService {
    private String dbPath;

    public DataSource getDs() {
        return new SimpleDataSource("jdbc:sqlite:" + dbPath, "", "");
    }


    public void init(String dbPath) throws SQLException {
        this.dbPath = dbPath;
        DataSource ds = getDs();
        Db.use(ds).execute("CREATE TABLE IF NOT EXISTS  history (id text NOT NULL, title TEXT NOT NULL, name TEXT NOT NULL, dbName TEXT NOT NULL, time text NOT NULL, status TEXT NOT NULL, PRIMARY KEY (id))");
        Db.use(ds).execute("CREATE TABLE IF NOT EXISTS  history_info (historyId text NOT NULL, operate TEXT, type TEXT, dbName TEXT, tableName TEXT, fieldName TEXT,  sql TEXT, time text,PRIMARY KEY ( historyId ))");
    }

    public List<History> getCommitLog(String dbName) throws SQLException {
        String sql = " select * from history where 1=1 ";
        if (StrUtil.isNotBlank(dbName)) {
            sql += " and dbName like '%" + dbName + "%' ";
        }
        sql += " order by id asc ";
        DataSource ds = getDs();
        return Db.use(ds).query(sql, History.class);
    }

    public List<HistoryInfo> getCommitDetail(String historyId) throws SQLException {
        String sql = "select * from history_info where historyId=" + historyId;
        DataSource ds = getDs();
        return Db.use(ds).query(sql, HistoryInfo.class);
    }


}


