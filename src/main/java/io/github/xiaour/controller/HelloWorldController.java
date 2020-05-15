package io.github.xiaour.controller;

import io.github.xiaour.abs.AbsBootDogServlet;
import io.github.xiaour.request.BootRequest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.io.IOException;

/**
 * @author zhangtao
 * @Class HelloWorldController
 * @Description
 * @Date 2020/5/12 14:38
 * @Version 1.0.0
 */
public class HelloWorldController extends AbsBootDogServlet {

    @Override
    protected FullHttpResponse doGet(BootRequest request) throws IOException {

        this.getApplicationLog().debug(request.getUrl());
        // 定义发送的数据消息(注意中文乱码的问题,idea编辑器,或者注意右下角那个编码多切换几次)
        ByteBuf content = Unpooled.copiedBuffer("Hello World by GET", CharsetUtil.UTF_8);
        // 构建一个http response
        FullHttpResponse response =
                new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                        HttpResponseStatus.OK,
                        content);
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
        return response;
    }

    @Override
    protected FullHttpResponse doPost(BootRequest request) throws IOException {

        this.getApplicationLog().debug(request.getUrl());
        // 定义发送的数据消息(注意中文乱码的问题,idea编辑器,或者注意右下角那个编码多切换几次)
        ByteBuf content = Unpooled.copiedBuffer("Hello World by POST", CharsetUtil.UTF_8);
        // 构建一个http response
        FullHttpResponse response =
                new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                        HttpResponseStatus.OK,
                        content);
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
        return response;
    }
}
