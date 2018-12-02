package com.app.util;

import com.app.view.HttpResult;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class HttpHelperTest {

    @Test
    public void executeGet() {
        try {
            HttpResult httpResult = HttpHelper.executeGet("https://www.baidu.com", "UTF-8", 20);
            System.out.println(httpResult.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void executePost() {
        Map<String,String> params = new HashMap<>();
        String url = "https://www.baidu.com";

        try {
            HttpResult httpResult = HttpHelper.executePost(url, params, "UTF-8", 20);
            System.out.println(httpResult.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}