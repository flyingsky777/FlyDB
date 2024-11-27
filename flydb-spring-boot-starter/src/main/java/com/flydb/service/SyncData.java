package com.flydb.service;

import org.springframework.stereotype.Service;

@Service
public class SyncData {
    /**
     * 同步逻辑
     * 1. 加载sqlite、加载mysql数据库
     * 2. 对比 flydb_logs 表，判断是否需要同步
     * 3。开启事务
     * 4. 执行 sqlliet 数据库的 sql
     * 5. 执行成功后、记录flydb_logs
     * 6. 不成功回滚、记录flydb_logs、输出日志
     */
    public void sync(String flyDbPath) {

    }
}
