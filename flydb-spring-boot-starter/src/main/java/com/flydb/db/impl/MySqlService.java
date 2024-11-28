package com.flydb.db.impl;

import com.flydb.config.FlyDBProperties;
import com.flydb.db.TargetService;
import com.flydb.entity.FlydbLogs;
import com.flydb.entity.Table;
import com.flydb.entity.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


@Slf4j
public class MySqlService implements TargetService {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private FlyDBProperties flyDBProperties;

    @Override
    public boolean isExist(String dbName) {
        AtomicBoolean flag = new AtomicBoolean(false);
        jdbcTemplate.query("SHOW TABLES LIKE '" + dbName + "';", (rs, rowNum) -> {
            flag.set(dbName.equals(rs.getString(1)));
            return null;
        });
        return flag.get();
    }

    @Override
    public void createLogsTable() {
        String table = flyDBProperties.getTable();
        jdbcTemplate.execute("CREATE TABLE `" + table + "` (\n" +
                "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                "  `history_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '提交ID',\n" +
                "  `title` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '提交说明',\n" +
                "  `name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '提交人',\n" +
                "  `exec_time` datetime NOT NULL COMMENT '执行开始时间',\n" +
                "  `exec_interval` int NOT NULL COMMENT '时长',\n" +
                "  `exec_result` int NOT NULL COMMENT '1-成功、0-失败',\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='FlyDB日志表';");
    }

    @Override
    public List<FlydbLogs> getLogs() {
        String table = flyDBProperties.getTable();
        return jdbcTemplate.query("select * from " + table, new BeanPropertyRowMapper<>(FlydbLogs.class));
    }

    @Override
    public boolean addLogs(FlydbLogs logs, boolean isOldDel) {
        String table = flyDBProperties.getTable();
        try {
            if (isOldDel) {
                jdbcTemplate.execute("delete from " + table + " where history_id = '" + logs.getHistoryId() + "'");
            }

            String sql = "insert into " + table
                    + "(history_id, title, name, exec_time, exec_interval, exec_result) values (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    logs.getHistoryId(),
                    logs.getTitle(),
                    logs.getName(),
                    logs.getExecTime(),
                    logs.getExecInterval(),
                    logs.getExecResult());
            return true;
        } catch (Exception e) {
            log.error("添加日志失败！", e);
        }
        return false;
    }

    @Override
    public List<Table> getTableNames() {
        String databaseName = jdbcTemplate.execute((ConnectionCallback<String>) (connection) -> connection.getMetaData().getDatabaseProductName());
        return jdbcTemplate.query("SELECT * FROM information_schema.TABLES WHERE TABLE_SCHEMA = '" + databaseName + "'", new BeanPropertyRowMapper<>(Table.class));
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
