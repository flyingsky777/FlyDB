package com.flydb.data.entity;

import lombok.Data;

@Data
public class FlyLast {
    private String id;
    private String time;
    private String logName;
    private Long fileSize;
    private String encrypted;
}
