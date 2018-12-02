package com.app.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ThreadRecord {
    private Integer id;

    private String threadId;

    private String threadName;

    private String log;

    private Date recordTime;
}