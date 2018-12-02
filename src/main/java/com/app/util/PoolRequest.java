package com.app.util;

import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiongyunfa
 * @version 1.0
 * @Description 使用http连接池发送http请求
 * @Date 创建时间：2018年12月2日 19:35
 * @modified by
 */
public class PoolRequest {
    private static void config(HttpRequestBase httpRequestBase) {
        httpRequestBase.setHeader("User-Agent", "Mozilla/5.0");
        httpRequestBase.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpRequestBase.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");//"en-US,en;q=0.5");
        httpRequestBase.setHeader("Accept-Charset", "ISO-8859-1,utf-8,gbk,gb2312;q=0.7,*;q=0.7");

        // 配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(3000)
                .setConnectTimeout(3000)
                .setSocketTimeout(3000)
                .build();
        httpRequestBase.setConfig(requestConfig);
    }

    public void test() {
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainsf)
                .register("https", sslsf)
                .build();

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
        // 将最大连接数增加到200
        cm.setMaxTotal(200);
        // 将每个路由基础的连接增加到20
        cm.setDefaultMaxPerRoute(20);

        // 将目标主机的最大连接数增加到50
        HttpHost localhost = new HttpHost("http://localhost",80);
        cm.setMaxPerRoute(new HttpRoute(localhost), 50);

        //请求重试处理
        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception,int executionCount, HttpContext context) {
                if (executionCount >= 5) {// 如果已经重试了5次，就放弃
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                    return false;
                }
                if (exception instanceof InterruptedIOException) {// 超时
                    return false;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                    return false;
                }
                if (exception instanceof SSLException) {// ssl握手异常
                    return false;
                }

                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setRetryHandler(httpRequestRetryHandler)
                .build();

        // URL列表数组
        String[] urisToGet = {
                "https://localhost/httpclienttest",
                "https://localhost/httpclienttest",
                "https://localhost/httpclienttest",
                "https://localhost/httpclienttest",

                "https://localhost/httpclienttest",
                "https://localhost/httpclienttest",
                "https://localhost/httpclienttest",
                "https://localhost/httpclienttest",

                "https://localhost/httpclienttest",
                "https://localhost/httpclienttest",
                "https://localhost/httpclienttest",
                "https://localhost/httpclienttest",

                "https://localhost/httpclienttest",
                "https://localhost/httpclienttest",
                "https://localhost/httpclienttest",
                "https://localhost/httpclienttest",

                "https://localhost/httpclienttest",
                "https://localhost/httpclienttest",
                "https://localhost/httpclienttest",
                "https://localhost/httpclienttest",
        };

        long start = System.currentTimeMillis();
        try {
            int pagecount = urisToGet.length;
            ExecutorService executors = Executors.newFixedThreadPool(pagecount);
            CountDownLatch countDownLatch = new CountDownLatch(pagecount);
            for(int i = 0; i< pagecount;i++){
                HttpPost httpPost = new HttpPost(urisToGet[i]);
                config(httpPost);
                //启动线程抓取
                executors.execute(new GetRunnable(httpClient,httpPost,null,countDownLatch));
            }
            countDownLatch.await();
            executors.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("线程" + Thread.currentThread().getName() + "," + System.currentTimeMillis() + ", 所有线程已完成，开始进入下一步！");
        }
        long end = System.currentTimeMillis();
        System.out.println("consume -> " + (end - start));
    }

    static class GetRunnable implements Runnable {
        private CountDownLatch countDownLatch;
        private final CloseableHttpClient httpClient;
        private final HttpPost httpPost;
        private  Map<String,String> map;

        public GetRunnable(CloseableHttpClient httpClient, HttpPost httpPost, Map<String,String> map, CountDownLatch countDownLatch){
            this.httpClient = httpClient;
            this.countDownLatch = countDownLatch;
            this.httpPost=httpPost;
        }

        @Override
        public void run() {
            CloseableHttpResponse response = null;
            map = new HashMap<>();
            Thread thread = Thread.currentThread();
            map.put("threadId",thread.getId()+"");
            map.put("threadName",thread.getName());
            try {
                // 创建参数队列
                ArrayList<BasicNameValuePair> formParams = new ArrayList<>();//用于存放表单数据.
                //遍历map 将其中的数据转化为表单数据
                for (Map.Entry<String,String> entry:
                        map.entrySet()) {
                    formParams.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
                }
                UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
                httpPost.setEntity(uefEntity);
                response = httpClient.execute(httpPost, HttpClientContext.create());
                HttpEntity entity = response.getEntity();
                //System.out.println(EntityUtils.toString(entity, "utf-8")) ;
                EntityUtils.consume(entity);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
                try {
                    if(response != null)
                        response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
