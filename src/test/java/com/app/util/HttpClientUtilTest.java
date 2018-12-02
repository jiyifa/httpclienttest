package com.app.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

@RunWith(SpringRunner.class)
public class HttpClientUtilTest {

    @Test
    public void get() {
        HttpClientUtil.get();
    }

    @Test
    public void post() {
    }
}