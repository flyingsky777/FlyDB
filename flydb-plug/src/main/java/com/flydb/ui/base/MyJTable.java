package com.flydb.ui.base;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Vector;

public class MyJTable<T> {

    private BeanTableModel<T> beanTableModel;

    private List<ColumnOption> options;

    public JTable create(List<T> list, List<ColumnOption> options) {
        this.options = options;

        Object[] columns = {"Column 1", "Column 2"};
        DefaultTableModel  model = new DefaultTableModel(columns, 0);
//        beanTableModel = new BeanTableModel<>(list, options.size(), (invoke, rowIndex, columnIndex) -> {
//            Class<?> aClass = invoke.getClass();
//            String field = options.get(columnIndex).getField();
//            try {
//                Field declaredField = aClass.getDeclaredField(field);
//                declaredField.setAccessible(true);
//                return declaredField.get(invoke);
//            } catch (Exception e) {
//                return "";
//            }
//        });

        JTable historyTable = new JTable(model);
        // 隐藏表头
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setPreferredSize(new Dimension(0, 0));
        historyTable.getTableHeader().setVisible(false);
        historyTable.getTableHeader().setDefaultRenderer(renderer);

        // 去掉网格线
        historyTable.setShowGrid(false);
        historyTable.setShowHorizontalLines(false);
        historyTable.setShowVerticalLines(false);


        for (int i = 0; i < options.size(); i++) {
            TableColumn column = historyTable.getColumnModel().getColumn(i);

            // 宽度设置
            Integer width = options.get(i).getWidth();
            if (width != null) {
                column.setPreferredWidth(width);
                column.setMaxWidth(width);
                column.setMinWidth(width);
            }

            DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
            // 居中设置
            if (options.get(i).getAlign().equals("left")) {
                cr.setHorizontalAlignment(SwingConstants.LEFT);
            } else if (options.get(i).getAlign().equals("right")) {
                cr.setHorizontalAlignment(SwingConstants.RIGHT);
            } else if (options.get(i).getAlign().equals("center")) {
                cr.setHorizontalAlignment(SwingConstants.CENTER);
            }

            column.setCellRenderer(cr);
        }

        return historyTable;
    }

    public void reloadData(List<T> historyList) {
        // 清空旧数据
        beanTableModel.getDataVector().clear();
        beanTableModel.fireTableDataChanged();

        historyList.forEach(item -> {
            Class<?> aClass = item.getClass();
            Vector<Object> rowData = new Vector<>();

            options.forEach(options -> {
                String field = options.getField();
                try {
                    Field declaredField = aClass.getDeclaredField(field);
                    declaredField.setAccessible(true);
                    rowData.add(declaredField.get(item));
                } catch (Exception e) {
                    rowData.add("");
                }
            });

            rowData.forEach(System.out::println);
            beanTableModel.addRow(rowData);
        });

        beanTableModel.fireTableDataChanged();
    }
}
