package com.flydb.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlyDBConfig {
    private String name;
    private Object[] original;
    private Object[] simplify;

}
