package io.github.xiaour.handler;

import io.github.xiaour.abs.AbsBootDogServlet;
import io.github.xiaour.config.ServletMappingConfig;
import io.github.xiaour.controller.BootdogPageNotFoundController;
import io.github.xiaour.mapping.ServletMapping;
import io.github.xiaour.request.BootRequest;
import io.github.xiaour.response.BootResponse;
import io.github.xiaour.startup.BootdogApplication;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangtao
 * @Class HttpRequestHandler
 * @Description
 * @Date 2020/5/12 18:50
 * @Version 1.0.0
 */
@ChannelHandler.Sharable
public class HttpRequestHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final Log logger = LogFactory.getLog(HttpRequestHandler.class);


    private Map<String,String> urlServletMap;

    public HttpRequestHandler(Map<String, String> urlServletMap) {
        this.urlServletMap = urlServletMap;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject msg) throws Exception {

        if (msg instanceof HttpRequest) {

            BootRequest request = new BootRequest();
            request.setUrl(((HttpRequest) msg).uri());
            request.setMethod(((HttpRequest) msg).method());

            if(urlServletMap.containsKey(request.getUrl())){
                dispatch(request,channelHandlerContext);
            }else{
                pageNotFountException(request,channelHandlerContext);
            }
        }
    }


    private void dispatch(BootRequest request,ChannelHandlerContext channelHandlerContext){
        String clazz = urlServletMap.get(request.getUrl());
        try {
            Class servletClass = Class.forName(clazz);
            AbsBootDogServlet myServlet = (AbsBootDogServlet)servletClass.newInstance();
            myServlet.service(request,channelHandlerContext);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pageNotFountException(BootRequest request,ChannelHandlerContext channelHandlerContext){
        String clazz = urlServletMap.get(request.getUrl());
        try {

            request.setUrl("BootdogPageNotFound");
            AbsBootDogServlet myServlet = BootdogPageNotFoundController.class.newInstance();
            myServlet.service(request,channelHandlerContext);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel。。。注册"+ctx.name());
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel。。。移除");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel。。。活跃");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel。。。不活跃");
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        System.out.println("channeld读取完毕。。。");
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("用户事件触发。。。");
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel可写更改");
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("捕获到异常");
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("助手类添加");
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("助手类移除");
        super.handlerRemoved(ctx);
    }

}
