package com.flydb.data.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.flydb.data.entity.History;
import com.flydb.data.entity.HistoryInfo;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryService {

    private final String dbPath;

    public HistoryService(String dbPath) throws SQLException {
        this.dbPath = dbPath;
        checkTable();
    }

    private DataSource getDs() throws SQLException {
        SimpleDataSource ds = new SimpleDataSource("jdbc:sqlite:" + dbPath, "", "");
        ds.setLoginTimeout(30);
        return ds;
    }

    public List<History> getHistoryList(String key) throws SQLException {
        String sql = " select * from history where 1=1 ";
        if (StrUtil.isNotBlank(key)) {
            sql += " and title like %" + key + "% ";
        }
        sql += " order by time desc ";
        DataSource ds = getDs();
        return Db.use(ds).query(sql, History.class);

    }

    public List<HistoryInfo> getHistory(String id) throws SQLException {
        String sql = "select * from history_info where historyId=" + id;
        DataSource ds = getDs();
        return Db.use(ds).query(sql, HistoryInfo.class);
    }

    public void addHistory(String title, String name, List<HistoryInfo> items) throws SQLException {

        String historyId = IdUtil.getSnowflakeNextIdStr();
        Entity insertHistory = Entity.create("history")
                .set("id", historyId)
                .set("title", title)
                .set("name", name)
                .set("time", DateUtil.format(DateUtil.date(), "yyyy/MM/dd HH:mm"))
                .set("status", "historyId");

        ArrayList<Entity> insertInfoList = new ArrayList<>();
        for (HistoryInfo info : items) {
            Entity insertInfo = Entity.create("history_info")
                    .set("id", IdUtil.getSnowflakeNextIdStr())
                    .set("historyId", historyId)
                    .set("operate", info.getOperate())
                    .set("type", info.getType())
                    .set("dbName", info.getDbName())
                    .set("tableName", info.getTableName())
                    .set("fieldName", info.getFieldName())
                    .set("sql", info.getSql())
                    .set("time", DateUtil.format(DateUtil.date(), "yyyy/MM/dd HH:mm"));
            insertInfoList.add(insertInfo);
        }

        DataSource ds = getDs();
        Db.use(ds).tx(db -> {
            db.insert(insertHistory);
            db.insert(insertInfoList);
        });

    }

    public void checkTable() throws SQLException {
        // 是否存在 flydb_last 表
        DataSource ds = getDs();
        List<Entity> query = Db.use(ds).query("select name from sqlite_master where type='table' order by name;");
        boolean historyTable = false;
        boolean historyInfoTable = false;
        for (Entity entity : query) {
            String str = entity.getStr("name");
            if ("history".equals(str)) {
                historyTable = true;
            }
            if ("history_info".equals(str)) {
                historyInfoTable = true;
            }
        }

        if (!historyTable) {
            Db.use(ds).execute("CREATE TABLE \"history\" (\n" +
                    "  \"id\" text NOT NULL,\n" +
                    "  \"title\" TEXT NOT NULL,\n" +
                    "  \"name\" TEXT NOT NULL,\n" +
                    "  \"time\" text NOT NULL,\n" +
                    "  \"status\" TEXT NOT NULL,\n" +
                    "  PRIMARY KEY (\"id\")\n" +
                    ");");
        }
        if (!historyInfoTable) {
            Db.use(ds).execute("CREATE TABLE \"history_info\" (\n" +
                    "  \"id\" text NOT NULL,\n" +
                    "  \"historyId\" text,\n" +
                    "  \"operate\" TEXT,\n" +
                    "  \"type\" TEXT,\n" +
                    "  \"dbName\" TEXT,\n" +
                    "  \"tableName\" TEXT,\n" +
                    "  \"fieldName\" TEXT,\n" +
                    "  \"sql\" TEXT,\n" +
                    "  \"time\" text,\n" +
                    "  PRIMARY KEY (\"id\")\n" +
                    ");");
        }
    }
}
