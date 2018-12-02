package com.app.util;

import com.app.entity.ThreadRecord;
import com.app.mapper.ThreadRecordMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolHttpClient {
    private ThreadRecordMapper threadRecordMapper;
    // 线程池
    private ExecutorService exe = null;
    // 线程池的容量
    private static final int POOL_SIZE = 20;
    private HttpClient client = null;
    String[] urls=null;

    public ThreadPoolHttpClient(ThreadRecordMapper threadRecordMapper, String[] urls) {
        this.threadRecordMapper = threadRecordMapper;
        this.urls = urls;
    }

    public void test() throws Exception {
        exe = Executors.newFixedThreadPool(POOL_SIZE);

        HttpParams params =new BasicHttpParams();
        /* 从连接池中取连接的超时时间 */
        ConnManagerParams.setTimeout(params, 1000);
        /* 连接超时 */
        HttpConnectionParams.setConnectionTimeout(params, 2000);
        /* 请求超时 */
        HttpConnectionParams.setSoTimeout(params, 4000);
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(
                new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));

        //ClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
        PoolingClientConnectionManager cm=new PoolingClientConnectionManager(schemeRegistry);
        cm.setMaxTotal(10);
        final HttpClient httpClient = new DefaultHttpClient(cm,params);


        // URIs to perform GETs on
        final String[] urisToGet = urls;
        /* 有多少url创建多少线程，url多时机子撑不住
        使用线程池*/
        System.out.println(threadRecordMapper);
        for (int i = 0; i < urisToGet.length; i++) {
            final int j=i;
            System.out.println(j);
            HttpGet httpget = new HttpGet(urisToGet[i]);
            exe.execute( new GetThread(httpClient, httpget,threadRecordMapper));
        }
        System.out.println("Done");
    }
}