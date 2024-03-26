package com.tianll.blog.web.interceptor;

import com.tianll.blog.utils.Commons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义interceptor拦截器类，封装【请求后的数据】到request域中，使html页面中
 * 1、使用@Configuration
 * 2、自定义的interceptor进行注册
 * @author tianll
 * @date 2024/3/7
 */
@Configuration
public class BaseInterceptor implements HandlerInterceptor {
    @Autowired
    private Commons commons;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        return HandlerInterceptor.super.preHandle(request, response, handler);
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        request.setAttribute("commons",commons);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
