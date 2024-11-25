package com.flydb.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.setting.dialect.Props;
import cn.hutool.setting.yaml.YamlUtil;
import com.flydb.data.entity.DBConfig;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigUtil {


    /**
     * 查找项目中 application 中配置的所有 数据库配置信息
     */
    public static List<DBConfig> getMysqlList(String basePath) {
        List<File> files = FileUtil.loopFiles(basePath, file -> file.getName().endsWith(".yml") | file.getName().endsWith(".properties"));
        files = files.stream().filter(file -> !file.getPath().contains("\\target\\classes")).toList();

        // yml 和 properties 文件里配置的 flydb.dbPath
        List<DBConfig> list = new ArrayList<>();
        for (File file : files) {
            if (file.getName().endsWith(".yml")) {
                Dict load = YamlUtil.load(FileUtil.getReader(file, Charset.defaultCharset()));
                String jsonStr = JSONUtil.toJsonStr(load);
                JSONObject entries = JSONUtil.parseObj(jsonStr);

                Object dynamic = JSONUtil.getByPath(entries, "spring.datasource.dynamic.datasource");
                Object url = JSONUtil.getByPath(entries, "spring.datasource.url");
                if (dynamic != null && dynamic != "") {
                    JSONArray objects = JSONUtil.parseArray(dynamic);
                    for (Object object : objects) {
                        JSONObject obj = JSONUtil.parseObj(object);
                        Iterator<Map.Entry<String, Object>> iterator = obj.iterator();
                        if (iterator.hasNext()) {
                            Map.Entry<String, Object> next = iterator.next();
                            Object value = next.getValue();

                            JSONObject jsonObject = JSONUtil.parseObj(value);
                            String url1 = jsonObject.getStr("url");
                            String username = jsonObject.getStr("username");
                            String password = jsonObject.getStr("password");

                            DBConfig dbConfig = SqlUtils.getSqlInfo(url1);
                            dbConfig.setName(username);
                            dbConfig.setPass(password);
                            list.add(dbConfig);
                        }
                    }

                } else if (url != null && url != "") {
                    Object obj = JSONUtil.getByPath(entries, "spring.datasource");
                    JSONObject json = JSONUtil.parseObj(obj);
                    String username = json.getStr("username");
                    String password = json.getStr("password");

                    DBConfig dbConfig = SqlUtils.getSqlInfo((String) url);
                    dbConfig.setName(username);
                    dbConfig.setPass(password);
                    list.add(dbConfig);
                }

            } else if (file.getName().endsWith(".properties")) {
//                Props prop = new Props(file);
                // TODO 待支持
            }
        }
        return list;
    }

    /**
     * 项目可能存在、单项目、多项目等复杂情况。查找所有可能的 fly.db
     * 1. 查找项目中所有的 springboot的配置文件 flydb 指定的 数据库文件名
     * 2. 查找所有 flydb.db + 指定的名称。
     *
     * @param basePath
     * @return
     */
    public static Object[] getFlyDBPath(String basePath) {
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
        return dbFiles.stream()
                .map(File::getPath)
                .map(e -> {
                    String bp = basePath.replace("/", "\\");
                    return e.replace(bp, "")
                            .replace("\\src\\main\\resources\\", "");
                })
                .toArray();
    }


}
