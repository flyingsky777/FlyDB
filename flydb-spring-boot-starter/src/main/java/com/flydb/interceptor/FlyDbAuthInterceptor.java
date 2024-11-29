package com.flydb.interceptor;

import cn.hutool.core.util.StrUtil;
import com.flydb.config.FlyDBProperties;
import com.flydb.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FlyDbAuthInterceptor implements HandlerInterceptor {
    @Autowired
    FlyDBProperties properties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StrUtil.isNotBlank(token)) {
            String token1 = Util.getToken(properties.getAccount(), properties.getPassword());
            return token.equals(token1);
        }
        return false;
    }
}
