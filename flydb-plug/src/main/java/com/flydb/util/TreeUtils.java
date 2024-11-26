package com.flydb.util;

import com.flydb.components.myTree.CheckBoxTreeNode;
import com.flydb.data.entity.HistoryInfo;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class TreeUtils {

    public static CheckBoxTreeNode getTree(List<HistoryInfo> list, String dbName) {
        LinkedHashMap<String, List<HistoryInfo>> ddlMap = new LinkedHashMap<>();
        LinkedHashMap<String, List<HistoryInfo>> dmlMap = new LinkedHashMap<>();
        list.forEach(item -> {
            if (item.getDbName().equals(dbName)) {
                if (item.getOperate().equals("DML")) {
                    dmlMap.computeIfAbsent(item.getTableName(), k -> new ArrayList<>()).add(item);
                }
                if (item.getOperate().equals("DDL")) {
                    ddlMap.computeIfAbsent(item.getTableName(), k -> new ArrayList<>()).add(item);
                }
            }
        });

        CheckBoxTreeNode dmlN = new CheckBoxTreeNode(new HistoryInfo("DML"));
        dmlMap.forEach((k, v) -> {
            HistoryInfo historyInfo = new HistoryInfo();
            historyInfo.setTableName(k);
            CheckBoxTreeNode dmlTable = new CheckBoxTreeNode(historyInfo);
            dmlN.add(dmlTable);

            v.forEach(item -> dmlTable.add(new CheckBoxTreeNode(item)));
        });


        CheckBoxTreeNode ddlN = new CheckBoxTreeNode(new HistoryInfo("DDL"));
        ddlMap.forEach((k, v) -> {
            HistoryInfo historyInfo = new HistoryInfo();
            historyInfo.setTableName(k);
            CheckBoxTreeNode ddlTable = new CheckBoxTreeNode(historyInfo);
            ddlN.add(ddlTable);

            v.forEach(item -> ddlTable.add(new CheckBoxTreeNode(item)));
        });

        CheckBoxTreeNode root = new CheckBoxTreeNode(new HistoryInfo("root"));
        root.add(ddlN);
        root.add(dmlN);
        return root;
    }

    // 展开节点
    public static void expandAllNodes(JTree tree, DefaultMutableTreeNode node) {
        for (int i = 0; i < node.getChildCount(); i++) {
            tree.expandPath(new TreePath(((DefaultMutableTreeNode) node.getChildAt(i)).getPath()));
            expandAllNodes(tree, (DefaultMutableTreeNode) node.getChildAt(i));
        }
    }

}
