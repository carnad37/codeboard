package com.hhs.codeboard.web.handler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hhs.codeboard.jpa.entity.MemberVO;
import com.hhs.codeboard.menu.service.MenuService;
import com.hhs.codeboard.menu.service.MenuVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            MemberVO memberVO = (MemberVO) auth.getPrincipal();
            if (memberVO != null) {
                List<MenuVO> menuList =  memberVO.getMenuList();
                model.addObject("menuList", menuList);
    
                //쿠키값에서 메뉴 seq를 구해야함.
                Integer activeSeq = menuService.getMenuSeq(request);
                if (activeSeq != null) {
                    model.addObject("menuSeq", activeSeq);
                    model.addObject("activeList", menuService.getSiteAcitveByDepth(memberVO.getMenuList(), activeSeq));
                }
               
            }
        }       
    }
}
