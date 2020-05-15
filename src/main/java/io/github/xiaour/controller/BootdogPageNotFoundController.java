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
 * @Class BootdogPageNotFoundController
 * @Description  404 页面定义，其实很多状态都是根据你自己来定义，可能这里页面错误层级定义的有点问题
 * @Date 2020/5/12 15:03
 * @Version 1.0.0
 */
public class BootdogPageNotFoundController extends AbsBootDogServlet {

    @Override
    protected FullHttpResponse doGet(BootRequest request) throws IOException {
        this.getApplicationLog().debug(request.getUrl());
        ByteBuf content = Unpooled.copiedBuffer("404.Page not found!", CharsetUtil.UTF_8);

        // 构建一个http response
        FullHttpResponse response =
                new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                        HttpResponseStatus.NOT_FOUND,
                        content);

        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
        return response;
    }

    @Override
    protected FullHttpResponse doPost(BootRequest request) throws IOException {
        ByteBuf content = Unpooled.copiedBuffer("404.Page not found!", CharsetUtil.UTF_8);
        // 构建一个http response
        FullHttpResponse response =
                new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                        HttpResponseStatus.NOT_FOUND,
                        content);
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

        return response;
    }
}
