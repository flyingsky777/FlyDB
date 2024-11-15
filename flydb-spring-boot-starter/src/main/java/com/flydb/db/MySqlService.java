package com.flydb.db;

import com.flydb.config.FlyDBProperties;
import com.flydb.entity.FlydbLogs;
import com.flydb.entity.Table;
import com.flydb.entity.TableInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Lazy
public class MySqlService implements FlyDBService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
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
    public boolean createLogsTable() {
        String table = flyDBProperties.getTable();
        jdbcTemplate.execute("CREATE TABLE `" + table + "` (" +
                "  `id` int NOT NULL AUTO_INCREMENT, " +
                "  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL," +
                "  PRIMARY KEY (`id`) " +
                ") ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='FlyDB日志表';");
        return true;
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
