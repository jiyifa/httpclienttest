package com.app.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Slf4j
public class HttpClientUtil {



    /**
     * 发送 get请求
     */
    public static void get() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建httpGet.
            HttpGet httpget = new HttpGet("http://www.google.com/");
            System.out.println("executing request " + httpget.getURI());
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(1000*60) //设置连接超时时间，单位毫秒。
                    .setConnectionRequestTimeout(1000*60) //设置从connect Manager(连接池)获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
                    .setSocketTimeout(1000*60).build(); //请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
            httpget.setConfig(requestConfig);
            // 执行get请求.
            try (CloseableHttpResponse response = httpclient.execute(httpget)) {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                System.out.println("--------------------------------------");
                // 打印响应状态
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    // 打印响应内容长度
                    System.out.println("Response content length: " + entity.getContentLength());
                    // 打印响应内容
                    System.out.println("Response content: " + EntityUtils.toString(entity));
                }
                System.out.println("------------------------------------");
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     */
    public static String post(Map<String,String> map, String url) {
        String responseText = null;
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httpPost
        HttpPost httppost = new HttpPost(url);
        //配置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(1000*60) //设置连接超时时间，单位毫秒。
                .setConnectionRequestTimeout(1000*60) //设置从connect Manager(连接池)获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
                .setSocketTimeout(1000*60).build(); //请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
        httppost.setConfig(requestConfig);
        // 创建参数队列
        ArrayList<BasicNameValuePair> formParams = new ArrayList<>();//用于存放表单数据.
        //遍历map 将其中的数据转化为表单数据
        for (Map.Entry<String,String> entry:
                map.entrySet()) {
            formParams.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }

        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
            httppost.setEntity(uefEntity);
            System.out.println("executing request " + httppost.getURI());
            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    System.out.println("--------------------------------------");
                    responseText = EntityUtils.toString(entity, "UTF-8");
                    System.out.println("Response content: " + responseText);
                    System.out.println("--------------------------------------");

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  responseText;
    }
}
