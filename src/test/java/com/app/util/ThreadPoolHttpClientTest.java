package com.app.util;

import com.app.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ThreadPoolHttpClientTest {

    @Test
    public void test1() {
//        String urls[] = {"http://www.baidu.com","http://www.baidu.com","http://www.baidu.com","http://www.baidu.com","http://www.baidu.com","http://www.baidu.com","http://www.baidu.com","http://www.baidu.com","http://www.baidu.com","http://www.baidu.com"};
//        ThreadPoolHttpClient threadPoolHttpClient = new ThreadPoolHttpClient(urls);
//        try {
//            threadPoolHttpClient.test();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}