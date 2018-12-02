package com.app.view;

import java.io.Serializable;

/**
 * @author xiongyunfa
 * @version 1.0
 * @Description Http请求返回的结果对象信息
 * @Date 创建时间：2018年11月30日 11:10
 * @modified by
 */
public class HttpResult implements Serializable{
    private Integer statusCode;
    private String content;

    public HttpResult(Integer statusCode, String content) {
        this.statusCode = statusCode;
        this.content = content;
    }
    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "statusCode=" + statusCode +
                ", content='" + content + '\'' +
                '}';
    }
}