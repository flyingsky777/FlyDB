package com.flydb.ui.history;

import com.flydb.components.myJBTable.MyJBTable;
import com.flydb.components.myTree.CheckBoxTreeNode;
import com.flydb.data.entity.DBConfig;
import com.flydb.data.entity.History;
import com.flydb.data.entity.HistoryInfo;
import com.flydb.data.service.HistoryService;
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

@Getter
public class DataHistory {
    private String path;
    private Object[] hosts;
    private Object[] databases;
    private Object[] flyDBs;
    private FlyDBConfig flyDBConfig;
    private List<DBConfig> mysqlList;

    public DataHistory(String path) {
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

    public List<History> updateTableData(String sFlyDb, String searchKey, String sHost, String sDb) {
        try {
            HistoryService historyService = new HistoryService(sFlyDb);
            return historyService.getHistoryList(searchKey, sDb);

            //TODO 对比数据库是否已同步
        } catch (Exception e) {
            return null;
        }
    }

    public CheckBoxTreeNode updateInfoData(String flydbPath, String dbName, String historyId) {
        try {
            HistoryService historyService = new HistoryService(flydbPath);
            List<HistoryInfo> history = historyService.getHistory(historyId);
            return TreeUtils.getTree(history, dbName);

        } catch (SQLException e) {
            return null;
        }
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

    public void updateTable(String sFlyDb, String searchKey, String sHost, String sDb, MyJBTable<History> table) {
        SwingWorker<Void, Void> swingWorker1 = new SwingWorker<>() {
            private List<History> result;

            @Override
            protected Void doInBackground() throws Exception {
                result = updateTableData(sFlyDb, searchKey, sHost, sDb);
                return null;
            }

            @Override
            protected void done() {
                table.setData(result);
                super.done();
            }
        };
        swingWorker1.execute();
    }

    public void updateInfoTree(String flydbPath, String historyId, String dbName, Tree tree) {
        SwingWorker<Void, Void> swingWorker1 = new SwingWorker<>() {
            private CheckBoxTreeNode root;

            @Override
            protected Void doInBackground() throws Exception {
                root = updateInfoData(flydbPath, dbName, historyId);
                return null;
            }

            @Override
            protected void done() {
                DefaultTreeModel newModel = new DefaultTreeModel(root);
                tree.setModel(newModel);
                TreeUtils.expandAllNodes(tree, root);
                super.done();
            }
        };
        swingWorker1.execute();
    }


}
