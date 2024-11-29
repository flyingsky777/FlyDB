package com.flydb.utils;

import cn.hutool.crypto.digest.DigestUtil;

public class Util {
    public static String getToken(String account, String password) {
        String s = DigestUtil.md5Hex(account) + DigestUtil.md5Hex(password);
        return DigestUtil.md5Hex(s);
    }
}
