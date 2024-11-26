package com.flydb.ui.history;

import com.flydb.components.myJBTable.MyJBTable;
import com.flydb.components.myTree.CheckBoxTreeNode;
import com.flydb.data.entity.History;
import com.flydb.data.entity.HistoryInfo;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.treeStructure.Tree;

import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HistoryUI extends SimpleToolWindowPanel {
    private final Project project;
    private final ToolWindow toolWindow;

    public HistoryUI(Project project, ToolWindow toolWindow) {
        super(false, false);
        this.project = project;
        this.toolWindow = toolWindow;
        initUI();
    }

    private void initUI() {
        JBSplitter splitter = new JBSplitter(false);

        DataHistory data = new DataHistory(project.getBasePath());
        LeftHistoryUI listUi = new LeftHistoryUI(data);
        RightInfoUI infoUi = new RightInfoUI(data);

        splitter.setFirstComponent(listUi);
        splitter.setSecondComponent(infoUi);
        splitter.setProportion(0.4f);
        splitter.setDividerWidth(2);
        setContent(splitter);

        // 更新下拉框
        data.updateComboBox(listUi.getHost(), listUi.getDatabases(), listUi.getFlydb());

        // 手动刷新数据库
        listUi.getRefresh().addActionListener(e -> {
            String sHost = (String) listUi.getHost().getSelectedItem();
            String sDb = (String) listUi.getDatabases().getSelectedItem();

            int selectedIndex = listUi.getFlydb().getSelectedIndex();
            if (selectedIndex >= 0) {
                String path = (String) data.getFlyDBConfig().getOriginal()[selectedIndex];
                data.updateTable(path, null, sHost, sDb, listUi.getTable());
            }
        });

        // 表格点击监听
        MyJBTable<History> table = listUi.getTable();
        Tree tree = infoUi.getTree();
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int selectedIndex = listUi.getFlydb().getSelectedIndex();
                    if (selectedIndex >= 0) {
                        String path = (String) data.getFlyDBConfig().getOriginal()[selectedIndex];
                        String historyId = (String) table.getModel().getValueAt(selectedRow, 0);
                        String dbName = (String) listUi.getDatabases().getSelectedItem();
                        data.updateInfoTree(path, historyId, dbName, tree);
                    }
                }
            }
        });

        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                Tree tree = (Tree) event.getSource();
                int x = event.getX();
                int y = event.getY();
                int row = tree.getRowForLocation(x, y);
                TreePath path = tree.getPathForRow(row);
                if (path != null) {
                    CheckBoxTreeNode node = (CheckBoxTreeNode) path.getLastPathComponent();
                    if (node != null) {
                        HistoryInfo info = (HistoryInfo) node.getUserObject();
                        infoUi.getArea().setText(info.getSql());
                    }
                }
            }
        });
    }
}
