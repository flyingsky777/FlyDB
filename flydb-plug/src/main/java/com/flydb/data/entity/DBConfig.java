package com.flydb.data.entity;

import lombok.Data;

@Data
public class DBConfig {
    private String url;
    private String name;
    private String pass;
    private String driver;
    private String type;

    private String host;
    private String database;
}
