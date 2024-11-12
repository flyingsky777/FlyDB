package com.flydb.data.service.impl;

import com.flydb.data.entity.DDLBean;
import com.flydb.data.service.DBService;

import java.util.List;

public class MySqlService  implements DBService {
    @Override
    public List<DDLBean> getDDLList(String time) {
        return List.of();
    }

    @Override
    public List<DDLBean> getDMLList(String time) {
        return List.of();
    }
}
