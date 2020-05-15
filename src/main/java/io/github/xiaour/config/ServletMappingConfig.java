package io.github.xiaour.config;

import io.github.xiaour.mapping.ServletMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangtao
 * @Class ServletMappingConfig
 * @Description  这里其实应该改成注解，直接扫描该注解下面的映射Servlet类或Controller
 * @Date 2020/5/12 14:36
 * @Version 1.0.0
 */
public class ServletMappingConfig {

    public static List<ServletMapping> servletMappings = new ArrayList<ServletMapping>(16);
    static {
        servletMappings.add(new ServletMapping("PageNotFound","/","io.github.xiaour.controller.BootdogPageNotFoundController"));

        servletMappings.add(new ServletMapping("favicon","/favicon.ico","io.github.xiaour.controller.FaviconController"));

        servletMappings.add(new ServletMapping("HelloWorldController","/hello","io.github.xiaour.controller.HelloWorldController"));

    }
}
