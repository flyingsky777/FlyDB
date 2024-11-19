package com.flydb.data.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.flydb.data.entity.Binlog;
import com.flydb.data.entity.DBConfig;
import com.flydb.data.entity.FlyLast;
import com.flydb.data.entity.HistoryInfo;
import com.flydb.data.service.DBService;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.alter.AlterExpression;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * MySql binlog ddl 和 dml 提取方案
 * 1. 获取上一次提交的保存数据
 * 2. 获取binlog 日志目录 show binary logs
 * 3. 如果上一次数据为空、则将 2步的日志数据存储、开始第一轮
 * 4. 如果不为空、则对比新增的文件、以及文件大小、判断哪个文件新增过
 * 5. 获取日志文件详情
 *          -  文件改动、获取上一次的pos 和 文件最大的pos 之间的数据 show binlog events in 'binlog.000120' from 126
 *          -  新增文件、全部数据
 * 6. 提交、 将当前的binlog日志详情、以及最后一个文件的pos 保存到数据库 flydb_last
 *
 */
public class MySqlService implements DBService {
    private final DBConfig config;

    public MySqlService(DBConfig config) {
        this.config = config;
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/flydb";
        String username = "root";
        String password = "sw@3100@admin";
        String driver = "com.mysql.cj.jdbc.Driver";
        DBConfig dbConfig = new DBConfig();
        dbConfig.setUrl(url);
        dbConfig.setName(username);
        dbConfig.setPass(password);
        dbConfig.setDriver(driver);
        MySqlService service = new MySqlService(dbConfig);
        boolean b = service.checkBinLog();
        if (b) {
            List<HistoryInfo> list = service.getList();
            list.forEach(System.out::println);
        }
//        service.saveNow();
    }

