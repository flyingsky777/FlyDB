package com.flydb.data.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.flydb.data.entity.SubmitHistory;
import com.flydb.data.entity.SubmitHistoryInfo;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryService {

    private final DataSource ds;

    public HistoryService(String dbPath) {
        this.ds = new SimpleDataSource("jdbc:sqlite:" + dbPath, "", "");
    }

    public List<SubmitHistory> getHistoryList(String key) {
        try {
            String sql = " select * from submit_history where 1=1 ";
            if (StrUtil.isNotBlank(key)) {
                sql += " and title like %" + key + "% ";
            }
            sql += " order by time desc ";
            return Db.use(ds).query(sql, SubmitHistory.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SubmitHistoryInfo> getHistory(String id) {
        try {
            String sql = "select * from submit_history_info where historyId=" + id;
            return Db.use(ds).query(sql, SubmitHistoryInfo.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addHistory(SubmitHistory history, List<SubmitHistoryInfo> items) {
        try {
            String historyId = IdUtil.getSnowflakeNextIdStr();
            Entity insertHistory = Entity.create("submit_history")
                    .set("id", historyId)
                    .set("title", history.getTitle())
                    .set("name", history.getName())
                    .set("time", DateUtil.format(DateUtil.date(), "yyyy/MM/dd HH:mm"))
                    .set("status", "historyId");

            ArrayList<Entity> insertInfoList = new ArrayList<>();
            for (SubmitHistoryInfo info : items) {
                Entity insertInfo = Entity.create("submit_history_info")
                        .set("historyId", historyId)
                        .set("operate", info.getOperate())
                        .set("type", info.getType())
                        .set("dbName", info.getDbName())
                        .set("tableName", info.getTableName())
                        .set("fieldName", info.getFieldName())
                        .set("primaryVal", info.getPrimaryVal())
                        .set("sql", info.getSql())
                        .set("time", DateUtil.format(DateUtil.date(), "yyyy/MM/dd HH:mm"));
                insertInfoList.add(insertInfo);
            }

            Db.use(ds).tx(db -> {
                db.insert(insertHistory);
                db.insert(insertInfoList);
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
