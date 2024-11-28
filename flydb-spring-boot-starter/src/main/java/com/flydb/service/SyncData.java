package com.flydb.service;

import com.flydb.db.ResourceService;
import com.flydb.db.TargetService;
import com.flydb.entity.FlydbLogs;
import com.flydb.entity.History;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SyncData {
    @Resource
    private ResourceService resourceService;
    @Resource
    private TargetService targetService;
    @Resource
    private SyncExec sync;

    /**
     * 同步逻辑
     * 1. 加载sqlite、加载mysql数据库
     * 2. 对比 flydb_logs 表，判断是否需要同步
     * 3。开启事务
     * 4. 执行 sqlliet 数据库的 sql
     * 5. 执行成功后、记录flydb_logs
     * 6. 不成功回滚、记录flydb_logs、输出日志
     * <p>
     * 异常情况处理:
     * 1。 如果发现、中间有数据未执行、则放弃、控制台error提示
     */
    public void sync(String dbName) {
        List<History> commitLog;
        try {
            commitLog = resourceService.getCommitLog(dbName);
        } catch (Exception e) {
            log.error("获取 更新库 数据失败！", e);
            return;
        }

        if (!commitLog.isEmpty()) {
            List<FlydbLogs> flydbLogs;
            try {
                flydbLogs = targetService.getLogs();
            } catch (Exception e) {
                log.error("获取 日志表 数据失败！", e);
                return;
            }

            Map<String, FlydbLogs> map = flydbLogs.stream()
                    .collect(Collectors.toMap(FlydbLogs::getHistoryId, log -> log));

            for (History history : commitLog) {
                if (map.containsKey(history.getId())) {
                    if (map.get(history.getId()).getExecResult() == 0) {
                        // 未执行成功的、再次执行
                        Integer time = sync.exec(history.getId());
                        boolean isSave = setLog(history, time, true);
                        if (time == null | !isSave) {
                            return;
                        }
                    }
                } else {   // 未执行的
                    Integer time = sync.exec(history.getId());
                    boolean isSave = setLog(history, time, false);
                    if (time == null | !isSave) {
                        return;
                    }
                }
            }
        }
    }

    public boolean setLog(History history, Integer time, boolean isOldDel) {
        try {
            FlydbLogs log = new FlydbLogs();
            log.setHistoryId(history.getId());
            log.setTitle(history.getTitle());
            log.setName(history.getName());
            log.setExecTime(new Date());
            if (time != null) {
                log.setExecResult(1);
                log.setExecInterval(time);
            } else {
                log.setExecResult(0);
            }
            return targetService.addLogs(log, isOldDel);
        } catch (Exception e) {
            log.error("保存记录失败！", e);
            return false;
        }
    }


}
