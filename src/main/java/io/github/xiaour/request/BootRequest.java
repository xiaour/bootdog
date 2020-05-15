package io.github.xiaour.request;

import io.netty.handler.codec.http.HttpMethod;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhangtao
 * @Class BootRequest
 * @Description
 * @Date 2020/5/11 17:54
 * @Version 1.0.0
 */
public class BootRequest {

    private String url;
    private HttpMethod method;

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }
}
