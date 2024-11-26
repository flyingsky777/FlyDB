package com.flydb.ui.submit;

import com.flydb.components.myTree.CheckBoxTreeNode;
import com.flydb.data.entity.DBConfig;
import com.flydb.data.entity.HistoryInfo;
import com.flydb.data.service.DBFactory;
import com.flydb.data.service.DBService;
import com.flydb.data.service.impl.MySqlService;
import com.flydb.util.ConfigUtil;
import com.flydb.util.FlyDBConfig;
import com.flydb.util.TreeUtils;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.treeStructure.Tree;
import lombok.Getter;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Getter
public class DataSubmit {
    private String path;
    private Object[] hosts;
    private Object[] databases;
    private Object[] flyDBs;
    private FlyDBConfig flyDBConfig;
    private CheckBoxTreeNode newRoot;
    private List<DBConfig> mysqlList;

    public DataSubmit(String path) {
        this.path = path;
    }

    public void updateComboBox() {
        mysqlList = ConfigUtil.getMysqlList(path);
        hosts = mysqlList.stream().map(DBConfig::getHost).distinct().toArray();
        databases = mysqlList.stream()
                .filter(e -> e.getHost().equals(hosts[0]))
                .map(DBConfig::getDatabase)
                .distinct()
                .toArray();
        flyDBConfig = ConfigUtil.getFlyDBPath(path);
        flyDBs = flyDBConfig.getSimplify();
    }


    public String updateTreeData(Object sHost, Object sDb, Tree tree) {
        Optional<DBConfig> first = mysqlList.stream()
                .filter(e -> e.getHost().equals(sHost) && e.getDatabase().equals(sDb))
                .findFirst();

        if (first.isEmpty()) {
            return "配置为空, 请点击刷新配置！";
        }
        DBConfig dbConfig = first.get();

        try {
            DBService service = DBFactory.getDBService(dbConfig);
            boolean b = service.check();
            if (b) {
                List<HistoryInfo> list = service.getList();
                if (!list.isEmpty()) {
                    newRoot = TreeUtils.getTree(list, dbConfig.getDatabase());
                    if (newRoot.getChildCount() == 0) {
                        return "暂无数据";
                    } else {
                        return null;
                    }
                } else {
                    return "暂无数据";
                }
            } else {
                return "不支持的数据库类型、请查看官网说明！";
            }
        } catch (SQLException e) {
            return "数据库连接失败！错误消息：" + e.getMessage();
        }
    }

    public void submitDb(Object sHost, Object sDb) throws SQLException {
        DBConfig dbConfig = mysqlList.stream()
                .filter(e -> e.getHost().equals(sHost) && e.getDatabase().equals(sDb))
                .findFirst()
                .get();

        DBService service = DBFactory.getDBService(dbConfig);
        service.saveNow();
    }


    public void updateComboBox(ComboBox<Object> hostBox, ComboBox<Object> databasesBox, ComboBox<Object> flydbBox) {
        SwingWorker<Void, Void> swingWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                updateComboBox();
                return null;
            }

            @Override
            protected void done() {
                DefaultComboBoxModel<Object> hostModel = new DefaultComboBoxModel<>(hosts);
                hostBox.setModel(hostModel);

                DefaultComboBoxModel<Object> databaseModel = new DefaultComboBoxModel<>(databases);
                databasesBox.setModel(databaseModel);

                DefaultComboBoxModel<Object> flydbModel = new DefaultComboBoxModel<>(flyDBs);
                flydbBox.setModel(flydbModel);

                super.done();
            }
        };
        swingWorker.execute();
    }

    public void updateTree(Object sHost, Object sDb, Tree tree) {
        SwingWorker<Void, Void> swingWorker1 = new SwingWorker<>() {

            private String result;

            @Override
            protected Void doInBackground() throws Exception {
                newRoot = null;
                result = updateTreeData(sHost, sDb, tree);
                return null;
            }

            @Override
            protected void done() {
                if (result != null) {
                    DefaultTreeModel newModel = new DefaultTreeModel(null);
                    tree.setModel(newModel);
                    tree.getEmptyText().setText(result);
                } else if (newRoot != null) {
                    DefaultTreeModel newModel = new DefaultTreeModel(newRoot);
                    tree.setModel(newModel);
                    TreeUtils.expandAllNodes(tree, newRoot);
                }
                super.done();
            }
        };
        swingWorker1.execute();
    }
}
