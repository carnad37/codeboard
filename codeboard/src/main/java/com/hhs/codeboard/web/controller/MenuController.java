package com.hhs.codeboard.web.controller;

import java.util.List;

import com.hhs.codeboard.jpa.entity.MemberVO;
import com.hhs.codeboard.menu.service.MenuVO;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu")
public class MenuController {
    
    @RequestMapping("config")
    public String menuConfig(@AuthenticationPrincipal MemberVO memberVO
            , Model model) {
        List<MenuVO> menuList = memberVO.getMenuList();
        model.addAttribute("menuList", menuList);
        return "/main";
    }

}
