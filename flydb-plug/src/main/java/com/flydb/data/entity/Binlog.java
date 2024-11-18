package com.flydb.data.entity;

import lombok.Data;

@Data
public class Binlog {
    private String logName;
    private Long pos;
    private String eventType;
    private String endLogPos;
    private String info;
}
