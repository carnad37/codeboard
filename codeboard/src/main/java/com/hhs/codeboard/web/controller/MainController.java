package com.hhs.codeboard.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping("/main")
	public String main() {
		
		return "main";
	}

	@RequestMapping("/login")
	public String login() {
		
		return "member/login";
	}

	@RequestMapping("/register")
	public String register() {
		
		return "member/register";
	}
	
}
