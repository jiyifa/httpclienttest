package com.app.controller;

import com.app.entity.Test;
import com.app.service.TestService;
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
    @RequestMapping("test")
    public Object test(){
        Test test = new Test();
        test.setName("name1");
        test.setRecordTime(new Date());
        this.testService.add(test);
        String info = "{\"id\":1,\"info\":\"hello world!\"}";
        return info;
    }
}
