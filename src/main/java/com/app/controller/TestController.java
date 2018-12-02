package com.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {
    @RequestMapping("test")
    public Object test(){
        String info = "{\"id\":1,\"info\":\"hello world!\"}";
        return info;
    }
}
