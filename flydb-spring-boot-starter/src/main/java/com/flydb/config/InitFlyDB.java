package com.flydb.config;

import cn.hutool.core.io.FileUtil;
import com.flydb.db.FlyDBService;
import com.flydb.service.FlySqliteService;
import com.flydb.service.SyncData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;

/**
 * 初始化FlyDB
 * 1. 创建 记录表
 * 2. 创建 flydb.db
 */
@Slf4j
@Component
public class InitFlyDB implements CommandLineRunner {

    @Autowired
    private FlyDBProperties flyDBProperties;
    @Autowired
    private FlyDBService dbService;
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private FlySqliteService sqliteService;
    @Autowired
    private SyncData syncData;

    @Override
    public void run(String... args) throws Exception {
        log.info("\n" +
                " ____  __    _  _  ____   ____ \n" +
                "( ___)(  )  ( \\/ )(  _ \\ (  _ \\\n" +
                " )__)  )(__  \\  /  )(_) ) ) _ <\n" +
                "(__)  (____) (__) (____/ (____/\n" +
                "                          0.0.1");

        // 不存在 flydb_logs 创建
        String table = flyDBProperties.getTable();
        boolean exist = dbService.isExist(flyDBProperties.getTable());
        if (!exist) {
            log.info("===============> FlyDB 创建：" + table + " <===============");
            dbService.createLogsTable();
        }

        // 不存在 fly.db 创建
        String flydbPath = "";
        String dbPath = flyDBProperties.getDbPath();
        Resource resource = resourceLoader.getResource(dbPath);

        if (!resource.exists()) { // 未加载到
            String path = ResourceUtils.getURL("classpath:").getPath();
            if (path.endsWith("/target/classes/")) { // IDE
                path = path.substring(1, path.lastIndexOf("/target/classes/"));
                path += "/src/main/resources/";
                String dbName = dbPath.replace("classpath:", "");
                path += dbName;
                log.info("===============> FlyDB 创建：" + dbName + " <===============");
                if (!FileUtil.exist(path)) {
                    File touch = FileUtil.touch(path);
                    if (touch.exists()) {
                        flydbPath = path;
                    }
                }
            }
        } else {
            log.info("===============> FlyDB 加载：" + dbPath + " <===============");
            flydbPath = resource.getFile().getPath();
        }
        // 执行初始化表
        sqliteService.init(flydbPath);

        // 执行数据同步
        log.info("===============> FlyDB 开始同步 <===============");
        syncData.sync(flydbPath);
    }


}