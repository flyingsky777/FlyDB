package com.flydb.data.entity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.util.List;

@Data
@NoArgsConstructor
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

    public HistoryInfo(String operate) {
        this.operate = operate;
    }

    public HistoryInfo(String operate, String tableName, List<HistoryInfo> child) {
        this.operate = operate;
        this.tableName = tableName;
        this.child = child;
    }

    public String getTitle() {
        if (StrUtil.isNotBlank(sql)) {
            return type + "," + fieldName;
        } else if (StrUtil.isNotBlank(tableName)) {
            return tableName;
        } else if (StrUtil.isNotBlank(operate)) {
            return operate;
        }
        return "";
    }
}
