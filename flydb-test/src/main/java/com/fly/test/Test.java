package com.fly.test;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@SpringBootTest
@Service
public class Test {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @org.junit.jupiter.api.Test
    public void getConn() throws SQLException {

//        String decryptStr = sm4.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
    }

    public static void main(String[] args) {
        String content = "123456";

        String key = "Swrcc_Certs22336";
        SymmetricCrypto sm4 = SmUtil.sm4(key.getBytes(StandardCharsets.UTF_8));

        String encryptHex = sm4.encryptBase64(content);

        System.out.println(encryptHex);
    }
}
