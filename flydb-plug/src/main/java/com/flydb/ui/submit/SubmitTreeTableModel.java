package com.flydb.ui.submit;

import com.flydb.data.entity.HistoryInfo;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.ui.treeStructure.treetable.TreeTableModel;

import javax.swing.*;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;
import java.util.List;

public class SubmitTreeTableModel implements TreeTableModel {
    private List<HistoryInfo> list;

    public SubmitTreeTableModel(List<HistoryInfo> list) {
        this.list = list;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public @NlsContexts.ColumnName String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Name";
            case 1 -> "SQL";
            default -> "Unknown";
        };
    }

    @Override
    public Class getColumnClass(int column) {
        return null;
    }

    @Override
    public Object getValueAt(Object node, int column) {
        HistoryInfo treenode = (HistoryInfo) node;
        return switch (column) {
            case 0 -> treenode.getFieldName();
            case 1 -> treenode.getSql();
            default -> "Unknown";
        };

    }

    @Override
    public boolean isCellEditable(Object node, int column) {
        return false;
    }

    @Override
    public void setValueAt(Object aValue, Object node, int column) {

    }

    @Override
    public void setTree(JTree tree) {

    }

    @Override
    public Object getRoot() {
        return null;
    }

    @Override
    public Object getChild(Object parent, int index) {
        return null;
    }

    @Override
    public int getChildCount(Object parent) {
        return 0;
    }

    @Override
    public boolean isLeaf(Object node) {
        return false;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {

    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return 0;
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {

    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {

    }
}
