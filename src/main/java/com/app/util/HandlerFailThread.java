package com.app.util;


import java.util.HashMap;
import java.util.Map;

/**
 * @author xiongyunfa
 * @version 1.0
 * @Description 类描述
 * @Date 创建时间：2018年11月30日 11:26
 * @modified by
 */
public class HandlerFailThread extends Thread {
    Map<String, String> failUrl = new HashMap<String, String>();

    public void putFailUrl(String url, String status) {
        synchronized (failUrl) {
            failUrl.put(url, status);
        }
    }

    @Override
    public void run() {
        while (true) {

        }
    }

    public void printFailUrl() {
        for (Map.Entry<String, String> m : failUrl.entrySet()) {
            System.out.println("****fail:url:" + m.getKey() + "  code :" + m.getValue());
        }
    }
}