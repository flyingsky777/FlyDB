package com.flydb.data.service;

import com.flydb.data.entity.DDLBean;

import java.util.List;

public interface DBService {

    /**
     * 获取 DDL列表
     */
    List<DDLBean> getDDLList(String time);

    /**
     * 获取 DML列表
     */
    List<DDLBean> getDMLList(String time);
}
