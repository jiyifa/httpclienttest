package com.app.util;

import org.junit.Test;

import static org.junit.Assert.*;


public class ThreadPoolHttpClientTest {

    @Test
    public void test1() {
        String urls[] = {"http://www.baidu.com","http://www.baidu.com","http://www.baidu.com","http://www.baidu.com","http://www.baidu.com","http://www.baidu.com","http://www.baidu.com","http://www.baidu.com","http://www.baidu.com","http://www.baidu.com"};
        ThreadPoolHttpClient threadPoolHttpClient = new ThreadPoolHttpClient(urls);
        try {
            threadPoolHttpClient.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}