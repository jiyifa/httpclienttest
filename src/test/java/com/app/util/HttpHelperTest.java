package com.app.util;

import com.app.view.HttpResult;
import org.junit.Test;

import java.io.IOException;

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
    }
}