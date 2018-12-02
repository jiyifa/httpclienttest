package com.app.util;

import com.app.entity.ThreadRecord;
import com.app.mapper.ThreadRecordMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class GetThread extends Thread{

    private final HttpClient httpClient;
    private final HttpContext context;
    private final HttpGet httpget;
    private ThreadRecordMapper threadRecordMapper;

    public GetThread(HttpClient httpClient, HttpGet httpget,ThreadRecordMapper threadRecordMapper) {
        this.httpClient = httpClient;
        this.context = new BasicHttpContext();
        this.httpget = httpget;
        this.threadRecordMapper=threadRecordMapper;
    }
    @Override
    public void run(){
        this.setName("threadsPoolClient");
        try {
            Thread.sleep(5000);
            System.out.println("run"+Thread.currentThread().getName());
            Thread thr = Thread.currentThread();
            ThreadRecord threadRecord = new ThreadRecord();
            threadRecord.setThreadId(thr.getId()+"");
            threadRecord.setThreadName(thr.getName());
            threadRecord.setRecordTime(new Date());
            this.threadRecordMapper.insert(threadRecord);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        get();
    }

    public void get() {
        System.out.println("this is get methord");
        try {
            HttpResponse response = this.httpClient.execute(this.httpget, this.context);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                System.out.println(this.httpget.getURI()+": status"+response.getStatusLine().toString());
            }
            // ensure the connection gets released to the manager
            EntityUtils.consume(entity);
        } catch (Exception ex) {
            this.httpget.abort();
        }finally{
            httpget.releaseConnection();
        }
    }
}