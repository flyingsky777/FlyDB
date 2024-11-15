package com.flydb.data.entity;

import lombok.Data;

@Data
public class DBConfig {
    private String host = "localhost";
    private String url = "root";
    private String name = "";
    private String pass = "";
    private String database = "";
}