    @Override
    public List<HistoryInfo> getList() {
        try {
            DataSource ds = new SimpleDataSource(config.getUrl(), config.getName(), config.getPass());
            List<FlyLast> last = Db.use(ds).query("select * from flydb_last", FlyLast.class);
            List<FlyLast> logs = Db.use(ds).query("show binary logs", FlyLast.class);
            if (last.isEmpty()) {
                // 如果上一次数据为空、则将 2步的日志数据存储、初始化开始第一轮
                saveNowBinLog(logs, ds);
            } else {
                FlyLast flyLast = last.stream().filter(item -> item.getPos() != null).findFirst().get();
                String logName = flyLast.getLogName();
                Long pos = flyLast.getPos();

                List<FlyLast> lastList = logs.stream().filter(item -> item.getLogName().compareTo(logName) > 0).toList();

                List<Binlog> binlogList = Db.use(ds).query("show binlog events in '" + logName + "' FROM " + pos + ";", Binlog.class);
                for (FlyLast item : lastList) {
                    List<Binlog> list2 = Db.use(ds).query("show binlog events in '" + item.getLogName() + "'", Binlog.class);
                    binlogList.addAll(list2);
                }
                binlogList = binlogList.stream().filter(item -> item.getEventType().equals("Query")).toList();

                return analyzeBinLog(binlogList);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }


    /**
     * 解析 Binlog 文件
     **/
    public List<HistoryInfo> analyzeBinLog(List<Binlog> binlogList) throws JSQLParserException {
        List<HistoryInfo> list = new ArrayList<>();
        for (Binlog binlog : binlogList) {
            String info = binlog.getInfo();
            if (info.contains("use")) {
                String dbName = info.split(";")[0]
                        .replace("use", "")
                        .replace("`", "")
                        .replace("`", "")
                        .trim();

                String sql = info.split(";")[1].trim();

                String operate = "";
                String type = "";
                String tableName = "";
                String filedName = "";
                String filedType = "";

                Statement statement = CCJSqlParserUtil.parse(sql);
                // DDL
                if (statement instanceof CreateTable createTable) {
                    operate = "DDL";
                    type = "CREATE";
                    tableName = createTable.getTable().getName();
                } else if (statement instanceof Alter alter) {
                    operate = "DDL";
                    type = "ALERT";
                    tableName = alter.getTable().getName();
                    // 修改表三种类型： 添加字段、删除字段、修改字段
                    LinkedHashMap<String, String> map = new LinkedHashMap<>();
                    if (!alter.getAlterExpressions().isEmpty()) {
                        for (AlterExpression alterExpression : alter.getAlterExpressions()) {
                            if (alterExpression.getColumnName() != null) {
                                map.put(alterExpression.getColumnName(), alterExpression.getOperation().name());
                            } else if (!alterExpression.getColDataTypeList().isEmpty()) {
                                map.put(alterExpression.getColDataTypeList().get(0).getColumnName(), alterExpression.getOperation().name());
                            }
                        }
                    }
                    if (!map.isEmpty()) {
                        filedName = CollUtil.join(map.keySet(), ",");
                        filedType = CollUtil.join(map.values(), ",");
                    }

                } else if (statement instanceof Drop drop) {
                    operate = "DDL";
                    type = "DROP";
                    tableName = drop.getName().getName();
                }
                // DML
                else if (statement instanceof Insert insert) {
                    operate = "DML";
                    type = "INSERT";
                    tableName = insert.getTable().getName();
                } else if (statement instanceof Delete delete) {
                    operate = "DML";
                    type = "DELETE";
                    tableName = delete.getTable().getName();
                } else if (statement instanceof Update update) {
                    operate = "DML";
                    type = "UPDATE";
                    tableName = update.getTable().getName();
                }

                if (StrUtil.isNotBlank(tableName)) {
                    HistoryInfo hi = new HistoryInfo();
                    hi.setOperate(operate);
                    hi.setType(type);
                    hi.setDbName(dbName);
                    hi.setTableName(tableName.replace("`", "").replace("`", "").trim());
                    hi.setFieldName(filedName);
                    hi.setFieldType(filedType);
                    hi.setSql(sql);
                    list.add(hi);
                }
            }
        }
        return list;
    }

    /**
     * MySQL的变量参数binlog_format的值应为ROW，参数binlog_row_image的值应为FULL
     * @return
     */
    public boolean checkBinLog() {
        try {
            DataSource ds = new SimpleDataSource(config.getUrl(), config.getName(), config.getPass());
            List<Entity> query1 = Db.use(ds).query("SHOW VARIABLES LIKE 'binlog_format'");
            List<Entity> query2 = Db.use(ds).query("SHOW VARIABLES LIKE 'binlog_row_image'");
            String binlog_format = query1.get(0).getStr("Value");
            String binlog_row_image = query2.get(0).getStr("Value");
            return binlog_format.equals("ROW") && binlog_row_image.equals("FULL");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveNow() {
        try {
            DataSource ds = new SimpleDataSource(config.getUrl(), config.getName(), config.getPass());
            List<FlyLast> logs = Db.use(ds).query("show binary logs", FlyLast.class);
            saveNowBinLog(logs, ds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 保存当前的binlog到数据库
     * @param logs
     * @throws SQLException
     */
    public void saveNowBinLog(List<FlyLast> logs, DataSource ds) throws SQLException {
        Db.use(ds).execute("TRUNCATE TABLE flydb_last;");

        ArrayList<Entity> entities = new ArrayList<>();
        Date date = new Date();
        List<Entity> query = Db.use(ds).query("show master status;");
        String file = query.get(0).getStr("file");
        Long position = query.get(0).getLong("position");
        for (FlyLast log : logs) {
            Entity entity = Entity.create("flydb_last")
                    .set("time", date)
                    .set("log_name", log.getLogName())
                    .set("file_size", log.getFileSize())
                    .set("encrypted", log.getEncrypted())
                    .set("pos", log.getLogName().equals(file) ? position : null);
            entities.add(entity);
        }
        Db.use(ds).insert(entities);
    }
}
