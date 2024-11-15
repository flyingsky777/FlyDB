package com.flydb.db;

import com.flydb.entity.FlydbLogs;
import com.flydb.entity.Table;
import com.flydb.entity.TableInfo;

import java.util.List;
import java.util.Map;

public interface FlyDBService {

    /**
     * 表是否存在
     */
    boolean isExist(String dbName);

    /**
     * 创建记录表
     */
    boolean createLogsTable();

    /**
     * 添加一条记录
     */
    boolean addLogs(FlydbLogs logs);

    /**
     * 更新记录
     */
    boolean updateLogs(FlydbLogs logs);

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
