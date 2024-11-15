package com.flydb.data.service.impl;

import com.flydb.data.entity.HistoryInfo;
import com.flydb.data.service.DBService;

import java.util.List;

public class MySqlService implements DBService {
    @Override
    public List<HistoryInfo> getDDLList(String time) {
        return List.of();
    }

    @Override
    public List<HistoryInfo> getDMLList(String time) {
        return List.of();
    }
}
