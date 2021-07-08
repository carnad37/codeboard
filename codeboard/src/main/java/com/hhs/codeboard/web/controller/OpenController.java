package com.hhs.codeboard.web.controller;

import java.sql.SQLIntegrityConstraintViolationException;

import com.hhs.codeboard.config.common.LoggerController;
import com.hhs.codeboard.jpa.entity.member.MemberEntity;
import com.hhs.codeboard.util.common.SessionUtil;
import com.hhs.codeboard.web.service.member.MemberVO;
import com.hhs.codeboard.web.service.member.MemberService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/open")
@Controller
public class OpenController extends LoggerController {

	@Autowired
	MemberService memberService;

    @RequestMapping("/login")
	public String login(@AuthenticationPrincipal MemberVO loginVO,
				Model model, HttpServletRequest request) {
		if (loginVO != null) return "redirect:/main";
		return "member/login";
	}

	@RequestMapping("/register")
	public String register(@AuthenticationPrincipal MemberVO loginVO,
				@ModelAttribute MemberEntity insertVO,
				Model model) {
		if (loginVO != null) return "/main";
        model.addAttribute("insertVO", insertVO);
		return "member/register";
	}

	@RequestMapping("/actionRegister")
	public String actionRegister(@AuthenticationPrincipal MemberVO loginVO,
				@ModelAttribute MemberEntity insertVO,
				Model model) throws Exception {
        if (loginVO != null) return "/main";
        try {
            memberService.insertUser(insertVO);
        } catch (Exception e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                model.addAttribute("error", "비밀번호가 중복됩니다.");
            } else {
                model.addAttribute("error", "알수없는 오류로 회원가입에 실패하였습니다.");
            }
            return register(null, insertVO, model);
        }
		
		return "member/login";
	}

}
