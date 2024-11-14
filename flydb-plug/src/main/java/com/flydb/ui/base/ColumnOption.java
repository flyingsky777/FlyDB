package com.flydb.ui.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColumnOption {
    private String label;
    private String field;
    private Integer width;
    private String align; // left, center, right

}
