package com.app.util;

import org.junit.Test;

import java.util.HashMap;

public class HttpClientUtilTest {
    @Test
    public void get() {
        HttpClientUtil.get();
    }

    @Test
    public void post() {
        HashMap<String, String> params = new HashMap<>();
        String url = "https://www.baidu.com";
        HttpClientUtil.post(params,url);
    }
}