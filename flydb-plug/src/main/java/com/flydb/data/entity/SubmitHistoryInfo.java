package com.flydb.data.entity;

import lombok.Data;

@Data
public class SubmitHistoryInfo {
    private String historyId;

    // ddl dml
    private String operate;

    // 操作类型： create, alter, drop
    // 操作类型： insert/update/delete
    private String type;

    private String dbName;
    private String tableName;
    private String tableComment;

    private String fieldName;
    private String fieldType;
    private String fieldComment;

    // 主键值
    private String primaryVal;

    private String sql;

    private String updateDate;
}
