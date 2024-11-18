package com.flydb.data.entity;

import lombok.Data;

@Data
public class DBConfig {
    private String host;
    private String url;
    private String name;
    private String pass;
    private String database;
    private String driver;
}
