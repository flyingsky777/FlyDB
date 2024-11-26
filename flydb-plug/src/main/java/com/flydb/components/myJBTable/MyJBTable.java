package com.flydb.components.myJBTable;

import com.intellij.ui.table.JBTable;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.List;

public class MyJBTable<T> extends JBTable {
    private List<ColumnOption> options;

    public MyJBTable(List<ColumnOption> options) {
        this.options = options;

        // 隐藏表头
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setPreferredSize(new Dimension(0, 0));
        this.getTableHeader().setVisible(false);
        this.getTableHeader().setDefaultRenderer(renderer);

        // 去掉网格线
        this.setShowGrid(false);
        this.setShowHorizontalLines(false);
        this.setShowVerticalLines(false);
        this.setFont(new Font("微软雅黑", Font.PLAIN, 12));

        // 去掉边框
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }

    public BeanTableModel<T> getBeanModel(List<T> list) {
        return new BeanTableModel<>(list, options.size(), (invoke, rowIndex, columnIndex) -> {
            Class<?> aClass = invoke.getClass();
            String field = options.get(columnIndex).getField();
            try {
                Field declaredField = aClass.getDeclaredField(field);
                declaredField.setAccessible(true);
                return declaredField.get(invoke);
            } catch (Exception e) {
                return "";
            }
        });
    }

    public void setColumnConfig() {
        for (int i = 0; i < options.size(); i++) {
            TableColumn column = this.getColumnModel().getColumn(i);

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
    }

    public void setData(List<T> historyList) {
        BeanTableModel<T> model = getBeanModel(historyList);
        this.setModel(model);
        setColumnConfig();
    }

    public void setText(String text) {
        this.getEmptyText().setText(text);
    }
}
