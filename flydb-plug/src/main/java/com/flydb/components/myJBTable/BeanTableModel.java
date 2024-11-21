package com.flydb.components.myJBTable;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class BeanTableModel<T> extends DefaultTableModel {
    private List<T> list;
    private Integer columnCount;
    public BeanTableModelInterface<T> invoke;

    public BeanTableModel(List<T> list, Integer columnCount, BeanTableModelInterface<T> invoke) {
        this.list = list;
        this.columnCount = columnCount;
        this.invoke = invoke;
    }

    @Override
    public int getRowCount() {
        return list == null ? 0 : list.size();
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
