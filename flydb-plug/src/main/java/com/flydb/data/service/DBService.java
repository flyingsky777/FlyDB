package com.flydb.data.service;


import com.flydb.data.entity.HistoryInfo;

import java.util.List;

public interface DBService {

    /**
     * 获取 DDL列表
     */
    List<HistoryInfo> getDDLList(String time);

    /**
     * 获取 DML列表
     */
    List<HistoryInfo> getDMLList(String time);
}
