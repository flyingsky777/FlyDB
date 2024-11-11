package com.flydb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 是否开启web服务
 */
@Configuration
public class FlyDBWebConfig implements WebMvcConfigurer {
    @Autowired
    FlyDbWebInterceptor flyDbWebInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(flyDbWebInterceptor)
                .addPathPatterns("/flydb/**");
    }
}

