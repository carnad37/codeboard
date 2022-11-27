package com.hhs.codeboard.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hhs.codeboard.web.service.member.MemberDto;
import com.hhs.codeboard.web.service.menu.MenuService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	MenuService menuService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
        
        MemberDto memberDto = (MemberDto) authentication.getPrincipal();
		//메뉴정보를 세션에 담는다
		menuService.initMenuList(memberDto, request);

		response.sendRedirect("/main");
	}
	
}
