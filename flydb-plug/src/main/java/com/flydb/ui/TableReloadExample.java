package com.flydb.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TableReloadExample {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;

    public TableReloadExample() {
        initUI();
    }

    private void initUI() {
        frame = new JFrame("JTable Data Reload Example");

        Object[] columns = {"Column 1", "Column 2"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        JButton reloadButton = new JButton("Reload Data");
        reloadButton.addActionListener(e -> reloadData());

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(reloadButton, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    private void reloadData() {
        // 清空旧数据
        model.getDataVector().clear();
        model.fireTableDataChanged();

        // 添加新数据
        // 假设我们有一些新的数据
        Object[] row1 = {"Row 1 Data 1", "Row 1 Data 2"};
        Object[] row2 = {"Row 2 Data 1", "Row 2 Data 2"};
        model.addRow(row1);
        model.addRow(row2);
        model.fireTableDataChanged();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TableReloadExample::new);
    }
}