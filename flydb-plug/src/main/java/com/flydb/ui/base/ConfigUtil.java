package com.flydb.ui.base;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.setting.dialect.Props;
import cn.hutool.setting.yaml.YamlUtil;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigUtil {

    public static void main(String[] args) {
        List<String> flyDBPath = getFlyDBPath("E:\\zhl\\flydb-test");
        System.out.println(flyDBPath);
    }

    /**
     *  项目可能存在、单项目、多项目等复杂情况。查找所有可能的 fly.db
     *  1. 查找项目中所有的 springboot的配置文件 flydb 指定的 数据库文件名
     *  2. 查找所有 flydb.db + 指定的名称。
     * @param basePath
     * @return
     */
    public static List<String> getFlyDBPath(String basePath) {
        List<File> files = FileUtil.loopFiles(basePath, file -> file.getName().endsWith(".yml") | file.getName().endsWith(".properties"));

        // yml 和 properties 文件里配置的 flydb.dbPath
        List<String> list = new ArrayList<>();
        for (File file : files) {
            String str = "";
            if (file.getName().endsWith(".yml")) {
                Dict load = YamlUtil.load(FileUtil.getReader(file, Charset.defaultCharset()));
                str = load.getByPath("flydb.db-path");
            } else if (file.getName().endsWith(".properties")) {
                Props prop1 = new Props(file);
                str = prop1.getStr("flydb.db-path");
            }

            if (str == null | "".equals(str)) continue;
            if (str.contains("/")) {
                String[] split = str.split("/");
                list.add(split[split.length - 1]);
            } else {
                list.add(str);
            }
        }

        List<File> dbFiles = FileUtil.loopFiles(basePath, file -> file.getName().equals("fly.db") | list.contains(file.getName()));
        return dbFiles.stream().map(File::getPath).collect(Collectors.toList());
    }


}
