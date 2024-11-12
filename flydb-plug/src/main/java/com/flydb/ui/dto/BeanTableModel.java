package com.flydb.ui.dto;

import cn.hutool.json.JSONUtil;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class BeanTableModel<T> extends AbstractTableModel {
    private final List<T> list;
    private final Class<T> type;
    private final Integer columnCount;
    public final BeanTableModelInterface<T> invoke;

    public BeanTableModel(List<T> list, Integer columnCount, BeanTableModelInterface<T> invoke) {
        this.list = list;
        this.type = (Class<T>) list.get(0).getClass();
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
        try {
            Object obj = getPropertyDescriptors()[columnIndex].getReadMethod().invoke(list.get(rowIndex));
            System.out.println(JSONUtil.toJsonStr(obj));

            return invoke.run((T) obj, rowIndex, columnIndex);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


    private java.beans.PropertyDescriptor[] getPropertyDescriptors() {
        try {
            java.beans.PropertyDescriptor[] propertyDescriptors = java.beans.Introspector.getBeanInfo(type).getPropertyDescriptors();
            return propertyDescriptors;
        } catch (Exception e) {
            e.printStackTrace();
            return new java.beans.PropertyDescriptor[0];
        }
    }
}
