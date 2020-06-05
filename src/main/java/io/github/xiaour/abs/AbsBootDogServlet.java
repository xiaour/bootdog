package io.github.xiaour.abs;

import io.github.xiaour.enums.RequestMethod;
import io.github.xiaour.request.BootRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * @author zhangtao
 * @Class AbsBootDogServlet
 * @Description
 * @Date 2020/5/12 10:06
 * @Version 1.0.0
 */
public abstract class AbsBootDogServlet {

    private static final Log logger = LogFactory.getLog(AbsBootDogServlet.class);

    private Class<?> mainApplicationClass;

    protected Log getApplicationLog() {
        return this.mainApplicationClass == null ? logger : LogFactory.getLog(this.mainApplicationClass);
    }

    /**
     * Get 请求
     * @param request
     * @throws IOException
     */
    protected abstract FullHttpResponse doGet(BootRequest request) throws IOException;

    /**
     * POST 请求
     * @param request
     * @throws IOException
     */
    protected abstract FullHttpResponse doPost(BootRequest request) throws IOException;

    /**
     * 处理逻辑
     * @param request
     * @param channelHandlerContext
     * @throws NoSuchMethodException
     * @throws IOException
     */
    public void service(BootRequest request,ChannelHandlerContext channelHandlerContext) throws NoSuchMethodException, IOException {
        if (request.getMethod().name().equals(RequestMethod.POST.name())) {
            // 把响应刷到客户端
            channelHandlerContext.writeAndFlush(doPost(request));
        }else if(request.getMethod().name().equals(RequestMethod.GET.name())){
            channelHandlerContext.writeAndFlush(doGet(request));
        }else {
            logger.error("Bootdog not support the request method:"+request.getMethod());
            throw new NoSuchMethodException("not support");
        }
    }
}
