package com.hhs.codeboard.web.controller;


import com.hhs.codeboard.jpa.entity.MemberVO;
import com.hhs.codeboard.member.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@Autowired
	MemberService memberService;

	@RequestMapping("/main")
	public String main() {		
		return "main";
	}

	@RequestMapping("/login")
	public String login(Model model) {
		return "member/login";
	}

	@RequestMapping("/register")
	public String register() {
		return "member/register";
	}

	@RequestMapping("/actionRegister")
	public String actionRegister(@ModelAttribute MemberVO insertVO) {
		memberService.insertUser(insertVO);
		return "member/login";
	}
	
}
