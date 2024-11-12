package com.flydb.data.entity;

import lombok.Data;

@Data
public class DDLBean {
    // 操作类型： create, alter, drop
    private String type;

    private String dbName;
    private String tableName;
    private String tableComment;

    private String fieldName;
    private String fieldType;
    private String fieldComment;

    private String sql;

    private String updateDate;
}
