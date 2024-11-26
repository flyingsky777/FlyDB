package com.flydb.data.service;


import com.flydb.data.entity.HistoryInfo;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public interface DBService {

    DataSource getDs() throws Exception;

    /**
     * 检测是否支持
     */
    boolean checkLog() throws Exception;

    /**
     * 获取 DDL列表 和 DML 列表
     */
    List<HistoryInfo> getList() throws Exception;

    /**
     * 保存当前日志
     */
    void saveNow() throws Exception;

}
