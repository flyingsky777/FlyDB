package com.flydb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = FlyDBProperties.PREFIX)
public class FlyDBProperties {

    public static final String PREFIX = "flydb";

    /**
     * 是否开启
     */
    private Boolean enable = true;

    /**
     * 数据库文件路径
     */
    private String dbPath = "classpath:/flydb.db";

    /**
     * 版本控制日志表
     */
    private String table = "flydb_logs";

    /**
     * 提交人姓名
     */
    private String commitName;

    /**
     * 是否开启web
     */
    private Boolean enableWeb = true;

    /**
     * Web的登录账号
     */
    private String account;

    /**
     * Web的登录密码
     */
    private String password;


}
