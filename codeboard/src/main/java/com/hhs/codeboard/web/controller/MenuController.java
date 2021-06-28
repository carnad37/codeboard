package com.hhs.codeboard.web.controller;

import java.util.List;

import com.hhs.codeboard.config.anno.AspectMenuActive;
import com.hhs.codeboard.config.common.LoggerController;
import com.hhs.codeboard.enumeration.MenuTypeEnum;
import com.hhs.codeboard.util.common.SessionUtil;
import com.hhs.codeboard.web.service.member.MemberVO;
import com.hhs.codeboard.web.service.menu.MenuService;
import com.hhs.codeboard.web.service.menu.MenuVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/menu")
public class MenuController extends LoggerController {

    @Autowired
    MenuService menuService;

    @RequestMapping("refresh")
    public String menuRefresh(@AuthenticationPrincipal MemberVO memberVO
            , HttpServletRequest request) {
        //로그인한 회원정보로 menu정보 리셋.
        SessionUtil.setSession(request, "menuList", menuService.selectAllBoardMenu(memberVO.getSeq()));
        return "redirect:/main";
    }

    @AspectMenuActive(menuType = MenuTypeEnum.BOARD_CONFIG)
    @RequestMapping("config")
    public String menuConfig(@AuthenticationPrincipal MemberVO memberVO
            , Model model) {
        return "/main";
    }

}
