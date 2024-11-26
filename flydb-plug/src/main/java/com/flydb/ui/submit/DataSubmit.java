package com.flydb.ui.submit;

import com.flydb.components.myTree.CheckBoxTreeNode;
import com.flydb.data.entity.DBConfig;
import com.flydb.data.entity.HistoryInfo;
import com.flydb.data.service.impl.MySqlService;
import com.flydb.util.ConfigUtil;
import com.flydb.util.TreeUtils;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.treeStructure.Tree;
import lombok.Getter;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;

@Getter
public class DataSubmit {
    private String path;
    private DBConfig dbConfig;
    private Object[] hosts;
    private Object[] databases;
    private Object[] flyDBs;
    private CheckBoxTreeNode newRoot;
    private List<DBConfig> mysqlList;

    public DataSubmit(String path) {
        this.path = path;
    }

    public void updateComboBox() {
        mysqlList = ConfigUtil.getMysqlList(path);
        hosts = mysqlList.stream().map(DBConfig::getHost).toArray();
        databases = mysqlList.stream().map(DBConfig::getDatabase).toArray();
        flyDBs = ConfigUtil.getFlyDBPath(path);
    }


    public void updateTreeData() {
        // TODO、记录已选择的
        dbConfig = mysqlList.get(0);

        if (dbConfig == null) {
            System.out.println("配置为空");
            return;
        }

        MySqlService service = new MySqlService(dbConfig);
        boolean b = service.checkBinLog();
        if (b) {
            List<HistoryInfo> list = service.getList();
            if (!list.isEmpty()) {
                newRoot = TreeUtils.getTree(list, dbConfig.getDatabase());
            }
        } else {
            System.out.println("数据库不支持！");
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

    public void updateTree(Tree tree) {
        SwingWorker<Void, Void> swingWorker1 = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                updateTreeData();
                return null;
            }

            @Override
            protected void done() {
                if (newRoot != null) {
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
