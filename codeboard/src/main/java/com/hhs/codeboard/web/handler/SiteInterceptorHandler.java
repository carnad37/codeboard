package com.hhs.codeboard.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (!(auth instanceof AnonymousAuthenticationToken)) {
//            MemberVO memberVO = (MemberVO) auth.getPrincipal();
//            if (memberVO != null) {
//                List<MenuVO> menuList =  memberVO.getMenuList();
//                model.addObject("menuList", menuList);
//
//                //쿠키값에서 메뉴 seq를 구해야함.
//                //실제 구현시는 쿠키로 보내고 jquery로 실행.
//                //서버오류 안나게. thymeleaf는 서버로직 실행엔 그닥 좋지않음.
//                // Integer activeSeq = menuService.getMenuSeq(request);
//                // if (activeSeq != null) {
//                //     model.addObject("menuSeq", activeSeq);
//                //     model.addObject("activeList", menuService.getSiteAcitveByDepth(memberVO.getMenuList(), activeSeq));
//                // }
//
//            }
//        }
    }
}
