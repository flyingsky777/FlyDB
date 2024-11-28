package com.flydb.db;

import com.flydb.entity.FlydbLogs;
import com.flydb.entity.Table;
import com.flydb.entity.TableInfo;

import java.util.List;
import java.util.Map;

public interface TargetService {

    /**
     * 表是否存在
     */
    boolean isExist(String dbName);

    /**
     * 创建记录表
     */
    void createLogsTable();

    /**
     * 获取 flydb_logs 数据
     */
    List<FlydbLogs> getLogs();

    /**
     * 添加一条记录
     */
    boolean addLogs(FlydbLogs logs, boolean isOldDel);

    /**
     * 获取所有表名
     */
    List<Table> getTableNames();

    /**
     * 获取表信息
     */
    List<TableInfo> getTableInfo(String tableName);

    /**
     * 获取表数据
     */
    List<Map<String, Object>> getTableData(String tableName, int page, int pageSize);
}
