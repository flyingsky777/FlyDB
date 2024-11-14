package com.flydb.ui.base;

@FunctionalInterface
public interface BeanTableModelInterface<T> {
    Object run(T invoke, int rowIndex, int columnIndex);
}
