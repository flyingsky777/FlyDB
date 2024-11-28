package com.flydb.service;

import com.flydb.db.ResourceService;
import com.flydb.db.TargetService;
import com.flydb.entity.HistoryInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Component
public class SyncExec {
    @Resource
    private ResourceService resourceService;
    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 事务不能在同一个类中自调用、所以新建一个类
     */
    @Transactional
    public Integer exec(String historyId) {
        try {
            long l = System.currentTimeMillis();
            List<HistoryInfo> infoList = resourceService.getCommitDetail(historyId);
            for (HistoryInfo item : infoList) {
                jdbcTemplate.execute(item.getSql());
                log.info("==> FlyDB 执行成功：" + item.getTableName() + " | " + item.getFieldName() + " | " + item.getSql());
            }
            return Math.toIntExact((System.currentTimeMillis() - l) / 1000);
        } catch (Exception e) {
            log.error("FlyDB 执行sql异常：", e);
            return null;
        }
    }
}
