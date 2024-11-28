package com.flydb.entity;

import lombok.Data;

import java.util.Date;

@Data
public class FlydbLogs {
    private Integer id;
    private String historyId;
    private String title;
    private String name;
    private Date execTime;
    private Integer execInterval;
    private Integer execResult;
}
