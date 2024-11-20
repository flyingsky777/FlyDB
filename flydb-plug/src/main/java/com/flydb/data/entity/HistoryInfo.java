package com.flydb.data.entity;

import lombok.Data;

import java.util.List;

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

    private String fieldType;

    private String sql;

    private String time;

    private String title;

    private List<HistoryInfo> child;


}
