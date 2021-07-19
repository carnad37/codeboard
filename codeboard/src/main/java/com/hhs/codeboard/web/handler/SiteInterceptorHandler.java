package com.hhs.codeboard.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hhs.codeboard.util.common.SessionUtil;
import com.hhs.codeboard.web.service.menu.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SiteInterceptorHandler implements HandlerInterceptor {
    
    @Autowired
    MenuService menuService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView model) throws Exception {
        if ("/".equals(request.getContextPath())) {
            response.sendRedirect("/main");
        }
    }
}
