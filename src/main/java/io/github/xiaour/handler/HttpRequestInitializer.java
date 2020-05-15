package io.github.xiaour.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangtao
 * @Class HelloServerInitializer
 * @Description
 * @Date 2020/5/12 18:58
 * @Version 1.0.0
 */
public class HttpRequestInitializer extends ChannelInitializer<Channel> {


    private Map<String,String> urlServletMap;


    public HttpRequestInitializer(Map<String, String> urlServletMap) {
        this.urlServletMap = urlServletMap;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        // 通过SocketChannel去获得对应的管道
        ChannelPipeline pipeline = channel.pipeline();
        // HttpServerCodec是由netty自己提供的助手类，可以理解为拦截器
        // 当请求到服务端，我们需要做解码，响应到客户端做编码
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());

        // 添加自定义的助手类，返回 "hello netty~"
        pipeline.addLast("HttpRequestHandler", new HttpRequestHandler(urlServletMap));
    }
}
