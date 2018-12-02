package com.app.controller;

import com.app.entity.Test;
import com.app.service.TestService;
import com.app.service.ThreadRecordService;
import com.app.util.PoolTest;
import com.app.util.ThreadPoolHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
public class TestController {
    @Autowired
    private TestService testService;
    @Autowired
    private ThreadRecordService threadRecordService;
    @RequestMapping("test")
    public Object test(){
        threadRecordService.record();
        return "test";
    }
    @RequestMapping("pooltest")
    public Object pooltest(){
        PoolTest poolTest = new PoolTest();
        poolTest.test();
        return "pooltest";
    }

}
