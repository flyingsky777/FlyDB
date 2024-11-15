package com.flydb.entity;

import lombok.Data;

@Data
public class HistoryInfo {
    private String historyId;

    // ddl dml
    private String operate;

    // 操作类型： create, alter, drop
    // 操作类型： insert/update/delete
    private String type;

    private String dbName;

    private String tableName;

    private String fieldName;

    // 主键字段
    private String primaryKey;

    // 主键值
    private String primaryVal;

    private String sql;

    private String time;

}
