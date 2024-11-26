package com.flydb.data.service;


import com.flydb.data.entity.HistoryInfo;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public interface DBService {

     DataSource getDs() throws SQLException;

    /**
     * 获取 DDL列表 和 DML 列表
     */
    List<HistoryInfo> getList();

    /**
     * 保存当前日志
     */
    void saveNow();

}
