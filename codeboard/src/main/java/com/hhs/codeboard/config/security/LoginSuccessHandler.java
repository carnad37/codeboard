package com.hhs.codeboard.config.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hhs.codeboard.jpa.entity.BoardManagerEntity;
import com.hhs.codeboard.jpa.entity.MemberVO;
import com.hhs.codeboard.jpa.service.BoardDAO;
import com.hhs.codeboard.menu.service.MenuService;
import com.hhs.codeboard.menu.service.MenuVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	MenuService menuService;

	@Autowired
	BoardDAO boardDAO; 

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
        
        MemberVO memberVO = (MemberVO) authentication.getPrincipal();
		List<MenuVO> menuList = menuService.initMenuList();

		List<BoardManagerEntity> boardManagerList = boardDAO.findAllByRegUserSeq(memberVO.getSeq());

		//boardList를 얻어야함. 해당 boardSeq들이 필요.
		//정렬에 관해선 나중에 boardManager 테이블에 order 추가.
		boardManagerList.forEach(
			boardManager -> menuList.add(new MenuVO(boardManager))
		);
		
		memberVO.setMenuList(menuList);

        response.sendRedirect("/main");
	}
	
}
