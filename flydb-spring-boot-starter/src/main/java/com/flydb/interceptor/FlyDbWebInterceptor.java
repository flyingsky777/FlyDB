package com.flydb.interceptor;

import com.flydb.config.FlyDBProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FlyDbWebInterceptor implements HandlerInterceptor {

    @Autowired
    FlyDBProperties properties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return properties.getEnableWeb();
    }
}