package com.flydb.ui.dto;

@FunctionalInterface
public interface BeanTableModelInterface<T> {
    Object run(T invoke, int rowIndex, int columnIndex);
}
