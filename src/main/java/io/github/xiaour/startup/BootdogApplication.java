package io.github.xiaour.startup;

import io.github.xiaour.config.ServletMappingConfig;
import io.github.xiaour.handler.HttpRequestInitializer;
import io.github.xiaour.mapping.ServletMapping;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangtao
 * @Class Application
 * @Description  启动入口
 * @Date 2020/5/12 14:41
 * @Version 1.0.0
 */
public class BootdogApplication {
    private int port;

    private static final Log logger = LogFactory.getLog(BootdogApplication.class);

    public BootdogApplication(int port) {
        this.port = port;
    }

    private Map<String,String> urlServletMap = new HashMap<String, String>(16);

    public void start() throws InterruptedException {
        logger.info("Bootdog initializing...");
        //初始化请求映射关系
        this.initServletMapping();
        logger.info("Bootdog is started.");
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new HttpRequestInitializer(urlServletMap));

            Channel ch = b.bind(port).sync().channel();
            logger.info("HTTP Server at port " + port + '.');
            logger.info("uri to http://localhost:" + port + '/');

            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    private void initServletMapping(){
        for (ServletMapping servletMapping: ServletMappingConfig.servletMappings) {
            logger.debug(servletMapping.getUrl());
            urlServletMap.put(servletMapping.getUrl(),servletMapping.getClazz());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new BootdogApplication(8080).start();
    }
}
