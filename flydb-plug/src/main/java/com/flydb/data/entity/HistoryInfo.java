package com.flydb.data.entity;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
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

    // 主键值
    private String primaryVal;

    private String sql;

    private String time;


    public HistoryInfo(String operate) {
        if (operate.equals("dml")) {
            this.type = RandomUtil.randomEle(List.of("insert", "update", "delete"));
        } else {
            this.type = RandomUtil.randomEle(List.of("create", "alter", "drop"));
        }
        this.dbName = "flydb";
        this.tableName = RandomUtil.randomString(10);
//        this.tableComment = tableComment;
        this.fieldName = RandomUtil.randomString(10);
//        this.fieldType = fieldType;
//        this.fieldComment = fieldComment;
//        this.primaryVal = primaryVal;
        this.sql = RandomUtil.randomString(500);
        this.time = DateUtil.format(DateUtil.date(), "yyyy/MM/dd HH:mm");
        ;
//        this.historyId = historyId;
        this.operate = operate;
    }
}
