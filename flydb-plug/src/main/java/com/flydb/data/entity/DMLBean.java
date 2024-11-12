package com.flydb.data.entity;

import lombok.Data;

@Data
public class DMLBean {
    // 操作类型： insert/update/delete
    private String type;

    private String dbName;
    private String tableName;
    private String tableComment;

    // 主键值
    private String primaryVal;

    private String sql;

    private String updateDate;
}
