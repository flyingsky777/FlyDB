package com.flydb.ui.dto;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class BeanTableModel<T> extends AbstractTableModel {
    private final List<T> list;
    private final Integer columnCount;
    public final BeanTableModelInterface<T> invoke;

    public BeanTableModel(List<T> list, Integer columnCount, BeanTableModelInterface<T> invoke) {
        this.list = list;
        this.columnCount = columnCount;
        this.invoke = invoke;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        T obj = list.get(rowIndex);
        return invoke.run(obj, rowIndex, columnIndex);
    }

}
