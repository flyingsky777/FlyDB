package com.flydb.components.myJBTable;


@FunctionalInterface
public interface BeanTableModelInterface<T> {
    Object run(T invoke, int rowIndex, int columnIndex);
}
