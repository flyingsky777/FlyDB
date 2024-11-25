package com.flydb.ui.submit;

import com.flydb.components.myTree.CheckBoxTreeNode;
import com.flydb.data.entity.DBConfig;
import com.flydb.data.entity.HistoryInfo;
import com.flydb.data.service.impl.MySqlService;
import com.flydb.util.ConfigUtil;
import com.flydb.util.TreeUtils;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DataSubmit {
    private String path;
    private DBConfig dbConfig;
    private Object[] hosts;
    private Object[] databases;
    private Object[] flyDBs;
    private CheckBoxTreeNode root;

    public DataSubmit(String path) {
        this.path = path;
        List<DBConfig> mysqlList = ConfigUtil.getMysqlList(path);
        hosts = mysqlList.stream().map(DBConfig::getHost).toArray();
        databases = mysqlList.stream().map(DBConfig::getDatabase).toArray();
        flyDBs = ConfigUtil.getFlyDBPath(path);

        // 加载保存的数据

        dbConfig = mysqlList.get(0);
        root = updateTreeData();
    }


    public CheckBoxTreeNode updateTreeData() {
        MySqlService service = new MySqlService(dbConfig);
        boolean b = service.checkBinLog();
        if (b) {
            List<HistoryInfo> list = service.getList();
            if (!list.isEmpty()) {
                return TreeUtils.getTree(list, dbConfig.getDatabase());
            }
        } else {
            System.out.println("数据库不支持！");
        }
        return new CheckBoxTreeNode();
    }
}
