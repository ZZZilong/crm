package com.whrj.interceptor;

import com.whrj.exceptions.NoLoginException;
import com.whrj.utils.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoLoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Integer userid = LoginUserUtil.releaseUserIdFromCookie(request);
        if (userid == null || userid == 0) {
            throw new NoLoginException();
        }
        return true;
    }
}
